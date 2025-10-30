namespace btpjapanprojectdemo;

using {
    managed,
    cuid,
    sap.common.CodeList,
    User
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
    invoice_log    : String(40);
    PO             : String(40);
    posting_period : String(40);
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
