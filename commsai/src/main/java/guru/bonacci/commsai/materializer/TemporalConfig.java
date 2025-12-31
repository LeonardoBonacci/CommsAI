package guru.bonacci.commsai.materializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;

@Configuration
public class TemporalConfig {

	@Bean
	WorkflowClient workflowClient() {
		return WorkflowClient.newInstance(WorkflowServiceStubs.newLocalServiceStubs());
	}
}