package guru.bonacci.commsai.egress;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.domain.DeliverableMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeliverableEgressRoutePrinter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:egress")
            .process(exchange -> {
                DeliverableMessage msg = exchange.getIn().getBody(DeliverableMessage.class);
                log.info("Camel route received Deliverable: uuid={}, message={}", 
                         msg.uuid(), msg.message());
            });
    }
}
