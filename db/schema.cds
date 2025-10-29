namespace btpjapanprojectdemo;

using {
    managed,
    cuid,
    sap.common.CodeList,
    User
} from '@sap/cds/common';

entity POMapping : cuid, managed {
    company_code : String(40);
    po_number    : String(40);
    item         : String(40);
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
