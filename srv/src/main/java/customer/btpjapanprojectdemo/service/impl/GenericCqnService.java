package customer.btpjapanprojectdemo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.cds.Result;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.ql.cqn.CqnSelect;

import cds.gen.mainservice.InvoiceLog;
import cds.gen.mainservice.InvoiceLog_;
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
                true);
    }

    /**
     * Get all PO mapping that is not in the exclude list
     * @param excludeList list of subcontracting PO
     * @return list of POMapping
     */
    public List<POMapping> getPoMappings(List<String> excludeList) {
        CqnSelect select = Select.from(POMapping_.CDS_NAME).where(w -> w.get(POMapping.ORIGINAL_PO).in(excludeList).not());

        List<POMapping> poMappingList = entityService.selectList(mainService, select, POMapping.class);

        return poMappingList;
    }

    /**
     * get all InvoiceLog
     * @return List of InvoiceLog
     */
    public List<InvoiceLog> getInvoiceLogPO() {
        CqnSelect select = Select.from(InvoiceLog_.CDS_NAME).columns(InvoiceLog.PO_ORIGINAL, InvoiceLog.PO_REPLICATED);

        List<InvoiceLog> invoiceLogList = entityService.selectList(mainService, select, InvoiceLog.class);

        return invoiceLogList;
    }

    /**
     * insert a InvoiceLog
     * @param invoiceLog
     */
    public void insertInvoiceLog(InvoiceLog invoiceLog) {
        entityService.insert(
            mainService, 
            null, 
            InvoiceLog_.class, 
            invoiceLog, 
            true);
    }

}
