package guru.bonacci.commsai.workflow;

import guru.bonacci.commsai.domain.DeliverableMessage;
import guru.bonacci.commsai.egress.DeliverableEgressRoute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DeliverableActivityImpl implements DeliverableActivity {

	private final DeliverableEgressRoute deliverableEgressRoute;
	
	@Override
	public void sendDeliverable(DeliverableMessage msg) {
		System.out.println("Activity sending Deliverable: " + msg);
		deliverableEgressRoute.sendDeliverable(msg);
	}
}