package guru.bonacci.commsai.workflow;

import java.time.Duration;

import guru.bonacci.commsai.domain.DeliverableMessage;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeliverableWorkflowImpl implements DeliverableWorkflow {

  // Create an activity stub
  private final DeliverableActivity activities =
      Workflow.newActivityStub(
          DeliverableActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(5))
              .build()
      );

  @Override
  public void scheduleDeliverable(DeliverableMessage msg, long delayMillis) {

  	try {
	    // Wait asynchronously in Temporal
	    Workflow.sleep(delayMillis);
	
	    // Call the activity (this is how you “execute” work outside the workflow)
	    activities.sendDeliverable(msg);
  	} catch (io.temporal.failure.CanceledFailure e) {
        // This is where you catch cancellation
        log.info("Deliverable workflow canceled for UUID {}", msg.uuid());
    }
  }
}