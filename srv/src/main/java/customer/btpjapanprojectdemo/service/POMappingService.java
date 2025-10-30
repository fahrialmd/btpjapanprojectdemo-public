package customer.btpjapanprojectdemo.service;

import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.POMappingStartReplicatingPOContext;

public interface POMappingService {
    public POMapping startReplicatingPO(POMappingStartReplicatingPOContext context);
}
