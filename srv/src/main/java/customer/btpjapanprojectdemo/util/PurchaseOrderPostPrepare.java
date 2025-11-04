package customer.btpjapanprojectdemo.util;

import customer.btpjapanprojectdemo.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrderPostPrepare {

        public static PurchaseOrderDTO cleanForPost(PurchaseOrderDTO source) {
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
                cleaned.setToPurchaseOrderNote(null);
                if (source.getToPurchaseOrderItem() != null) {
                        cleaned.setToPurchaseOrderItem(cleanItems(source.getToPurchaseOrderItem()));
                }
                return cleaned;
        }

        private static PurchaseOrderDTO.NavigationResults<PurchaseOrderItemDTO> cleanItems(
                        PurchaseOrderDTO.NavigationResults<PurchaseOrderItemDTO> sourceItems) {
                if (sourceItems == null || sourceItems.getResults() == null) {
                        return null;
                }
                List<PurchaseOrderItemDTO> cleanedItems = sourceItems.getResults().stream()
                                .map(PurchaseOrderPostPrepare::cleanItem)
                                .collect(Collectors.toList());
                return PurchaseOrderDTO.NavigationResults.<PurchaseOrderItemDTO>builder()
                                .results(cleanedItems)
                                .build();
        }

        private static PurchaseOrderItemDTO cleanItem(PurchaseOrderItemDTO source) {
                PurchaseOrderItemDTO cleaned = source.toBuilder().build();
                cleaned.setPurchaseOrder(null);
                cleaned.setPurchaseOrderItemCategory("0");
                cleaned.setAccountAssignmentCategory("K");
                cleaned.setPurchasingDocumentDeletionCode(null);
                cleaned.setIsCompletelyDelivered(null);
                cleaned.setIsFinallyInvoiced(null);
                cleaned.setToPurchaseOrderItemNote(null);
                // Create new dummy account assignment
                cleaned.setToAccountAssignment(createAccountAssignment(cleaned));
                // Clean schedule lines
                if (source.getToScheduleLine() != null) {
                        cleaned.setToScheduleLine(cleanScheduleLines(source.getToScheduleLine()));
                }
                return cleaned;
        }

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
}