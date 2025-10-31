package customer.btpjapanprojectdemo.handlers;

import org.springframework.stereotype.Component;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;
import customer.btpjapanprojectdemo.service.POMappingService;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServicePOMappingHandler implements EventHandler {

    private final POMappingService poMappingService;

    public MainServicePOMappingHandler(POMappingService poMappingService) {
        this.poMappingService = poMappingService;
    }

    @On(event = StartReplicatingPOContext.CDS_NAME)
    public void onStartReplicatingPO(StartReplicatingPOContext context) {
        POMapping result = poMappingService.startReplicatingPO(context);
        context.getMessages().success("PO Replicated");
        context.setCompleted();
        // context.setResult(result);
    }
}
