package com.spring.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

/**
 * URLS Consultadas
https://dzone.com/articles/spring-reactive-samples
https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code
https://dzone.com/articles/spring-boot-reactive-tutorial
https://itnext.io/reactive-microservices-with-spring-5-95c5f8cd03d0
https://stackoverflow.com/questions/34414367/mongo-tries-to-connect-automatically-to-port-27017localhost
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-mongodb
https://stackoverflow.com/questions/28747909/how-to-disable-spring-data-mongodb-autoconfiguration-in-spring-boot
https://stackoverflow.com/questions/31568351/how-do-you-configure-embedded-mongdb-for-integration-testing-in-a-spring-boot-ap
https://springframework.guru/spring-boot-with-embedded-mongodb/

https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html
 */


@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableWebFluxSecurity
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
