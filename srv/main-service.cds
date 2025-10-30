using btpjapanprojectdemo from '../db/schema';

service MainService {
    entity POMapping        as projection on btpjapanprojectdemo.POMapping;
    entity GoodsMovementLog as projection on btpjapanprojectdemo.GoodsMovementLog;
    entity InvoiceLog       as projection on btpjapanprojectdemo.InvoiceLog;
    entity JobHistory       as projection on btpjapanprojectdemo.JobHistory;
    entity ClearingLog      as projection on btpjapanprojectdemo.ClearingLog;
    entity SupplierInvoice as projection on btpjapanprojectdemo.SupplierInvoice
    actions {
        action ShowPreviousMonth() returns SupplierInvoice;
        action ShowCurrentMonth() returns SupplierInvoice;
    };
    
    action ReplicateInvoices();
}
