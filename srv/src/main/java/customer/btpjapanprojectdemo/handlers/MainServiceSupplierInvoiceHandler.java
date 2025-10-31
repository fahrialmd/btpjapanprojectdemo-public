package customer.btpjapanprojectdemo.handlers;

import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.SupplierInvoice_;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceSupplierInvoiceHandler implements EventHandler {

    private static final String INVOICE_READ_URL = "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_SUPPLIERINVOICE_PROCESS_SRV/A_SupplierInvoice?%24inlinecount=allpages&%24top=50";
    private static final String INVOICE_POST_URL = "https://my200132.s4hana.sapcloud.cn/sap/opu/odata/sap/API_SUPPLIERINVOICE_PROCESS_SRV/A_SupplierInvoice";
    private static final String INVOICE_USERNAME = "INVOICEUSER_49223";
    private static final String INVOICE_PASSWORD = "XdqSnTPjtkJ{rKSPR26K<6#7zpK6#LQg+AVee#bU";

    private final RestTemplate restTemplate;

    public MainServiceSupplierInvoiceHandler() {
        this.restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }

    @Before(event = CqnService.EVENT_READ, entity = SupplierInvoice_.CDS_NAME)
    public void readSupplierInvoice(CdsReadEventContext context) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("DataServiceVersion", "2.0");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(INVOICE_USERNAME, INVOICE_PASSWORD);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    INVOICE_READ_URL,
                    HttpMethod.GET,
                    httpEntity,
                    String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("INVOICE_READ" + responseEntity.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        context.setResult(null);
    }
}
