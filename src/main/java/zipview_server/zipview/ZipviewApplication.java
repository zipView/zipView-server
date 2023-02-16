package zipview_server.zipview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZipviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipviewApplication.class, args);
	}

}
