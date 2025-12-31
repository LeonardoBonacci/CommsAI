package guru.bonacci.commsai;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.bonacci.commsai.egress.DeliverableEgressRoute;
import guru.bonacci.commsai.workflow.DeliverableActivityImpl;
import guru.bonacci.commsai.workflow.DeliverableWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TemporalWorker implements CommandLineRunner {

	private final DeliverableEgressRoute deliverableEgressRoute;
	
	@Override
	public void run(String... args) {
		WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
		WorkflowClient client = WorkflowClient.newInstance(service);

		WorkerFactory factory = WorkerFactory.newInstance(client);
		Worker worker = factory.newWorker("DELIVERABLE_TASK_QUEUE");

		// register workflow implementation and activities
		worker.registerWorkflowImplementationTypes(DeliverableWorkflowImpl.class);
		worker.registerActivitiesImplementations(new DeliverableActivityImpl(deliverableEgressRoute));

		factory.start();

		System.out.println("Temporal Worker started...");
	}
}
