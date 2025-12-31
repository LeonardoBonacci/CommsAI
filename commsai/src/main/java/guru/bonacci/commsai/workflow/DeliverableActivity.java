package guru.bonacci.commsai.workflow;

import guru.bonacci.commsai.domain.DeliverableMessage;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface DeliverableActivity {

	@ActivityMethod
	void sendDeliverable(DeliverableMessage msg);
}