package customer.btpjapanprojectdemo.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;
import customer.btpjapanprojectdemo.exception.BusinessException;
import customer.btpjapanprojectdemo.model.PurchaseOrderDTO;
import customer.btpjapanprojectdemo.model.PurchaseOrderItemDTO;
import customer.btpjapanprojectdemo.service.PurchaseOrderReplicationService;
import customer.btpjapanprojectdemo.util.PurchaseOrderPostPrepare;

@Service
public class PurchaseOrderReplicationServiceImpl implements PurchaseOrderReplicationService {

        private final RestTemplate restTemplate;
        private final GenericCqnService genericCqnService;
        private final ObjectMapper objectMapper;
        private String csrfToken = null;
        private String cookies = null;
        private final SAPCloudODataClient sapCloudODataClient;

        public PurchaseOrderReplicationServiceImpl(GenericCqnService genericCqnService,
                        SAPCloudODataClient sapCloudODataClient) {
                this.restTemplate = new RestTemplate();
                this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                this.genericCqnService = genericCqnService;
                this.objectMapper = new ObjectMapper();
                this.objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
                this.sapCloudODataClient = sapCloudODataClient;
        }

        @Override
        public POMapping startReplicatingPO(StartReplicatingPOContext context) {

                // 1. Get existing mapped PO numbers
                JsonNode mappedPoNumbers = genericCqnService.getPOHeaderItemOriginalIds();

                // 2. Get unmapped PO numbers from SAP
                String completeFilter;

                if (mappedPoNumbers.size() == 0) {
                        completeFilter = "PurchaseOrderItemCategory eq '3'";
                } else {
                        List<String> mappedPoFilters = new ArrayList<>();

                        mappedPoNumbers.forEach(node -> {
                                String filter = String.format(
                                                "(PurchaseOrder eq '%s' and PurchaseOrderItem eq '%s')",
                                                node.get(POMapping.ORIGINAL_PO).asText(),
                                                node.get(POMapping.ORIGINAL_PO_ITEM).asText());
                                mappedPoFilters.add(filter);
                        });

                        String mappedPoConditions = String.join(" or ", mappedPoFilters);
                        completeFilter = String.format(
                                        "PurchaseOrderItemCategory eq '3' and not (%s)",
                                        mappedPoConditions);
                }

                String allPoNumbersUrl = UriComponentsBuilder.newInstance()
                                .scheme("https")
                                .host("my200132.s4hana.sapcloud.cn")
                                .path("/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrderItem")
                                .queryParam("$select", "PurchaseOrder,PurchaseOrderItem")
                                .queryParam("$filter", completeFilter)
                                .queryParam("$top", "1")
                                .build()
                                .toUriString();

                JsonNode allPoNumbers = sapCloudODataClient.executeRequest(
                                HttpMethod.GET,
                                allPoNumbersUrl,
                                null,
                                SAPCloudODataClient.SAPCommUser.PURCHASE_ORDER);

                // 3. Group allPoNumbers by PurchaseOrder
                Map<String, List<String>> allPoNumbersMap = new LinkedHashMap<>();
                ArrayNode allPoNumbersResult = objectMapper.createArrayNode();

                allPoNumbers.forEach(node -> {
                        String poNumber = node.path("PurchaseOrder").asText();
                        String poItem = node.path("PurchaseOrderItem").asText();

                        allPoNumbersMap.computeIfAbsent(poNumber, k -> new ArrayList<>()).add(poItem);
                });

                for (Map.Entry<String, List<String>> entry : allPoNumbersMap.entrySet()) {
                        ObjectNode poObject = objectMapper.createObjectNode();
                        poObject.put("PurchaseOrder", entry.getKey());

                        ArrayNode itemsArray = objectMapper.createArrayNode();
                        for (String item : entry.getValue()) {
                                itemsArray.add(item);
                        }

                        poObject.set("PurchaseOrderItem", itemsArray);
                        allPoNumbersResult.add(poObject);
                }

                // 4. Get the PO Body for replication
                ArrayNode poBodyResults = objectMapper.createArrayNode();
                allPoNumbersResult.forEach(node -> {
                        boolean isSingleItem = node.path("PurchaseOrderItem").size() == 1;
                        // Check if the PO has only one123 item
                        if (isSingleItem) {
                                // Header and item in single request
                                String poBodySingleUrlTemp = String.format(
                                                "/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrderItem(PurchaseOrder='%s',PurchaseOrderItem='%s')/to_PurchaseOrder",
                                                node.path("PurchaseOrder").asText(),
                                                node.path("PurchaseOrderItem").get(0).asText());

                                String poBodySingleUrl = UriComponentsBuilder.newInstance()
                                                .scheme("https")
                                                .host("my200132.s4hana.sapcloud.cn")
                                                .path(poBodySingleUrlTemp)
                                                .queryParam("$expand",
                                                                "to_PurchaseOrderNote,to_PurchaseOrderItem/to_ScheduleLine/to_SubcontractingComponent,to_PurchaseOrderItem/to_AccountAssignment,to_PurchaseOrderItem/to_PurchaseOrderItemNote")
                                                .build()
                                                .toUriString();

                                JsonNode poBodySingle = sapCloudODataClient.executeRequest(
                                                HttpMethod.GET,
                                                poBodySingleUrl,
                                                null,
                                                SAPCloudODataClient.SAPCommUser.PURCHASE_ORDER);

                                poBodyResults.add(poBodySingle);

                        } else {
                                // Header
                                String poBodyMultiHeaderUrlTemp = String.format(
                                                "/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrder('%s')",
                                                node.path("PurchaseOrder").asText());

                                String poBodyMultiHeaderUrl = UriComponentsBuilder.newInstance()
                                                .scheme("https")
                                                .host("my200132.s4hana.sapcloud.cn")
                                                .path(poBodyMultiHeaderUrlTemp)
                                                .queryParam("$expand",
                                                                "to_PurchaseOrderNote")
                                                .build()
                                                .toUriString();

                                JsonNode poBodyMultiHeader = sapCloudODataClient.executeRequest(
                                                HttpMethod.GET,
                                                poBodyMultiHeaderUrl,
                                                null,
                                                SAPCloudODataClient.SAPCommUser.PURCHASE_ORDER);

                                // Item
                                String poBodyMultiItemFilter = node.path("PurchaseOrderItem")
                                                .valueStream()
                                                .map(po -> String.format(
                                                                "(PurchaseOrder eq '%s' and PurchaseOrderItem eq '%s')",
                                                                node.path("PurchaseOrder").asText(),
                                                                po))
                                                .collect(Collectors.joining(" or "));

                                String poBodyMultiItemUrl = UriComponentsBuilder.newInstance()
                                                .scheme("https")
                                                .host("my200132.s4hana.sapcloud.cn")
                                                .path("/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrderItem")
                                                .queryParam("$filter", poBodyMultiItemFilter)
                                                .queryParam("$expand",
                                                                "to_ScheduleLine/to_SubcontractingComponent,to_AccountAssignment,to_PurchaseOrderItemNote")
                                                .build()
                                                .toUriString();

                                JsonNode poBodyMultiItem = sapCloudODataClient.executeRequest(
                                                HttpMethod.GET,
                                                poBodyMultiItemUrl,
                                                null,
                                                SAPCloudODataClient.SAPCommUser.PURCHASE_ORDER);

                                // Combine: Replace to_PurchaseOrderItem in header
                                ObjectNode poBodyMultiCombined = ((ObjectNode) poBodyMultiHeader).deepCopy();
                                poBodyMultiCombined.replace("to_PurchaseOrderItem", poBodyMultiItem);

                                poBodyResults.add(poBodyMultiCombined);
                        }
                });

                // 5. Convert JsonNode to DTO, Clean, and POST
                List<POMapping> poMappingList = new ArrayList<>();

                poBodyResults.forEach(poJsonNode -> {
                        try {
                                // Step 1: Convert JsonNode to DTO
                                PurchaseOrderDTO originalPO = objectMapper.treeToValue(
                                                poJsonNode,
                                                PurchaseOrderDTO.class);

                                // Store original info for mapping
                                String originalPoNumber = originalPO.getPurchaseOrder();
                                String companyCode = originalPO.getCompanyCode();
                                String supplier = originalPO.getSupplier();

                                // Step 2: Clean the DTO for POST
                                PurchaseOrderDTO cleanedPO = PurchaseOrderPostPrepare
                                                .preparePurchaseOrderBody(originalPO);

                                // Step 3: Convert DTO to JSON string
                                String postPayload = objectMapper.writeValueAsString(cleanedPO);

                                // Debug: Print the payload (optional)
                                System.out.println("=== POST PAYLOAD ===");
                                System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                                                .writeValueAsString(cleanedPO));

                                // Step 4: POST to create replica PO
                                String poReplicateUrl = UriComponentsBuilder.newInstance()
                                                .scheme("https")
                                                .host("my200132.s4hana.sapcloud.cn")
                                                .path("/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrder")
                                                .build()
                                                .toUriString();

                                JsonNode poReplicateResponse = sapCloudODataClient.executeRequest(
                                                HttpMethod.POST,
                                                poReplicateUrl,
                                                postPayload,
                                                SAPCloudODataClient.SAPCommUser.PURCHASE_ORDER);

                                // Step 5: Parse response and create mappings
                                PurchaseOrderDTO replicatedPO = objectMapper.treeToValue(
                                                poReplicateResponse,
                                                PurchaseOrderDTO.class);

                                String replicaPoNumber = replicatedPO.getPurchaseOrder();

                                // Map original items to replica items
                                List<PurchaseOrderItemDTO> originalItems = originalPO.getToPurchaseOrderItem()
                                                .getResults();
                                List<PurchaseOrderItemDTO> replicaItems = replicatedPO.getToPurchaseOrderItem()
                                                .getResults();

                                for (int i = 0; i < originalItems.size(); i++) {
                                        POMapping mapping = POMapping.create();
                                        mapping.setOriginalPo(originalPoNumber);
                                        mapping.setOriginalPoItem(originalItems.get(i).getPurchaseOrderItem());
                                        mapping.setReplicaPo(replicaPoNumber);
                                        mapping.setReplicaPoItem(replicaItems.get(i).getPurchaseOrderItem());
                                        mapping.setCompanyCode(companyCode);
                                        mapping.setSupplier(supplier);
                                        poMappingList.add(mapping);
                                }

                        } catch (Exception e) {
                                System.err.println("Error replicating PO: " + e.getMessage());
                                throw new BusinessException("Failed to replicate PO " + e);
                        }
                });

                // 6. Insert PO Mappings
                poMappingList.forEach(poMapping -> {
                        genericCqnService.insertPoMapping(poMapping);
                });

                return poMappingList.isEmpty() ? null : poMappingList.get(0);
        }
}