package guru.bonacci.commsai.ingress;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.domain.DeliverableMessage;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class DeliverableIngressRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Camel REST configuration using Spring Boot servlet
        restConfiguration()
            .component("servlet")               // use embedded Tomcat
            .bindingMode(RestBindingMode.json)  // automatic JSON ↔ Java mapping
            .dataFormatProperty("prettyPrint", "true")
            .dataFormatProperty("jsonLibrary", "Jackson");

        // REST endpoint for POST /deliverables
        rest("/deliverables")
            .post()
            .type(DeliverableMessage.class)    // parse JSON into this record
            .to("direct:deliverable");         // send to internal Camel route
    }
}
