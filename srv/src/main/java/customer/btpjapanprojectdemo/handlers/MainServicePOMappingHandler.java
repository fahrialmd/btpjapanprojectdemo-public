package customer.btpjapanprojectdemo.handlers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;
import customer.btpjapanprojectdemo.service.PurchaseOrderReplicationService;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServicePOMappingHandler implements EventHandler {

    private final PurchaseOrderReplicationService PurchaseOrderReplicationService;

    public MainServicePOMappingHandler(PurchaseOrderReplicationService PurchaseOrderReplicationService) {
        this.PurchaseOrderReplicationService = PurchaseOrderReplicationService;
    }

    @On(event = StartReplicatingPOContext.CDS_NAME)
    public void onStartReplicatingPO(StartReplicatingPOContext context) {
        List<POMapping> result = PurchaseOrderReplicationService.startReplicatingPO(context);
        context.getMessages().success("PO Replicated");
        context.setResult(result);
        // context.setCompleted();
    }
}
