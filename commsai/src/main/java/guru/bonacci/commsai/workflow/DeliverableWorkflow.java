package guru.bonacci.commsai.workflow;

import guru.bonacci.commsai.domain.DeliverableMessage;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface DeliverableWorkflow {

	@WorkflowMethod
	void scheduleDeliverable(DeliverableMessage msg, long delayMillis);

}
