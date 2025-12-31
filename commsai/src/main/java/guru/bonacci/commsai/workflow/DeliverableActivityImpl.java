package guru.bonacci.commsai.workflow;

import guru.bonacci.commsai.domain.DeliverableMessage;
import org.springframework.stereotype.Component;

@Component
public class DeliverableActivityImpl implements DeliverableActivity {

	@Override
	public void sendDeliverable(DeliverableMessage msg) {
		System.out.println("Activity sending Deliverable: " + msg);
	}
}