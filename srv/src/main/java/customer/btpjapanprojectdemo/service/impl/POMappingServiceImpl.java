package customer.btpjapanprojectdemo.service.impl;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;
import customer.btpjapanprojectdemo.service.POMappingService;

@Service
public class POMappingServiceImpl implements POMappingService {

    private final RestTemplate restTemplate;

    public POMappingServiceImpl() {
        this.restTemplate = createRestTemplate();
    }

    @Override
    public POMapping startReplicatingPO(StartReplicatingPOContext context) {

        POMapping testvar;

        // 1. Get all unprocessed subcontract po item number
        getSubcontractPOItem();
        // 2.

        return null;
    }

    private void getSubcontractPOItem() {
        // API URL with query parameter
        String apiUrl = "https://my200132.s4hana.sapcloud.cn:443/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrderItem?$filter=PurchaseOrderItemCategory eq '3'";

        // Setup headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("DataServiceVersion", "2.0");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Basic U1VCUE9fNDc4MjU6JmhTa0Bbdm82LWsyU0R2Vm5ZW2RaLTVmVldNKXJ4UyhkXGtrRkt0Uw==");

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    requestEntity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Response: " + response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }

}
