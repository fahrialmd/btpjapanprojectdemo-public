package customer.btpjapanprojectdemo.service;

import java.util.List;

import com.sap.cds.services.cds.CdsReadEventContext;

import cds.gen.mainservice.SupplierInvoice;
import cds.gen.mainservice.SupplierInvoiceReplicateInvoicesContext;

public interface SupplierInvoiceReplicationService {
    /**
     * Get all Invoice with the replicated PO
     * that has not already been replicated back
     */
    public List<SupplierInvoice> readSupplierInvoice(CdsReadEventContext context);

    /**
     * Replicate Invoice with Standard PO
     * into Invoice with Subcontracting PO
     */
    public void replicateInvoice(SupplierInvoiceReplicateInvoicesContext context);
}
