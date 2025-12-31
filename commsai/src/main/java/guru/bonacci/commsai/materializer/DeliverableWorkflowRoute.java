package guru.bonacci.commsai.materializer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.domain.DeliverableMessage;
import guru.bonacci.commsai.workflow.DeliverableWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowNotFoundException;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliverableWorkflowRoute extends RouteBuilder {

	private final WorkflowClient workflowClient;

	private static final String TOPIC = "deliverables";
  
  
	@Override
	public void configure() throws Exception {
		from("direct:deliverable")
			.process(exchange -> {
				var msg = exchange.getIn().getBody(DeliverableMessage.class);
				log.info("sending Deliverable to temporal '{}'", msg.message());

				var workflowId = "deliverable-" + msg.uuid();
				// Create a workflow stub
        DeliverableWorkflow workflow = workflowClient.newWorkflowStub(
            DeliverableWorkflow.class,
            WorkflowOptions.newBuilder()
                .setTaskQueue("DELIVERABLE_TASK_QUEUE")
                .setWorkflowId(workflowId)
                .build()
        );

        // Start workflow asynchronously
        WorkflowClient.start(workflow::scheduleDeliverable, msg, 5000L);
			});
		
		from("direct:deldeliverable")
			.process(exchange -> {
				var id = exchange.getIn().getBody(String.class);
				log.info("removing Deliverable from temporal '{}'", id);
	
				var workflowId = "deliverable-" + id;
	      WorkflowStub stub = workflowClient.newUntypedWorkflowStub(workflowId);
	      try {
	        stub.cancel();
	        log.info("Workflow {} cancelled", workflowId);
	      } catch (io.temporal.client.WorkflowNotFoundException e) {
	        log.warn("Workflow {} not found or already completed", workflowId);
	      }
		});
		
		from("kafka:" + TOPIC + "?brokers=localhost:9092" +
		     "&keyDeserializer=org.apache.kafka.common.serialization.StringDeserializer" +
		     "&valueDeserializer=org.apache.kafka.common.serialization.StringDeserializer")
		.process(exchange -> {
		    DeliverableMessage msg = exchange.getIn().getBody(DeliverableMessage.class);
		    String key = exchange.getIn().getHeader("kafka.KEY", String.class);

		    if (msg == null) {
		        log.info("Received tombstone for key {} – deleting workflow", key);
		        WorkflowStub stub = workflowClient.newUntypedWorkflowStub("deliverable-" + key);
		        try {
		            stub.cancel();
		        } catch (WorkflowNotFoundException e) {
		            log.warn("Workflow {} not found for cancellation", key);
		        }
		    } else {
		        log.info("Sending Deliverable to Temporal: '{}'", msg.message());
		        var workflowId = "deliverable-" + msg.uuid();
		        DeliverableWorkflow workflow = workflowClient.newWorkflowStub(
		            DeliverableWorkflow.class,
		            WorkflowOptions.newBuilder()
		                .setTaskQueue("DELIVERABLE_TASK_QUEUE")
		                .setWorkflowId(workflowId)
		                .build()
		        );

		        WorkflowClient.start(workflow::scheduleDeliverable, msg, 5000L);
		    }
		});
	}
}