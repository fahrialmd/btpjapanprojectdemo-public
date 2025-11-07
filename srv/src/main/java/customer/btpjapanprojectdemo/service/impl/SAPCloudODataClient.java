package customer.btpjapanprojectdemo.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import customer.btpjapanprojectdemo.exception.BusinessException;

@Service
public class SAPCloudODataClient {

    // SAP Communication Users
    private static final String PURCHASE_ORDER_USER_AUTH = "Basic U1VCUE9fNDc4MjU6JmhTa0Bbdm82LWsyU0R2Vm5ZW2RaLTVmVldNKXJ4UyhkXGtrRkt0Uw==";
    private static final String GOODS_MOVEMENT_USER_AUTH = "Basic R09PRFNSRUNFSVBUXzQ3ODE4OktZaDZKVDg+V11vNyh4OUxcJDxaXXp2JXMve2hcLTVva0ppRz4lQ1k=";
    private static final String INVOICE_REGISTRATION_USER_AUTH = "Basic SU5WT0lDRVVTRVJfNDkyMjM6WGRxU25UUGp0a0p7cktTUFIyNks8NiM3enBLNiNMUWcrQVZlZSNiVQ==";
    private static final String ACCOUNTING_CLEARING_USER_AUTH = "";

    // Basic URL for CSRF
    private static final String PURCHASE_ORDER_USER_URL = "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrder?$top=0";
    private static final String GOODS_MOVEMENT_USER_URL = "https://my200132-api.s4hana.sapcloud.cn/sap/opu/odata/sap/API_MATERIAL_DOCUMENT_SRV/A_MaterialDocumentHeader?$top=0";
    private static final String INVOICE_REGISTRATION_USER_URL = "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_SUPPLIERINVOICE_PROCESS_SRV/A_SupplierInvoice?$top=0";
    private static final String ACCOUNTING_CLEARING_USER_URL = "";

    // User session CSRF token
    private String purchaseOrderCsrfToken;
    private String goodsMovementCsrfToken;
    private String invoiceRegistrationCsrfToken;
    private String accountingClearingCsrfToken;

    // User session cookies
    private String purchaseOrderCookies;
    private String goodsMovementCookies;
    private String invoiceRegistrationCookies;
    private String accountingClearingCookies;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SAPCloudODataClient() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        objectMapper = new ObjectMapper();
    }

    /**
     * SAP communication user constant
     */
    public enum SAPCommUser {
        PURCHASE_ORDER,
        GOODS_MOVEMENT,
        INVOICE_REGISTRATION,
        ACCOUNTING_CLEARING
    }

    /**
     * Main API execution method
     */
    public JsonNode executeRequest(HttpMethod method,
            String baseUrl,
            String requestBody,
            SAPCommUser user) {
        try {
            if (method != HttpMethod.GET) {
                ensureCsrfToken(baseUrl, user);
            }

            if (requestBody == null) {
                requestBody = "";
            }

            HttpHeaders headers = buildHeaders(getCsrfToken(user), getCookies(user), user);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(baseUrl, method, request, String.class);

            updateSessionState(response, user);

            return parseResponse(response);

        } catch (Exception e) {
            throw new BusinessException("OData API call failed " + e.getMessage());
        }
    }

    /*
     * Check if CSRF token exist
     */
    private void ensureCsrfToken(String baseUrl, SAPCommUser user) {
        String currentToken = getCsrfToken(user);
        if (currentToken == null || currentToken.isEmpty()) {
            fetchCsrfToken(baseUrl, user);
        }
    }

    /*
     * Get CSRF token with basic url
     */
    private void fetchCsrfToken(String baseUrl, SAPCommUser user) {
        try {
            HttpHeaders headers = buildHeaders("fetch", null, user);
            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    getUserBasicUrl(user),
                    HttpMethod.GET,
                    request,
                    String.class);

            updateSessionState(response, user);
        } catch (RestClientException e) {
            throw new BusinessException("Failed to fetch CSRF token for " + user, e);
        }
    }

    /*
     * Build request headers
     */
    private HttpHeaders buildHeaders(String csrfToken, String cookies, SAPCommUser user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("DataServiceVersion", "2.0");
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        headers.set("Authorization", getAuthorization(user));
        if (csrfToken != null && !csrfToken.isEmpty()) {
            headers.set("x-csrf-token", csrfToken);
        }
        if (cookies != null && !cookies.isEmpty()) {
            headers.set("Cookie", cookies);
        }
        return headers;
    }

    private void updateSessionState(ResponseEntity<String> response, SAPCommUser user) {
        String newCsrfToken = response.getHeaders().getFirst("x-csrf-token");
        if (newCsrfToken != null && !newCsrfToken.isEmpty()) {
            setCsrfToken(user, newCsrfToken);
        }

        List<String> cookieList = response.getHeaders().get("Set-Cookie");
        if (cookieList != null && !cookieList.isEmpty()) {
            String cookies = cookieList.stream()
                    .map(cookie -> cookie.split(";", 2)[0].trim())
                    .filter(cookie -> cookie.contains("="))
                    .collect(Collectors.joining("; "));
            setCookies(user, cookies);
        }
    }

    private JsonNode parseResponse(ResponseEntity<String> response) throws Exception {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException("API returned status: " + response.getStatusCode());
        }

        if (response.getBody() == null || response.getBody().isEmpty()) {
            return objectMapper.createObjectNode();
        }

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode data = root.get("d");

        if (data == null) {
            return root;
        }

        // If the result is a collection, odata will put it into an array called
        // "results"
        return data.has("results") ? data.get("results") : data;
    }

    // Helper methods for user-specific session management
    private String getAuthorization(SAPCommUser user) {
        return switch (user) {
            case SAPCommUser.PURCHASE_ORDER -> PURCHASE_ORDER_USER_AUTH;
            case SAPCommUser.GOODS_MOVEMENT -> GOODS_MOVEMENT_USER_AUTH;
            case SAPCommUser.INVOICE_REGISTRATION -> INVOICE_REGISTRATION_USER_AUTH;
            case SAPCommUser.ACCOUNTING_CLEARING -> ACCOUNTING_CLEARING_USER_AUTH;
            default -> throw new BusinessException("Unknown SAP user" + user);
        };
    }

    private String getCsrfToken(SAPCommUser user) {
        return switch (user) {
            case SAPCommUser.PURCHASE_ORDER -> purchaseOrderCsrfToken;
            case SAPCommUser.GOODS_MOVEMENT -> goodsMovementCsrfToken;
            case SAPCommUser.INVOICE_REGISTRATION -> invoiceRegistrationCsrfToken;
            case SAPCommUser.ACCOUNTING_CLEARING -> accountingClearingCsrfToken;
            default -> null;
        };
    }

    private void setCsrfToken(SAPCommUser user, String token) {
        switch (user) {
            case SAPCommUser.PURCHASE_ORDER -> purchaseOrderCsrfToken = token;
            case SAPCommUser.GOODS_MOVEMENT -> goodsMovementCsrfToken = token;
            case SAPCommUser.INVOICE_REGISTRATION -> invoiceRegistrationCsrfToken = token;
            case SAPCommUser.ACCOUNTING_CLEARING -> accountingClearingCsrfToken = token;
            default -> {
            }
        }
    }

    private String getCookies(SAPCommUser user) {
        return switch (user) {
            case SAPCommUser.PURCHASE_ORDER -> purchaseOrderCookies;
            case SAPCommUser.GOODS_MOVEMENT -> goodsMovementCookies;
            case SAPCommUser.INVOICE_REGISTRATION -> invoiceRegistrationCookies;
            case SAPCommUser.ACCOUNTING_CLEARING -> accountingClearingCookies;
            default -> null;
        };
    }

    private void setCookies(SAPCommUser user, String cookies) {
        switch (user) {
            case SAPCommUser.PURCHASE_ORDER -> purchaseOrderCookies = cookies;
            case SAPCommUser.GOODS_MOVEMENT -> goodsMovementCookies = cookies;
            case SAPCommUser.INVOICE_REGISTRATION -> invoiceRegistrationCookies = cookies;
            case SAPCommUser.ACCOUNTING_CLEARING -> accountingClearingCookies = cookies;
            default -> {
            }
        }
    }

    private String getUserBasicUrl(SAPCommUser user) {
        return switch (user) {
            case SAPCommUser.PURCHASE_ORDER -> PURCHASE_ORDER_USER_URL;
            case SAPCommUser.GOODS_MOVEMENT -> GOODS_MOVEMENT_USER_URL;
            case SAPCommUser.INVOICE_REGISTRATION -> INVOICE_REGISTRATION_USER_URL;
            case SAPCommUser.ACCOUNTING_CLEARING -> ACCOUNTING_CLEARING_USER_URL;
            default -> throw new BusinessException("Unknown SAP user" + user);
        };
    }

}