package customer.btpjapanprojectdemo.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import cds.gen.mainservice.SupplierInvoice;
import customer.btpjapanprojectdemo.model.InvoiceDTO;
import customer.btpjapanprojectdemo.model.InvoiceDTO.SuplrInvcItemPurOrdRefResult;
import customer.btpjapanprojectdemo.model.InvoiceDTO.SupplierInvoiceItmAcctAssgmtResult;
import customer.btpjapanprojectdemo.model.InvoiceDTO.ToSuplrInvcItemPurOrdRef;
import customer.btpjapanprojectdemo.model.InvoiceDTO.ToSupplierInvoiceItmAcctAssgmt;
import customer.btpjapanprojectdemo.model.MaterialDocumentItemKeyDTO;

public class InvoiceMapper {
    public static SupplierInvoice invoiceGetResponseDTOtoSupplierInvoice(
            InvoiceDTO invoiceGetResponseDTO) {
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

    public static InvoiceDTO getResponseToPostRequest(
            InvoiceDTO invoiceGetResponseDTO,
            HashMap<String, String> poRepToOriList,
            HashMap<String, HashMap<String, MaterialDocumentItemKeyDTO>> matdocMap) {

        InvoiceDTO invoicePostRequestDTO = invoiceGetResponseDTO.toBuilder().build();

        // Set to_SuplrInvcItemPurOrdRef
        ArrayList<SuplrInvcItemPurOrdRefResult> poRefList = responsePORefToRequestPORef(invoiceGetResponseDTO,
                poRepToOriList, matdocMap);
        invoicePostRequestDTO.setTo_SuplrInvcItemPurOrdRef(ToSuplrInvcItemPurOrdRef.builder().results(poRefList).build());

        return invoicePostRequestDTO;
    }

    public static ArrayList<SuplrInvcItemPurOrdRefResult> responsePORefToRequestPORef(
            InvoiceDTO invoiceGetResponseDTO, 
            HashMap<String, String> poRepToOriList,
            HashMap<String, HashMap<String, MaterialDocumentItemKeyDTO>> matdocMap) {
        ArrayList<SuplrInvcItemPurOrdRefResult> poRefList = new ArrayList<>();

        invoiceGetResponseDTO.getTo_SuplrInvcItemPurOrdRef().getResults().forEach(poRefResponse -> {
            SuplrInvcItemPurOrdRefResult poRefRequest = poRefResponse.toBuilder().build();

            // map PO repli to PO ori
            String oriPO = poRepToOriList.get(poRefResponse.getPurchaseOrder());
            poRefRequest.setPurchaseOrder(oriPO);

            // map Matdoc Repli to Matdoc Ori
            MaterialDocumentItemKeyDTO matdoc = matdocMap.get(oriPO).get(poRefResponse.getPurchaseOrderItem());
            poRefRequest.setReferenceDocument(matdoc.getMaterialDocument());
            poRefRequest.setReferenceDocumentFiscalYear(matdoc.getMaterialDocumentYear());
            poRefRequest.setReferenceDocumentItem(matdoc.getMaterialDocumentItem());

            ArrayList<SupplierInvoiceItmAcctAssgmtResult> actAssgList = responseActAssgTorequestActAssg(
                    poRefResponse);

            ToSupplierInvoiceItmAcctAssgmt toSupplierInvoiceItmAcctAssgmt = new ToSupplierInvoiceItmAcctAssgmt();
            toSupplierInvoiceItmAcctAssgmt.setResults(actAssgList);

            poRefRequest.setTo_SupplierInvoiceItmAcctAssgmt(toSupplierInvoiceItmAcctAssgmt);

            poRefList.add(poRefRequest);
        });

        return poRefList;
    }

    public static ArrayList<SupplierInvoiceItmAcctAssgmtResult> responseActAssgTorequestActAssg(
            SuplrInvcItemPurOrdRefResult poRefResponse) {
        ArrayList<SupplierInvoiceItmAcctAssgmtResult> actAssgList = new ArrayList<>();

        // poRefResponse.getTo_SupplierInvoiceItmAcctAssgmt().getResults().forEach(actAssgResponse -> {
        //     SupplierInvoiceItmAcctAssgmtResult actAssgRequest = actAssgResponse.toBuilder().build();

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
