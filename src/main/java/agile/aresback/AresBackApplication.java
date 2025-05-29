package agile.aresback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AresBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresBackApplication.class, args);
    }

}
