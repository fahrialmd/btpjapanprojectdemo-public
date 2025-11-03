package customer.btpjapanprojectdemo.service;

import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.StartReplicatingPOContext;

public interface PurchaseOrderReplicationService {
    public POMapping startReplicatingPO(StartReplicatingPOContext context);
}
