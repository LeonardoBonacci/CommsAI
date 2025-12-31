package guru.bonacci.commsai.egress;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.domain.DeliverableMessage;

@Component
public class DeliverableEgressRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:deliverable")
			.process(exchange -> {
				DeliverableMessage msg = exchange.getIn().getBody(DeliverableMessage.class);
				// Just print for now
				log.info("Egress: sending Deliverable '{}'", msg.message());
			});
	}
}