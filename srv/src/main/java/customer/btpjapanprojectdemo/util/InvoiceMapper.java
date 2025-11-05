package customer.btpjapanprojectdemo.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import cds.gen.mainservice.SupplierInvoice;
import customer.btpjapanprojectdemo.model.InvoiceGetResponseDTO;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.Result;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.ToSuplrInvcItemPurOrdRef;
import customer.btpjapanprojectdemo.model.InvoicePostRequestDTO.ToSupplierInvoiceItmAcctAssgmt;

public class InvoiceMapper {
    public static SupplierInvoice invoiceGetResponseDTOtoSupplierInvoice(InvoiceGetResponseDTO invoiceGetResponseDTO) {
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

    public static InvoicePostRequestDTO getResponseToPostRequest(InvoiceGetResponseDTO invoiceGetResponseDTO) {
        InvoicePostRequestDTO invoicePostRequestDTO = new InvoicePostRequestDTO();

        invoicePostRequestDTO.setCompanyCode(invoiceGetResponseDTO.getCompanyCode());
        invoicePostRequestDTO.setDocumentDate(invoiceGetResponseDTO.getDocumentDate());
        invoicePostRequestDTO.setPostingDate(invoiceGetResponseDTO.getPostingDate());
        invoicePostRequestDTO
                .setSupplierInvoiceIDByInvcgParty(invoiceGetResponseDTO.getSupplierInvoiceIDByInvcgParty());
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
        invoicePostRequestDTO.setIndirectQuotedExchangeRate(invoiceGetResponseDTO.getIndirectQuotedExchangeRate());
        invoicePostRequestDTO.setDirectQuotedExchangeRate(invoiceGetResponseDTO.getDirectQuotedExchangeRate());
        invoicePostRequestDTO
                .setStateCentralBankPaymentReason(invoiceGetResponseDTO.getStateCentralBankPaymentReason());
        invoicePostRequestDTO.setSupplyingCountry(invoiceGetResponseDTO.getSupplyingCountry());
        invoicePostRequestDTO.setPaymentMethod(invoiceGetResponseDTO.getPaymentMethod());
        invoicePostRequestDTO.setPaymentMethodSupplement(invoiceGetResponseDTO.getPaymentMethodSupplement());
        invoicePostRequestDTO.setPaymentReference(invoiceGetResponseDTO.getPaymentReference());
        invoicePostRequestDTO.setInvoiceReference(invoiceGetResponseDTO.getInvoiceReference());
        invoicePostRequestDTO.setInvoiceReferenceFiscalYear(invoiceGetResponseDTO.getInvoiceReferenceFiscalYear());
        invoicePostRequestDTO.setFixedCashDiscount(invoiceGetResponseDTO.getFixedCashDiscount());
        invoicePostRequestDTO.setUnplannedDeliveryCostTaxCode(invoiceGetResponseDTO.getUnplannedDeliveryCostTaxCode());
        invoicePostRequestDTO
                .setUnplndDelivCostTaxJurisdiction(invoiceGetResponseDTO.getUnplndDelivCostTaxJurisdiction());
        invoicePostRequestDTO.setUnplndDeliveryCostTaxCountry(invoiceGetResponseDTO.getUnplndDeliveryCostTaxCountry());
        invoicePostRequestDTO.setAssignmentReference(invoiceGetResponseDTO.getAssignmentReference());
        invoicePostRequestDTO.setSupplierPostingLineItemText(invoiceGetResponseDTO.getSupplierPostingLineItemText());
        invoicePostRequestDTO.setTaxIsCalculatedAutomatically(invoiceGetResponseDTO.isTaxIsCalculatedAutomatically());
        invoicePostRequestDTO.setBusinessPlace(invoiceGetResponseDTO.getBusinessPlace());
        invoicePostRequestDTO.setBusinessSectionCode(invoiceGetResponseDTO.getBusinessSectionCode());
        invoicePostRequestDTO.setBusinessArea(invoiceGetResponseDTO.getBusinessArea());
        invoicePostRequestDTO
                .setSuplrInvcIsCapitalGoodsRelated(invoiceGetResponseDTO.isSuplrInvcIsCapitalGoodsRelated());
        invoicePostRequestDTO.setSupplierInvoiceIsCreditMemo(invoiceGetResponseDTO.getSupplierInvoiceIsCreditMemo());
        invoicePostRequestDTO.setPaytSlipWthRefSubscriber(invoiceGetResponseDTO.getPaytSlipWthRefSubscriber());
        invoicePostRequestDTO.setPaytSlipWthRefCheckDigit(invoiceGetResponseDTO.getPaytSlipWthRefCheckDigit());
        invoicePostRequestDTO.setPaytSlipWthRefReference(invoiceGetResponseDTO.getPaytSlipWthRefReference());
        invoicePostRequestDTO.setTaxDeterminationDate(invoiceGetResponseDTO.getTaxDeterminationDate());
        invoicePostRequestDTO.setTaxReportingDate(invoiceGetResponseDTO.getTaxReportingDate());
        invoicePostRequestDTO.setTaxFulfillmentDate(invoiceGetResponseDTO.getTaxFulfillmentDate());
        invoicePostRequestDTO.setInvoiceReceiptDate(invoiceGetResponseDTO.getInvoiceReceiptDate());
        invoicePostRequestDTO
                .setDeliveryOfGoodsReportingCntry(invoiceGetResponseDTO.getDeliveryOfGoodsReportingCntry());
        invoicePostRequestDTO.setSupplierVATRegistration(invoiceGetResponseDTO.getSupplierVATRegistration());
        invoicePostRequestDTO.setEuTriangularDeal(invoiceGetResponseDTO.isEUTriangularDeal());
        invoicePostRequestDTO
                .setSuplrInvcDebitCrdtCodeDelivery(invoiceGetResponseDTO.getSuplrInvcDebitCrdtCodeDelivery());
        invoicePostRequestDTO
                .setSuplrInvcDebitCrdtCodeReturns(invoiceGetResponseDTO.getSuplrInvcDebitCrdtCodeReturns());
        invoicePostRequestDTO.setRetentionDueDate(invoiceGetResponseDTO.getRetentionDueDate());
        invoicePostRequestDTO.setPaymentReason(invoiceGetResponseDTO.getPaymentReason());
        invoicePostRequestDTO.setHouseBank(invoiceGetResponseDTO.getHouseBank());
        invoicePostRequestDTO.setHouseBankAccount(invoiceGetResponseDTO.getHouseBankAccount());
        invoicePostRequestDTO.setAlternativePayeePayer(invoiceGetResponseDTO.getAlternativePayeePayer());
        invoicePostRequestDTO.setIn_GSTPartner(invoiceGetResponseDTO.getIN_GSTPartner());
        invoicePostRequestDTO.setIn_GSTPlaceOfSupply(invoiceGetResponseDTO.getIN_GSTPlaceOfSupply());
        invoicePostRequestDTO.setIn_InvoiceReferenceNumber(invoiceGetResponseDTO.getIN_InvoiceReferenceNumber());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef1());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate1());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef2());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate2());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef3(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef3());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate3(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate3());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef4(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef4());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate4(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate4());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificRef5(invoiceGetResponseDTO.getJrnlEntryCntrySpecificRef5());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificDate5(invoiceGetResponseDTO.getJrnlEntryCntrySpecificDate5());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificBP1(invoiceGetResponseDTO.getJrnlEntryCntrySpecificBP1());
        invoicePostRequestDTO.setJrnlEntryCntrySpecificBP2(invoiceGetResponseDTO.getJrnlEntryCntrySpecificBP2());

        invoicePostRequestDTO.setTo_BR_SupplierInvoiceNFDocument(null);

        // Set to_SuplrInvcItemPurOrdRef
        ArrayList<Result> poRefList = new ArrayList<>();

        invoiceGetResponseDTO.getTo_SuplrInvcItemPurOrdRef().getResults().forEach(poRefResponse -> {
            Result poRefRequest = new Result();

            poRefRequest.setSupplierInvoiceItem(poRefResponse.getSupplierInvoiceItem());
            poRefRequest.setPurchaseOrder(poRefResponse.getPurchaseOrder());
            poRefRequest.setPurchaseOrderItem(poRefResponse.getPurchaseOrderItem());
            poRefRequest.setPlant(poRefResponse.getPlant());
            poRefRequest.setReferenceDocument(poRefResponse.getReferenceDocument());
            poRefRequest.setReferenceDocumentFiscalYear(poRefResponse.getReferenceDocumentFiscalYear());
            poRefRequest.setReferenceDocumentItem(poRefResponse.getReferenceDocumentItem());
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

            ToSupplierInvoiceItmAcctAssgmt toSupplierInvoiceItmAcctAssgmt = invoicePostRequestDTO.new ToSupplierInvoiceItmAcctAssgmt();
            toSupplierInvoiceItmAcctAssgmt.results = new ArrayList<>();

            poRefRequest.setTo_SupplierInvoiceItmAcctAssgmt(toSupplierInvoiceItmAcctAssgmt);

            poRefList.add(poRefRequest);
        });


    ToSuplrInvcItemPurOrdRef invoicePORef = invoicePostRequestDTO.new ToSuplrInvcItemPurOrdRef();
    invoicePORef.setResults(poRefList);

    invoicePostRequestDTO.setTo_SuplrInvcItemPurOrdRef(invoicePORef);

    return invoicePostRequestDTO;
    }

    public static LocalDate convertJsonToDate(String jsonEpochDate) {
        String dateEpoch = jsonEpochDate.replaceAll("[^0-9]", "");
        Instant dateinstant = Instant.ofEpochMilli(Long.valueOf(dateEpoch));
        LocalDate date = dateinstant.atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }
}
