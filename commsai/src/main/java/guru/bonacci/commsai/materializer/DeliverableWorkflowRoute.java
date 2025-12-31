package guru.bonacci.commsai.materializer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.domain.DeliverableMessage;
import guru.bonacci.commsai.workflow.DeliverableWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliverableWorkflowRoute extends RouteBuilder {

	private final WorkflowClient workflowClient;

	@Override
	public void configure() throws Exception {
		from("direct:deliverable")
			.process(exchange -> {
				var msg = exchange.getIn().getBody(DeliverableMessage.class);
				log.info("sending Deliverable to temporal '{}'", msg.message());
				
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