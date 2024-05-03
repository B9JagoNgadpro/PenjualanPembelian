package jagongadpro.penjualanpembelian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication()
public class PenjualanPembelianApplication {

	public static void main(String[] args) {
		SpringApplication.run(PenjualanPembelianApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(){
		return  new RestTemplate();
	}
}
