package customer.btpjapanprojectdemo.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;
import customer.btpjapanprojectdemo.exception.BusinessException;
import customer.btpjapanprojectdemo.service.POMappingService;

@Service
public class POMappingServiceImpl implements POMappingService {

    private final RestTemplate restTemplate;
    private final GenericCqnService genericCqnService;
    private final ObjectMapper objectMapper;
    private String csrfToken = null;
    private String cookies = null;

    public POMappingServiceImpl(GenericCqnService genericCqnService) {
        // Configure RestTemplate with UTF-8
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        this.genericCqnService = genericCqnService;

        // Configure ObjectMapper with UTF-8 (single instance)
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
    }

    @Override
    public POMapping startReplicatingPO(StartReplicatingPOContext context) {

        // 1. Get all replicated PO number
        JsonNode mappedPONumbers = genericCqnService.getPOHeaderItemOriginalIds();

        // 2. Create filter to get unreplicated PO Number
        StringBuilder filterCondition = new StringBuilder("PurchaseOrderItemCategory eq '3'");
        if (mappedPONumbers.size() > 0) {
            filterCondition.append(" and not (");
            boolean first = true;
            for (JsonNode node : mappedPONumbers) {
                String po = node.get(POMapping.ORIGINAL_PO).asText();
                String item = node.get(POMapping.ORIGINAL_PO_ITEM).asText();
                if (!first) {
                    filterCondition.append(" or ");
                }
                filterCondition.append("PurchaseOrder eq '").append(po).append("'")
                        .append(" and PurchaseOrderItem eq '").append(item).append("'");
                first = false;
            }
            filterCondition.append(")");
        }

        // 3. Get all unreplicated PO number
        List<String> queryParams1 = Arrays.asList(
                "$filter=" + filterCondition.toString(),
                "$select=PurchaseOrder,PurchaseOrderItem");
        JsonNode allPONumbers = executeRequest(
                HttpMethod.GET,
                "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrderItem",
                "Basic U1VCUE9fNDc4MjU6JmhTa0Bbdm82LWsyU0R2Vm5ZW2RaLTVmVldNKXJ4UyhkXGtrRkt0Uw==",
                queryParams1,
                "",
                "",
                "");

        // 4. Replicate the PO
        List<String> queryParams2 = Arrays.asList(
                "$expand=to_PurchaseOrderItem/to_ScheduleLine/to_SubcontractingComponent");
        allPONumbers.forEach(node -> {
            String replicate_po_url = String.format(
                    "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrderItem(PurchaseOrder='%s',PurchaseOrderItem='%s')/to_PurchaseOrder",
                    node.get("PurchaseOrder").asText(),
                    node.get("PurchaseOrderItem").asText());
            JsonNode subcontractPO = executeRequest(
                    HttpMethod.GET,
                    replicate_po_url,
                    "Basic U1VCUE9fNDc4MjU6JmhTa0Bbdm82LWsyU0R2Vm5ZW2RaLTVmVldNKXJ4UyhkXGtrRkt0Uw==",
                    queryParams2,
                    "",
                    "",
                    "");
            String requestBody = generateBody(subcontractPO);
            if (requestBody != null) {
                // JsonNode replicatedPO = executeRequest(
                // HttpMethod.POST,
                // "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrder",
                // "Basic
                // U1VCUE9fNDc4MjU6JmhTa0Bbdm82LWsyU0R2Vm5ZW2RaLTVmVldNKXJ4UyhkXGtrRkt0Uw==",
                // Collections.emptyList(),
                // this.csrfToken,
                // requestBody,
                // this.cookies);
            }
        });
        return null;
    }

    private JsonNode executeRequest(HttpMethod method, String baseUrl, String authorization, List<String> parameters,
            String CSRFToken, String requestBody,
            String cookieHeader) {
        // Build API Url
        String queryParams = String.join("&", parameters);
        String apiUrl = baseUrl + "?" + queryParams;

        // Build API Header with UTF-8
        HttpHeaders headers = new HttpHeaders();
        headers.set("DataServiceVersion", "2.0");
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
        headers.set("Authorization", authorization);
        headers.set("x-csrf-token", (CSRFToken == null || CSRFToken.isEmpty()) ? "fetch" : CSRFToken);
        if (cookieHeader != null && !cookieHeader.isEmpty()) {
            headers.set("Cookie", cookieHeader);
        }

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Execute API
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    method,
                    requestEntity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode responseJsonNode = objectMapper.readTree(response.getBody());
                JsonNode d = responseJsonNode.get("d");
                this.csrfToken = response.getHeaders().getFirst("x-csrf-token");
                List<String> cookieList = response.getHeaders().get("Set-Cookie");
                if (cookieList != null) {
                    List<String> pairs = new java.util.ArrayList<>();
                    for (String line : cookieList) {
                        String first = line.split(";", 2)[0];
                        if (first.contains("="))
                            pairs.add(first.trim());
                    }
                    this.cookies = String.join("; ", pairs);
                }

                // Check if it's a collection or single entity
                if (d.has("results")) {
                    return d.get("results");
                } else {
                    return d;
                }
            } else {
                throw new BusinessException("API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            throw new BusinessException("Failed to get subcontract PO items", e);
        }
    }

    private String generateBody(JsonNode rawBody) {
        JsonNode cleanedBody = cleanODataResponse(rawBody);
        try {
            // Use the UTF-8 configured objectMapper
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cleanedBody);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Failed to generate request body", e);
        }
    }

    private JsonNode cleanODataResponse(JsonNode node) {
        if (node == null)
            return null;

        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;

            // Remove all unneeded fields
            objectNode.remove("PurchaseOrder");
            objectNode.remove("CreationDate");
            objectNode.remove("CreatedByUser");
            objectNode.remove("LastChangeDateTime");
            objectNode.remove("PurchasingDocument");
            objectNode.remove("PurchasingDocumentItem");
            objectNode.remove("PurchasingProcessingStatus");
            objectNode.remove("PurchasingCompletenessStatus");
            objectNode.remove("PurchasingDocumentOrigin");
            objectNode.remove("PurchasingDocumentDeletionCode");
            objectNode.remove("IsCompletelyDelivered");
            objectNode.remove("IsFinallyInvoiced");
            objectNode.remove("__metadata");
            objectNode.remove("__deferred");
            objectNode.remove("__next");
            objectNode.remove("__count");
            objectNode.remove("to_PurchaseOrderNote");
            objectNode.remove("to_AccountAssignment");
            objectNode.remove("to_PurchaseOrderItemNote");
            objectNode.remove("to_PurchaseOrderPricingElement");
            objectNode.remove("to_PurchaseOrder");

            // Recursively clean nested objects
            objectNode.fields().forEachRemaining(entry -> {
                cleanODataResponse(entry.getValue());
            });
        } else if (node.isArray()) {
            node.forEach(this::cleanODataResponse);
        }

        return node;
    }
}