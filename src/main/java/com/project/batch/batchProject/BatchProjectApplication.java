package com.project.batch.batchProject;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@EnableBatchProcessing
public class BatchProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProjectApplication.class, args);
	}

}
