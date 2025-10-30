package customer.btpjapanprojectdemo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;

import cds.gen.mainservice.MainService;
import cds.gen.mainservice.POMapping;
import cds.gen.mainservice.POMapping_;

@Service
public class GenericCqnService {

    private final MainService mainService;
    private final EntityService entityService;

    public GenericCqnService(MainService mainService, EntityService entityService) {
        this.mainService = mainService;
        this.entityService = entityService;
    }

    public List<String> getPOMappingOriginalIds() {
        CqnSelect select = Select.from(POMapping_.CDS_NAME)
                .columns(POMapping.ORIGINAL_PO);

        List<POMapping> result = entityService.selectList(mainService, select, POMapping.class);

        return result.stream()
                .map(POMapping::getOriginalPo)
                .collect(Collectors.toList());
    }

}
