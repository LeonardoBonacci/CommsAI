package guru.bonacci.commsai.egress;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.domain.DeliverableMessage;
import guru.bonacci.commsai.workflow.DeliverableWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliverableEgressRoute extends RouteBuilder {

	private final WorkflowClient workflowClient;

	@Override
	public void configure() throws Exception {
		from("direct:deliverable")
			.process(exchange -> {
				DeliverableMessage msg = exchange.getIn().getBody(DeliverableMessage.class);
				// Just print for now
				log.info("Egress: sending Deliverable '{}'", msg.message());
				
				// Create a workflow stub
        DeliverableWorkflow workflow = workflowClient.newWorkflowStub(
            DeliverableWorkflow.class,
            WorkflowOptions.newBuilder()
                .setTaskQueue("DELIVERABLE_TASK_QUEUE")
                .build()
        );

        // Start workflow asynchronously
        WorkflowClient.start(workflow::scheduleDeliverable, msg, 1000L);
			});
	}
}