package guru.bonacci.commsai.egress;

import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.domain.DeliverableMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliverableEgressRoute {

	private final ProducerTemplate producerTemplate;

	/**
	 * Sends a Deliverable into the internal Camel route. 
	 * For now, route can handle logging or future delivery.
	 */
	public void sendDeliverable(DeliverableMessage msg) {
		log.info("Egress sending Deliverable: uuid={}, message={}", msg.uuid(), msg.message());

		// Send message into Camel internal route for now
		producerTemplate.sendBody("direct:egress", msg);
	}
}