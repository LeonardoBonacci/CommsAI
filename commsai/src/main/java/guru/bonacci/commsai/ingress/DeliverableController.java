package guru.bonacci.commsai.ingress;

import java.util.UUID;

import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.commsai.domain.DeliverableMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeliverableController {

	private final ProducerTemplate producerTemplate;

	private static final String TOPIC = "deliverables";
	
	
	@PostMapping("/deliverables")
	public String create(@RequestBody DeliverableMessageRequest msgReq) {
		var msg = new DeliverableMessage(UUID.randomUUID(), msgReq.message());
		log.info("Ingress: received DeliverableMessage {}", msg.uuid());

    // Produce tombstone (key only, null value) to Kafka for compaction
    producerTemplate.sendBodyAndHeader(
    		"kafka:" + TOPIC + "?brokers=localhost:9092"
            + "&keySerializer=org.apache.kafka.common.serialization.StringSerializer"
            + "&valueSerializer=org.apache.kafka.common.serialization.StringSerializer",
//            + "&valueSerializer=org.springframework.kafka.support.serializer.JsonSerializer",
        msg,                     
        "kafka.KEY", msg.uuid().toString()
    );

		return msg.uuid().toString();
	}
	
	@DeleteMapping("/deliverables/{id}")
	public void create(@PathVariable String id) {
		log.info("Ingress: deleting Deliverable with id {}", id);
		
		// Produce tombstone (key only, null value) to Kafka for compaction
    producerTemplate.sendBodyAndHeader(
        "kafka:" + TOPIC + "?brokers=localhost:9092",
        null,                     
        "kafka.KEY", id            // the key for compaction
    );
	}

}