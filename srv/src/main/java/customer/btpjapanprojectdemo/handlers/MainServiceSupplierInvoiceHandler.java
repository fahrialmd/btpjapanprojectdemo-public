package customer.btpjapanprojectdemo.handlers;

import java.util.List;

import org.springframework.stereotype.Component;
import com.sap.cds.ResultBuilder;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.SupplierInvoice;
import cds.gen.mainservice.SupplierInvoiceReplicateInvoicesContext;
import cds.gen.mainservice.SupplierInvoice_;
import customer.btpjapanprojectdemo.exception.BusinessException;
import customer.btpjapanprojectdemo.service.SupplierInvoiceReplicationService;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceSupplierInvoiceHandler implements EventHandler {

    private final SupplierInvoiceReplicationService supplierInvoiceReplicationService;

    public MainServiceSupplierInvoiceHandler(SupplierInvoiceReplicationService supplierInvoiceReplicationService) {
        this.supplierInvoiceReplicationService = supplierInvoiceReplicationService;
    }

    @On(event = CqnService.EVENT_READ, entity = SupplierInvoice_.CDS_NAME)
    public void readSupplierInvoice(CdsReadEventContext context) {

        List<SupplierInvoice> supplierInvoices = supplierInvoiceReplicationService.readSupplierInvoice(context);

        if (supplierInvoices == null || supplierInvoices.isEmpty()) {
            throw new BusinessException("There is no invoice to be processed");
        }

        ResultBuilder resultBuilder = ResultBuilder.selectedRows(supplierInvoices);
        if (context.getCqn().hasInlineCount()) {
            resultBuilder.inlineCount(supplierInvoices.size());
        }
        context.setResult(resultBuilder.result());
    }

    @On(event = SupplierInvoiceReplicateInvoicesContext.CDS_NAME, entity = SupplierInvoice_.CDS_NAME)
    public void replicateInvoice(SupplierInvoiceReplicateInvoicesContext context) {

        supplierInvoiceReplicationService.replicateInvoice(context);

        context.setCompleted();

    }
}
