package cmc.hana.umuljeong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UmuljeongApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmuljeongApplication.class, args);
	}

}
