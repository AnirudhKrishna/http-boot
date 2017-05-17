package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.http.Http;

@SpringBootApplication
@EanbleIntegrationManagement
public class HttpBootApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(HttpBootApplication.class, args);
		System.out.println("Visit http://localhost:8080/integration to view the JSON object model");
		System.out.println("Hit enter to terminate");
		System.in.read();
		context.close();
	}
	
	@Bean
	public IntegrationFlow flow(){
		return IntegrationFlows.from(Http.inboundGateway("receiveGateway")
					.requestMapping(m -> m.methods(HttpMethod.POST))
					.requestPayloadType(String.class))
					.<String,String>transform(p -> p +"from the other side")
					.<String,String>transform(String::toUpperCase)
					.get();
		
	}
}
