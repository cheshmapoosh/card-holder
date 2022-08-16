package ir.co.isc.assignment.cardholder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@EnableBatchProcessing
@EnableMethodSecurity
public class CardHolderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardHolderApplication.class, args);
    }

}
