package org.study.pmqc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "org.study.pmqc.model.entities"
})
@EnableJpaRepositories(basePackages = {
        "org.study.pmqc.model.repository"
})
public class PmqcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmqcApplication.class, args);
    }

}
