package org.example.source_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"org.example.api.repository","org.example.source_api.repository"})
@EntityScan(basePackages = {"org.example.api.entity","org.example.source_api.entity"})
public class SourceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourceApiApplication.class, args);
	}

}
