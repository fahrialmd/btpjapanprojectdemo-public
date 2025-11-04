package customer.btpjapanprojectdemo.util;

import customer.btpjapanprojectdemo.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrderPostPrepare {

        /*
         * Main method for preparing the JSON body for replicating subcontract PO
         */
        public static PurchaseOrderDTO preparePurchaseOrderBody(PurchaseOrderDTO source) {
                String originalPO = source.getPurchaseOrder();

                PurchaseOrderDTO cleaned = source.toBuilder()
                                .build();
                cleaned.setPurchaseOrder("");
                cleaned.setPurchasingProcessingStatus(null);
                cleaned.setCreatedByUser(null);
                cleaned.setCreationDate(null);
                cleaned.setLastChangeDateTime(null);
                cleaned.setPurchasingDocumentOrigin(null);
                cleaned.setPurchasingDocumentDeletionCode(null);
                cleaned.setPurchasingCompletenessStatus(null);
                cleaned.setPurchaseOrderSubtype(null);
                cleaned.setToPurchaseOrderNote(createPurchaseOrderNote(originalPO));
                if (source.getToPurchaseOrderItem() != null) {
                        cleaned.setToPurchaseOrderItem(cleanPurchaseOrderItems(source.getToPurchaseOrderItem()));
                }
                return cleaned;
        }

        /*
         * Method for recursively process multiple line of PO item
         */
        private static PurchaseOrderDTO.NavigationResults<PurchaseOrderItemDTO> cleanPurchaseOrderItems(
                        PurchaseOrderDTO.NavigationResults<PurchaseOrderItemDTO> sourceItems) {
                if (sourceItems == null || sourceItems.getResults() == null) {
                        return null;
                }
                List<PurchaseOrderItemDTO> cleanedItems = sourceItems.getResults().stream()
                                .map(PurchaseOrderPostPrepare::cleanPurchaseOrderItem)
                                .collect(Collectors.toList());
                return PurchaseOrderDTO.NavigationResults.<PurchaseOrderItemDTO>builder()
                                .results(cleanedItems)
                                .build();
        }

        /*
         * Method for processing each line of PO Item
         */
        private static PurchaseOrderItemDTO cleanPurchaseOrderItem(PurchaseOrderItemDTO source) {
                String originalPO = source.getPurchaseOrder();
                String originalPOItem = source.getPurchaseOrderItem();
                PurchaseOrderItemDTO cleaned = source.toBuilder().build();
                cleaned.setPurchaseOrderItemCategory("0");
                cleaned.setAccountAssignmentCategory("K");
                cleaned.setPurchasingDocumentDeletionCode(null);
                cleaned.setIsCompletelyDelivered(null);
                cleaned.setIsFinallyInvoiced(null);
                cleaned.setDeliveryAddressID(null);
                cleaned.setDeliveryAddressFullName(null);
                cleaned.setReferenceDeliveryAddressID(null);
                cleaned.setToPurchaseOrderItemNote(createPurchaseOrderItemNote(originalPO, originalPOItem));
                // Create new dummy account assignment
                cleaned.setToAccountAssignment(createAccountAssignment(cleaned));
                // Clean schedule lines
                if (source.getToScheduleLine() != null) {
                        cleaned.setToScheduleLine(cleanScheduleLines(source.getToScheduleLine()));
                }
                cleaned.setPurchaseOrder(null);
                cleaned.setPurchaseOrderItem(null);
                return cleaned;
        }

        /*
         * Method for recursively process multiple line of PO item's Schedule line
         */
        private static PurchaseOrderDTO.NavigationResults<ScheduleLineDTO> cleanScheduleLines(
                        PurchaseOrderDTO.NavigationResults<ScheduleLineDTO> sourceLines) {
                if (sourceLines == null || sourceLines.getResults() == null) {
                        return null;
                }
                List<ScheduleLineDTO> cleanedLines = sourceLines.getResults().stream()
                                .map(PurchaseOrderPostPrepare::cleanScheduleLine)
                                .collect(Collectors.toList());
                return PurchaseOrderDTO.NavigationResults.<ScheduleLineDTO>builder()
                                .results(cleanedLines)
                                .build();
        }

        /*
         * Method for processing each line of PO item's Schedule line
         */
        private static ScheduleLineDTO cleanScheduleLine(ScheduleLineDTO source) {
                ScheduleLineDTO cleaned = source.toBuilder().build();
                cleaned.setPurchasingDocument(null);
                cleaned.setPurchasingDocumentItem(null);
                cleaned.setScheduleLineDeliveryTime(null);
                cleaned.setSchedLineStscDeliveryDate(null);
                cleaned.setPurchaseRequisition(null);
                cleaned.setPurchaseRequisitionItem(null);
                cleaned.setScheduleLineCommittedQuantity(null);
                cleaned.setPerformancePeriodStartDate(null);
                cleaned.setPerformancePeriodEndDate(null);
                cleaned.setToSubcontractingComponent(null);
                return cleaned;
        }

        /*
         * Method for creating dummy account assignment
         */
        private static PurchaseOrderDTO.NavigationResults<AccountAssignmentDTO> createAccountAssignment(
                        PurchaseOrderItemDTO source) {
                AccountAssignmentDTO accountAssignment = AccountAssignmentDTO.builder()
                                .purchaseOrderItem(source.getPurchaseOrderItem())
                                .accountAssignmentNumber("1")
                                .costCenter("0013101101")
                                .glAccount("0051600000")
                                .quantity(source.getOrderQuantity())
                                .build();
                return PurchaseOrderDTO.NavigationResults.<AccountAssignmentDTO>builder()
                                .results(List.of(accountAssignment))
                                .build();
        }

        /*
         * Method for creating purchase order header note
         */
        private static PurchaseOrderDTO.NavigationResults<PurchaseOrderNoteDTO> createPurchaseOrderNote(
                        String originalPO) {
                String headerNoteText = "Original PO: " + originalPO;

                PurchaseOrderNoteDTO poHeaderNote = PurchaseOrderNoteDTO.builder()
                                .textObjectType("F01")
                                .language("E")
                                .plainLongText(headerNoteText)
                                .build();

                return PurchaseOrderDTO.NavigationResults.<PurchaseOrderNoteDTO>builder()
                                .results(List.of(poHeaderNote))
                                .build();
        }

        /*
         * Method for creating purchase order item note
         */
        private static PurchaseOrderDTO.NavigationResults<PurchaseOrderItemNoteDTO> createPurchaseOrderItemNote(
                        String originalPO, String originalPOItem) {
                String headerNoteText = "Original PO: " + originalPO + " and "
                                + originalPOItem;

                PurchaseOrderItemNoteDTO poItemNote = PurchaseOrderItemNoteDTO.builder()
                                .textObjectType("F03")
                                .language("E")
                                .plainLongText(headerNoteText)
                                .build();

                return PurchaseOrderDTO.NavigationResults.<PurchaseOrderItemNoteDTO>builder()
                                .results(List.of(poItemNote))
                                .build();
        }
}