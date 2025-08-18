package com.edigest.myFirstApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "controller",
	    "services",
	    "repository",
	    "entity"
	})
@ComponentScan(basePackages = {"com.edigest.myFirstApplication", "Config"})
@EnableMongoRepositories(basePackages = "repository")
@EnableTransactionManagement
public class MyFirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFirstApplication.class, args);
	}
	
	@Bean
	public PlatformTransactionManager function(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

}
