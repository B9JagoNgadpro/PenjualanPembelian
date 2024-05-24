package jagongadpro.penjualanpembelian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@SpringBootApplication()
public class PenjualanPembelianApplication {

	public static void main(String[] args) {
		SpringApplication.run(PenjualanPembelianApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(){
		return  new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}


	@Bean
	public Executor taskExecutor () {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(500);
		executor.initialize();
		return executor;
	}
}
