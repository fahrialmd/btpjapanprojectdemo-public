package customer.btpjapanprojectdemo.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import cds.gen.mainservice.SupplierInvoice;
import customer.btpjapanprojectdemo.model.InvoiceGetResponseDTO;
import customer.btpjapanprojectdemo.model.InvoiceGetResponseDTO.SuplrInvcItemPurOrdRefResultGet;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.SuplrInvcItemPurOrdRefResultPost;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.SupplierInvoiceItmAcctAssgmtResultPost;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.ToSuplrInvcItemPurOrdRef;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.ToSupplierInvoiceItmAcctAssgmt;
import customer.btpjapanprojectdemo.model.MaterialDocumentItemKeyDTO;

public class InvoiceMapper {
    public static SupplierInvoice invoiceGetResponseDTOtoSupplierInvoice(
            InvoiceGetResponseDTO invoiceGetResponseDTO) {
        SupplierInvoice supplierInvoice = SupplierInvoice.create();

        supplierInvoice.setInvoiceNo(invoiceGetResponseDTO.getSupplierInvoice());
        supplierInvoice.setFiscalYear(invoiceGetResponseDTO.getFiscalYear());
        supplierInvoice.setInvoicingParty(invoiceGetResponseDTO.getInvoicingParty());
        supplierInvoice.setCompanyCode(invoiceGetResponseDTO.getCompanyCode());

        BigDecimal grossInvoice = new BigDecimal(invoiceGetResponseDTO.getInvoiceGrossAmount());
        supplierInvoice.setGrossInvoice(grossInvoice);

        supplierInvoice.setDocumentCurrencyCode(invoiceGetResponseDTO.getDocumentCurrency());
        supplierInvoice.setReference(invoiceGetResponseDTO.getSupplierInvoiceIDByInvcgParty());

        supplierInvoice.setPostingDate(convertJsonToDate(invoiceGetResponseDTO.getPostingDate()));
        supplierInvoice.setInvoiceDate(convertJsonToDate(invoiceGetResponseDTO.getDocumentDate()));

        return supplierInvoice;
    }

    public static InvoicePostRequestDTO getResponseToPostRequest(
            InvoiceGetResponseDTO invoiceGetResponseDTO,
            HashMap<String, String> poRepToOriList,
            HashMap<String, HashMap<String, MaterialDocumentItemKeyDTO>> matdocMap) {
        InvoicePostRequestDTO invoicePostRequestDTO = new InvoicePostRequestDTO();

        invoicePostRequestDTO.setCompanyCode(invoiceGetResponseDTO.getCompanyCode());
        invoicePostRequestDTO.setDocumentDate(invoiceGetResponseDTO.getDocumentDate());
        invoicePostRequestDTO.setPostingDate(invoiceGetResponseDTO.getPostingDate());
        invoicePostRequestDTO
                .setSupplierInvoiceIDByInvcgParty(
                        invoiceGetResponseDTO.getSupplierInvoiceIDByInvcgParty());
        invoicePostRequestDTO.setInvoicingParty(invoiceGetResponseDTO.getInvoicingParty());
        invoicePostRequestDTO.setDocumentCurrency(invoiceGetResponseDTO.getDocumentCurrency());
        invoicePostRequestDTO.setInvoiceGrossAmount(invoiceGetResponseDTO.getInvoiceGrossAmount());
        invoicePostRequestDTO.setUnplannedDeliveryCost(invoiceGetResponseDTO.getUnplannedDeliveryCost());
        invoicePostRequestDTO.setDocumentHeaderText(invoiceGetResponseDTO.getDocumentHeaderText());
        invoicePostRequestDTO.setReconciliationAccount(invoiceGetResponseDTO.getReconciliationAccount());
        invoicePostRequestDTO.setManualCashDiscount(invoiceGetResponseDTO.getManualCashDiscount());
        invoicePostRequestDTO.setPaymentTerms(invoiceGetResponseDTO.getPaymentTerms());
        invoicePostRequestDTO.setDueCalculationBaseDate(invoiceGetResponseDTO.getDueCalculationBaseDate());
        invoicePostRequestDTO.setCashDiscount1Percent(invoiceGetResponseDTO.getCashDiscount1Percent());
        invoicePostRequestDTO.setCashDiscount1Days(invoiceGetResponseDTO.getCashDiscount1Days());
        invoicePostRequestDTO.setCashDiscount2Percent(invoiceGetResponseDTO.getCashDiscount2Percent());
        invoicePostRequestDTO.setCashDiscount2Days(invoiceGetResponseDTO.getCashDiscount2Days());
        invoicePostRequestDTO.setNetPaymentDays(invoiceGetResponseDTO.getNetPaymentDays());
        invoicePostRequestDTO.setPaymentBlockingReason(invoiceGetResponseDTO.getPaymentBlockingReason());
        invoicePostRequestDTO.setAccountingDocumentType(invoiceGetResponseDTO.getAccountingDocumentType());
        invoicePostRequestDTO.setBpBankAccountInternalID(invoiceGetResponseDTO.getBPBankAccountInternalID());
        invoicePostRequestDTO.setSupplierInvoiceStatus(invoiceGetResponseDTO.getSupplierInvoiceStatus());
        invoicePostRequestDTO
                .setIndirectQuotedExchangeRate(invoiceGetResponseDTO.getIndirectQuotedExchangeRate());
        invoicePostRequestDTO.setDirectQuotedExchangeRate(invoiceGetResponseDTO.getDirectQuotedExchangeRate());
        invoicePostRequestDTO
                .setStateCentralBankPaymentReason(
                        invoiceGetResponseDTO.getStateCentralBankPaymentReason());
        invoicePostRequestDTO.setSupplyingCountry(invoiceGetResponseDTO.getSupplyingCountry());
        invoicePostRequestDTO.setPaymentMethod(invoiceGetResponseDTO.getPaymentMethod());
        invoicePostRequestDTO.setPaymentMethodSupplement(invoiceGetResponseDTO.getPaymentMethodSupplement());
        invoicePostRequestDTO.setPaymentReference(invoiceGetResponseDTO.getPaymentReference());
        invoicePostRequestDTO.setInvoiceReference(invoiceGetResponseDTO.getInvoiceReference());
        invoicePostRequestDTO
                .setInvoiceReferenceFiscalYear(invoiceGetResponseDTO.getInvoiceReferenceFiscalYear());
        invoicePostRequestDTO.setFixedCashDiscount(invoiceGetResponseDTO.getFixedCashDiscount());
        invoicePostRequestDTO.setUnplannedDeliveryCostTaxCode(
                invoiceGetResponseDTO.getUnplannedDeliveryCostTaxCode());
        invoicePostRequestDTO
                .setUnplndDelivCostTaxJurisdiction(
                        invoiceGetResponseDTO.getUnplndDelivCostTaxJurisdiction());
        invoicePostRequestDTO.setUnplndDeliveryCostTaxCountry(
                invoiceGetResponseDTO.getUnplndDeliveryCostTaxCountry());
        invoicePostRequestDTO.setAssignmentReference(invoiceGetResponseDTO.getAssignmentReference());
        invoicePostRequestDTO
                .setSupplierPostingLineItemText(invoiceGetResponseDTO.getSupplierPostingLineItemText());
        invoicePostRequestDTO.setTaxIsCalculatedAutomatically(
                invoiceGetResponseDTO.isTaxIsCalculatedAutomatically());
        invoicePostRequestDTO.setBusinessPlace(invoiceGetResponseDTO.getBusinessPlace());
        invoicePostRequestDTO.setBusinessSectionCode(invoiceGetResponseDTO.getBusinessSectionCode());
        invoicePostRequestDTO.setBusinessArea(invoiceGetResponseDTO.getBusinessArea());
        invoicePostRequestDTO
                .setSuplrInvcIsCapitalGoodsRelated(
                        invoiceGetResponseDTO.isSuplrInvcIsCapitalGoodsRelated());
        invoicePostRequestDTO
                .setSupplierInvoiceIsCreditMemo(invoiceGetResponseDTO.getSupplierInvoiceIsCreditMemo());
        invoicePostRequestDTO.setPaytSlipWthRefSubscriber(invoiceGetResponseDTO.getPaytSlipWthRefSubscriber());
        invoicePostRequestDTO.setPaytSlipWthRefCheckDigit(invoiceGetResponseDTO.getPaytSlipWthRefCheckDigit());
        invoicePostRequestDTO.setPaytSlipWthRefReference(invoiceGetResponseDTO.getPaytSlipWthRefReference());
        invoicePostRequestDTO.setTaxDeterminationDate(invoiceGetResponseDTO.getTaxDeterminationDate());
        invoicePostRequestDTO.setTaxReportingDate(invoiceGetResponseDTO.getTaxReportingDate());
        invoicePostRequestDTO.setTaxFulfillmentDate(invoiceGetResponseDTO.getTaxFulfillmentDate());
        invoicePostRequestDTO.setInvoiceReceiptDate(invoiceGetResponseDTO.getInvoiceReceiptDate());
        invoicePostRequestDTO
                .setDeliveryOfGoodsReportingCntry(
                        invoiceGetResponseDTO.getDeliveryOfGoodsReportingCntry());
        invoicePostRequestDTO.setSupplierVATRegistration(invoiceGetResponseDTO.getSupplierVATRegistration());
        invoicePostRequestDTO.setEuTriangularDeal(invoiceGetResponseDTO.isEUTriangularDeal());
        invoicePostRequestDTO
                .setSuplrInvcDebitCrdtCodeDelivery(
                        invoiceGetResponseDTO.getSuplrInvcDebitCrdtCodeDelivery());
        invoicePostRequestDTO
                .setSuplrInvcDebitCrdtCodeReturns(
                        invoiceGetResponseDTO.getSuplrInvcDebitCrdtCodeReturns());
        invoicePostRequestDTO.setRetentionDueDate(invoiceGetResponseDTO.getRetentionDueDate());
        invoicePostRequestDTO.setPaymentReason(invoiceGetResponseDTO.getPaymentReason());
        invoicePostRequestDTO.setHouseBank(invoiceGetResponseDTO.getHouseBank());
        invoicePostRequestDTO.setHouseBankAccount(invoiceGetResponseDTO.getHouseBankAccount());
        invoicePostRequestDTO.setAlternativePayeePayer(invoiceGetResponseDTO.getAlternativePayeePayer());
        invoicePostRequestDTO.setIn_GSTPartner(invoiceGetResponseDTO.getIN_GSTPartner());
        invoicePostRequestDTO.setIn_GSTPlaceOfSupply(invoiceGetResponseDTO.getIN_GSTPlaceOfSupply());
        invoicePostRequestDTO
                .setIn_InvoiceReferenceNumber(invoiceGetResponseDTO.getIN_InvoiceReferenceNumber());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificRef1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef1());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificDate1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate1());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificRef2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef2());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificDate2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate2());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificRef3(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef3());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificDate3(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate3());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificRef4(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef4());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificDate4(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate4());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificRef5(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef5());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificDate5(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate5());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificBP1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificBP1());
        invoicePostRequestDTO
                .setJrnlEntryCntrySpecificBP2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificBP2());

        invoicePostRequestDTO.setTo_BR_SupplierInvoiceNFDocument(null);

        // Set to_SuplrInvcItemPurOrdRef

        ArrayList<SuplrInvcItemPurOrdRefResultPost> poRefList = responsePORefToRequestPORef(invoiceGetResponseDTO,
                poRepToOriList, matdocMap);

        ToSuplrInvcItemPurOrdRef invoicePORef = invoicePostRequestDTO.new ToSuplrInvcItemPurOrdRef();
        invoicePORef.setResults(poRefList);

        invoicePostRequestDTO.setTo_SuplrInvcItemPurOrdRef(invoicePORef);

        return invoicePostRequestDTO;
    }

    public static ArrayList<SuplrInvcItemPurOrdRefResultPost> responsePORefToRequestPORef(
            InvoiceGetResponseDTO invoiceGetResponseDTO, 
            HashMap<String, String> poRepToOriList,
            HashMap<String, HashMap<String, MaterialDocumentItemKeyDTO>> matdocMap) {
        ArrayList<SuplrInvcItemPurOrdRefResultPost> poRefList = new ArrayList<>();

        invoiceGetResponseDTO.getTo_SuplrInvcItemPurOrdRef().getResults().forEach(poRefResponse -> {
            SuplrInvcItemPurOrdRefResultPost poRefRequest = new SuplrInvcItemPurOrdRefResultPost();

            poRefRequest.setSupplierInvoiceItem(poRefResponse.getSupplierInvoiceItem());

            // map PO repli to PO ori
            String oriPO = poRepToOriList.get(poRefResponse.getPurchaseOrder());
            poRefRequest.setPurchaseOrder(oriPO);
            poRefRequest.setPurchaseOrderItem(poRefResponse.getPurchaseOrderItem());
            poRefRequest.setPlant(poRefResponse.getPlant());

            // map Matdoc Repli to Matdoc Ori
            MaterialDocumentItemKeyDTO matdoc = matdocMap.get(oriPO).get(poRefResponse.getPurchaseOrderItem());
            poRefRequest.setReferenceDocument(matdoc.getMaterialDocument());
            poRefRequest.setReferenceDocumentFiscalYear(matdoc.getMaterialDocumentYear());
            poRefRequest.setReferenceDocumentItem(matdoc.getMaterialDocumentItem());

            poRefRequest.setIsSubsequentDebitCredit(poRefResponse.getIsSubsequentDebitCredit());
            poRefRequest.setTaxCode(poRefResponse.getTaxCode());
            poRefRequest.setTaxJurisdiction(poRefResponse.getTaxJurisdiction());
            poRefRequest.setDocumentCurrency(poRefResponse.getDocumentCurrency());
            poRefRequest.setSupplierInvoiceItemAmount(poRefResponse.getSupplierInvoiceItemAmount());
            poRefRequest.setPurchaseOrderQuantityUnit(poRefResponse.getPurchaseOrderQuantityUnit());
            poRefRequest.setPurchaseOrderQtyUnitSAPCode(poRefResponse.getPurchaseOrderQtyUnitSAPCode());
            poRefRequest.setPurchaseOrderQtyUnitISOCode(poRefResponse.getPurchaseOrderQtyUnitISOCode());
            poRefRequest.setQuantityInPurchaseOrderUnit(poRefResponse.getQuantityInPurchaseOrderUnit());
            poRefRequest.setPurchaseOrderPriceUnit(poRefResponse.getPurchaseOrderPriceUnit());
            poRefRequest.setPurchaseOrderPriceUnitSAPCode(poRefResponse.getPurchaseOrderPriceUnitSAPCode());
            poRefRequest.setPurchaseOrderPriceUnitISOCode(poRefResponse.getPurchaseOrderPriceUnitISOCode());
            poRefRequest.setQtyInPurchaseOrderPriceUnit(poRefResponse.getQtyInPurchaseOrderPriceUnit());
            poRefRequest.setSuplrInvcDeliveryCostCndnType(poRefResponse.getSuplrInvcDeliveryCostCndnType());
            poRefRequest.setSuplrInvcDeliveryCostCndnStep(poRefResponse.getSuplrInvcDeliveryCostCndnStep());
            poRefRequest.setSuplrInvcDeliveryCostCndnCount(poRefResponse.getSuplrInvcDeliveryCostCndnCount());
            poRefRequest.setSupplierInvoiceItemText(poRefResponse.getSupplierInvoiceItemText());
            poRefRequest.setFreightSupplier(poRefResponse.getFreightSupplier());
            poRefRequest.setNotCashDiscountLiable(poRefResponse.isNotCashDiscountLiable());
            poRefRequest.setRetentionAmountInDocCurrency(poRefResponse.getRetentionAmountInDocCurrency());
            poRefRequest.setRetentionPercentage(poRefResponse.getRetentionPercentage());
            poRefRequest.setRetentionDueDate(poRefResponse.getRetentionDueDate());
            poRefRequest.setSuplrInvcItmIsNotRlvtForRtntn(poRefResponse.isSuplrInvcItmIsNotRlvtForRtntn());
            poRefRequest.setServiceEntrySheet(poRefResponse.getServiceEntrySheet());
            poRefRequest.setServiceEntrySheetItem(poRefResponse.getServiceEntrySheetItem());
            poRefRequest.setTaxCountry(poRefResponse.getTaxCountry());
            poRefRequest.setFinallyInvoiced(poRefResponse.isFinallyInvoiced());
            poRefRequest.setTaxDeterminationDate(poRefResponse.getTaxDeterminationDate());
            poRefRequest.setIn_HSNOrSACCode(poRefResponse.getIN_HSNOrSACCode());
            poRefRequest.setIn_CustomDutyAssessableValue(poRefResponse.getIN_CustomDutyAssessableValue());
            poRefRequest.setNl_ChainLiabilityStartDate(poRefResponse.getNL_ChainLiabilityStartDate());
            poRefRequest.setNl_ChainLiabilityEndDate(poRefResponse.getNL_ChainLiabilityEndDate());
            poRefRequest.setNl_ChainLiabilityDescription(poRefResponse.getNL_ChainLiabilityDescription());
            poRefRequest.setNl_ChainLbltyCnstrctnSiteDesc(poRefResponse.getNL_ChainLbltyCnstrctnSiteDesc());
            poRefRequest.setNl_ChainLiabilityDuration(poRefResponse.getNL_ChainLiabilityDuration());
            poRefRequest.setNl_ChainLiabilityPercent(poRefResponse.getNL_ChainLiabilityPercent());

            ArrayList<SupplierInvoiceItmAcctAssgmtResultPost> actAssgList = responseActAssgTorequestActAssg(
                    poRefResponse);

            ToSupplierInvoiceItmAcctAssgmt toSupplierInvoiceItmAcctAssgmt = new ToSupplierInvoiceItmAcctAssgmt();
            toSupplierInvoiceItmAcctAssgmt.setResults(actAssgList);

            poRefRequest.setTo_SupplierInvoiceItmAcctAssgmt(toSupplierInvoiceItmAcctAssgmt);

            poRefList.add(poRefRequest);
        });

        return poRefList;
    }

    public static ArrayList<SupplierInvoiceItmAcctAssgmtResultPost> responseActAssgTorequestActAssg(
            SuplrInvcItemPurOrdRefResultGet poRefResponse) {
        ArrayList<SupplierInvoiceItmAcctAssgmtResultPost> actAssgList = new ArrayList<>();

        // poRefResponse.getTo_SupplierInvoiceItmAcctAssgmt().getResults().forEach(actAssgResponse -> {
        //     SupplierInvoiceItmAcctAssgmtResultPost actAssgRequest = new SupplierInvoiceItmAcctAssgmtResultPost();

        //     actAssgRequest.setSupplierInvoiceItem(actAssgResponse.getSupplierInvoiceItem());
        //     actAssgRequest.setOrdinalNumber(actAssgResponse.getOrdinalNumber());
        //     actAssgRequest.setCostCenter(actAssgResponse.getCostCenter());
        //     actAssgRequest.setControllingArea(actAssgResponse.getControllingArea());
        //     actAssgRequest.setBusinessArea(actAssgResponse.getBusinessArea());
        //     actAssgRequest.setProfitCenter(actAssgResponse.getProfitCenter());
        //     actAssgRequest.setFunctionalArea(actAssgResponse.getFunctionalArea());
        //     actAssgRequest.setGlAccount(actAssgResponse.getGlAccount());
        //     actAssgRequest.setSalesOrder(actAssgResponse.getSalesOrder());
        //     actAssgRequest.setSalesOrderItem(actAssgResponse.getSalesOrderItem());
        //     actAssgRequest.setCostObject(actAssgResponse.getCostObject());
        //     actAssgRequest.setWbSElement(actAssgResponse.getWbSElement());
        //     actAssgRequest.setDocumentCurrency(actAssgResponse.getDocumentCurrency());
        //     actAssgRequest.setSuplrInvcAcctAssignmentAmount(actAssgResponse.getSuplrInvcAcctAssignmentAmount());
        //     actAssgRequest.setPurchaseOrderQuantityUnit(actAssgResponse.getPurchaseOrderQuantityUnit());
        //     actAssgRequest.setPurchaseOrderQtyUnitSAPCode(actAssgResponse.getPurchaseOrderQtyUnitSAPCode());
        //     actAssgRequest.setPurchaseOrderQtyUnitISOCode(actAssgResponse.getPurchaseOrderQtyUnitISOCode());
        //     actAssgRequest.setQuantity(actAssgResponse.getQuantity());
        //     actAssgRequest.setTaxCode(actAssgResponse.getTaxCode());
        //     actAssgRequest.setAccountAssignmentNumber(actAssgResponse.getAccountAssignmentNumber());
        //     actAssgRequest.setAccountAssignmentIsUnplanned(actAssgResponse.isAccountAssignmentIsUnplanned());
        //     actAssgRequest.setPersonnelNumber(actAssgResponse.getPersonnelNumber());
        //     actAssgRequest.setMasterFixedAsset(actAssgResponse.getMasterFixedAsset());
        //     actAssgRequest.setFixedAsset(actAssgResponse.getFixedAsset());
        //     actAssgRequest.setTaxJurisdiction(actAssgResponse.getTaxJurisdiction());
        //     actAssgRequest.setInternalOrder(actAssgResponse.getInternalOrder());
        //     actAssgRequest.setProjectNetworkInternalID(actAssgResponse.getProjectNetworkInternalID());
        //     actAssgRequest.setNetworkActivityInternalID(actAssgResponse.getNetworkActivityInternalID());
        //     actAssgRequest.setProjectNetwork(actAssgResponse.getProjectNetwork());
        //     actAssgRequest.setNetworkActivity(actAssgResponse.getNetworkActivity());
        //     actAssgRequest.setCommitmentItem(actAssgResponse.getCommitmentItem());
        //     actAssgRequest.setFundsCenter(actAssgResponse.getFundsCenter());
        //     actAssgRequest.setFund(actAssgResponse.getFund());
        //     actAssgRequest.setGrantID(actAssgResponse.getGrantID());
        //     actAssgRequest.setSuplrInvcAccountAssignmentText(actAssgResponse.getSuplrInvcAccountAssignmentText());
        //     actAssgRequest.setPurchaseOrderPriceUnit(actAssgResponse.getPurchaseOrderPriceUnit());
        //     actAssgRequest.setPurchaseOrderPriceUnitSAPCode(actAssgResponse.getPurchaseOrderPriceUnitSAPCode());
        //     actAssgRequest.setPurchaseOrderPriceUnitISOCode(actAssgResponse.getPurchaseOrderPriceUnitISOCode());
        //     actAssgRequest.setQuantityInPurchaseOrderUnit(actAssgResponse.getQuantityInPurchaseOrderUnit());
        //     actAssgRequest.setProfitabilitySegment(actAssgResponse.getProfitabilitySegment());
        //     actAssgRequest.setBudgetPeriod(actAssgResponse.getBudgetPeriod());
        //     actAssgRequest.setTaxCountry(actAssgResponse.getTaxCountry());

        //     actAssgList.add(actAssgRequest);

        // });
        return actAssgList;
    }

    public static LocalDate convertJsonToDate(String jsonEpochDate) {
        String dateEpoch = jsonEpochDate.replaceAll("[^0-9]", "");
        Instant dateinstant = Instant.ofEpochMilli(Long.valueOf(dateEpoch));
        LocalDate date = dateinstant.atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }
}
