package customer.btpjapanprojectdemo.service;

import java.util.List;

import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;

public interface PurchaseOrderReplicationService {
    public List<POMapping> startReplicatingPO(StartReplicatingPOContext context);
}
