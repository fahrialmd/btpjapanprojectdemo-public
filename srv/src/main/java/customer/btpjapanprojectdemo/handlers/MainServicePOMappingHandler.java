package customer.btpjapanprojectdemo.handlers;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;
import cds.gen.mainservice.StartReplicatingPOJobContext;
import customer.btpjapanprojectdemo.service.PurchaseOrderReplicationService;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServicePOMappingHandler implements EventHandler {

    private final PurchaseOrderReplicationService purchaseOrderReplicationService;

    public MainServicePOMappingHandler(PurchaseOrderReplicationService purchaseOrderReplicationService) {
        this.purchaseOrderReplicationService = purchaseOrderReplicationService;
    }

    @On(event = StartReplicatingPOContext.CDS_NAME)
    public void onStartReplicatingPO(StartReplicatingPOContext context) {
        List<POMapping> result = purchaseOrderReplicationService.startReplicatingPO(context);
        context.getMessages().success("PO Replicated");
        context.setResult(result);
        // context.setCompleted();
    }

    @On(event = StartReplicatingPOJobContext.CDS_NAME)
    public void onStartReplicatingPOJob(StartReplicatingPOJobContext context) {
        try {
            List<POMapping> result = purchaseOrderReplicationService.startReplicatingPO(null);

            String resultMessage = String.format(
                    "Scheduled PO replication completed successfully. Processed %d PO mappings at %s",
                    result.size(),
                    Instant.now());

            context.setResult(resultMessage);

        } catch (Exception e) {
            context.getMessages().error("Scheduled PO replication failed: " + e.getMessage());
            throw e; // Let Job Scheduler know it failed
        }
    }
}
