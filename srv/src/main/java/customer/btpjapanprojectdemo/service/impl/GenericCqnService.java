package customer.btpjapanprojectdemo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.cds.Result;
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

    public JsonNode getPOHeaderItemOriginalIds() {
        CqnSelect select = Select.from(POMapping_.CDS_NAME)
                .columns(POMapping.ORIGINAL_PO, POMapping.ORIGINAL_PO_ITEM)
                .distinct();

        List<POMapping> result = entityService.selectList(mainService, select, POMapping.class);

        // Create ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // Create root object with "d" and "results" structure
        ArrayNode results = mapper.createArrayNode();

        // Convert each POMapping to JsonNode
        result.forEach(po -> {
            ObjectNode item = mapper.createObjectNode();
            item.put("original_po", po.getOriginalPo());
            item.put("original_po_item", po.getOriginalPoItem());
            results.add(item);
        });

        return results;
    }

    // Insert single POMapping
    public Result insertPoMapping(POMapping data) {
        return entityService.insert(
                mainService,
                null,
                POMapping_.class,
                data,
                true // isActiveEntity = true (not a draft)
        );
    }

}
