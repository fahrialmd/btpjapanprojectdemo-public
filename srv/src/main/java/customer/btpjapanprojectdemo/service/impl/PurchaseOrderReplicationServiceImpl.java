package customer.btpjapanprojectdemo.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        private final SAPCloudODataClient sapCloudODataClient;

        public PurchaseOrderReplicationServiceImpl(
                        GenericCqnService genericCqnService,
                        SAPCloudODataClient sapCloudODataClient) {
                this.restTemplate = new RestTemplate();
                this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                this.genericCqnService = genericCqnService;
                this.objectMapper = new ObjectMapper();
                this.objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
                this.sapCloudODataClient = sapCloudODataClient;
        }

        /*
         * Business Process of replicating subcontract purchase order
         */
        @Override
        public List<POMapping> startReplicatingPO(StartReplicatingPOContext context) {

                // 1. Get existing mapped PO numbers
                JsonNode mappedPoNumbers = genericCqnService.getPOHeaderItemOriginalIds();

                // 2. Get unmapped PO numbers from SAP
                JsonNode unmappedPoNumbers = getSubcontractPoFromSap(mappedPoNumbers);

                // 3. Group allPoNumbers by PurchaseOrder
                ArrayNode unmappedPoNumbersGrouped = groupedUnmappedPoNumbers(unmappedPoNumbers);

                // 4. Get the PO Body for replication
                ArrayNode poBodyResults = getPoBodyFromSap(unmappedPoNumbersGrouped);

                // 5. POST/Replicate PO
                List<POMapping> poMappingList = executeReplicatePo(poBodyResults);

                // 6. Persist PO mappings
                poMappingList.forEach(poMapping -> {
                        genericCqnService.insertPoMapping(poMapping);
                });

                return poMappingList.isEmpty() ? null : poMappingList;
        }

        private JsonNode getSubcontractPoFromSap(JsonNode mappedPoNumbers) {
                // Creating filter string for API request
                String completeFilter = "PurchaseOrderItemCategory eq '3'";
                if (mappedPoNumbers.size() > 0) {
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
                // Creating API URI
                String allPoNumbersUrl = UriComponentsBuilder.newInstance()
                                .scheme("https")
                                .host("my200132.s4hana.sapcloud.cn")
                                .path("/sap/opu/odata/sap/API_PURCHASEORDER_PROCESS_SRV/A_PurchaseOrderItem")
                                .queryParam("$select", "PurchaseOrder,PurchaseOrderItem")
                                .queryParam("$filter", completeFilter)
                                .queryParam("$orderby", "PurchaseOrder")
                                .build()
                                .toUriString();
                // Execute API Request
                JsonNode allPoNumbers = sapCloudODataClient.executeRequest(
                                HttpMethod.GET,
                                allPoNumbersUrl,
                                null,
                                SAPCloudODataClient.SAPCommUser.PURCHASE_ORDER);
                if (allPoNumbers == null || allPoNumbers.isEmpty()) {
                        throw new BusinessException("There is no subcontract purchase order to be processed");
                }
                // Logic to only process one PO header at a time
                ArrayNode allPoNumbersFiltered = objectMapper.createArrayNode();
                String firstPurchaseOrder = allPoNumbers.get(0).path("PurchaseOrder").asText();
                allPoNumbers.forEach(node -> {
                        if (firstPurchaseOrder.equals(node.path("PurchaseOrder").asText())) {
                                allPoNumbersFiltered.add(node);
                        }
                });
                return allPoNumbersFiltered;

        }

        private ArrayNode groupedUnmappedPoNumbers(JsonNode unmappedPoNumbers) {
                Map<String, List<String>> poNumbersMap = new LinkedHashMap<>();
                ArrayNode poNumbersGroupedResult = objectMapper.createArrayNode();
                // Group the PO numbers as linked hash map
                unmappedPoNumbers.forEach(node -> {
                        String purchaseOrderNumber = node.path("PurchaseOrder").asText();
                        String purchaseOrderItemNumber = node.path("PurchaseOrderItem").asText();

                        poNumbersMap.computeIfAbsent(purchaseOrderNumber, k -> new ArrayList<>())
                                        .add(purchaseOrderItemNumber);
                });
                // Transform the hash map as a JSON
                poNumbersMap.forEach((poNumber, items) -> {
                        ObjectNode poObject = objectMapper.createObjectNode();
                        ArrayNode itemsArray = objectMapper.createArrayNode();
                        poObject.put("PurchaseOrder", poNumber);
                        items.forEach(itemsArray::add);
                        poObject.set("PurchaseOrderItem", itemsArray);
                        poNumbersGroupedResult.add(poObject);
                });
                return poNumbersGroupedResult;
        }

        private ArrayNode getPoBodyFromSap(ArrayNode unmappedPoNumbersGrouped) {

                ArrayNode poBodyResults = objectMapper.createArrayNode();
                unmappedPoNumbersGrouped.forEach(node -> {
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

                        ObjectNode navigationResultsWrapper = objectMapper.createObjectNode();
                        navigationResultsWrapper.set("results", poBodyMultiItem);

                        // Combine: Replace to_PurchaseOrderItem in header
                        ObjectNode poBodyMultiCombined = ((ObjectNode) poBodyMultiHeader).deepCopy();
                        poBodyMultiCombined.replace("to_PurchaseOrderItem", navigationResultsWrapper);
                        poBodyResults.add(poBodyMultiCombined);
                });
                return poBodyResults;
        }

        private List<POMapping> executeReplicatePo(ArrayNode poBodyResults) {
                List<POMapping> poMappingList = new ArrayList<>();
                poBodyResults.forEach(poJsonNode -> {
                        try {
                                // Step 1: Convert JsonNode to DTO
                                PurchaseOrderDTO originalPO = objectMapper.treeToValue(
                                                poJsonNode,
                                                PurchaseOrderDTO.class);

                                // Step 2: Store original info for mapping
                                String originalPoNumber = originalPO.getPurchaseOrder();
                                String companyCode = originalPO.getCompanyCode();
                                String supplier = originalPO.getSupplier();

                                // Step 3: Clean the DTO for POST and Create dummy account assignment
                                PurchaseOrderDTO cleanedPO = PurchaseOrderPostPrepare
                                                .preparePurchaseOrderBody(originalPO);

                                // Step 3: Convert DTO to JSON string
                                String postPayload = objectMapper.writeValueAsString(cleanedPO);

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

                                // Step 6: Map original items to replica items
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
                                throw new BusinessException("Failed to replicate PO: " + e.getMessage());
                        }
                });

                return poMappingList;
        }
}