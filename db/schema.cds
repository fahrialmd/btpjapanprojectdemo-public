namespace btpjapanprojectdemo;

using {
    managed,
    cuid,
    sap.common.CodeList,
    User,
    Currency
} from '@sap/cds/common';

entity POMapping : cuid, managed {
    original_po      : String(10);
    original_po_item : String(5);
    replica_po       : String(10);
    replica_po_item  : String(5);
    company_code     : String(4);
    supplier         : String(10);
}

entity GoodsMovementLog : cuid, managed {   
    po_number    : String(40);
    material_doc : String(40);
    timestamp    : String(40);
}

entity InvoiceLog : cuid, managed {
    invoice_replicated          : String(10);
    year_replicated             : String(4);
    posting_period_replicated   : String(2);
    PO_replicated               : String(10);

    invoice_original            : String(10);
    year_original               : String(4);
    posting_period_original     : String(2);
    PO_original                 : String(10);

    timestamp : Timestamp;
}

entity ClearingLog : cuid, managed {
    po            : String(40);
    dummy_account : String(40);
    period        : String(40);
}

entity JobHistory : cuid, managed {
    job_id   : String(40);
    status   : String(40);
    duration : String(40);
}

@cds.persistence.skip
entity SupplierInvoice {
    key invoice_no : String(10);
    key fiscal_year : String(4);
    invoicing_party : String(8);
    company_code : String(4);
    @Measures.ISOCurrency : document_currency_code
    gross_invoice : Decimal(13,2);
    @Semantics.currencyCode
    document_currency : Currency;
    posting_date : Date;
    invoice_date : Date;
    reference : String(35);
}
