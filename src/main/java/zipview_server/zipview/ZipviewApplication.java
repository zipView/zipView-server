package zipview_server.zipview;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class ZipviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipviewApplication.class, args);
	}

}
