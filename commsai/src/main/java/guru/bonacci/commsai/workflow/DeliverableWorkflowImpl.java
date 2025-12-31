package guru.bonacci.commsai.workflow;

import java.time.Duration;

import guru.bonacci.commsai.domain.DeliverableMessage;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

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

    // Wait asynchronously in Temporal
    Workflow.sleep(delayMillis);

    // Call the activity (this is how you “execute” work outside the workflow)
    activities.sendDeliverable(msg);
  }
}