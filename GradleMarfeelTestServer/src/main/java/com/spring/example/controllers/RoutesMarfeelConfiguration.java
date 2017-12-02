package com.spring.example.controllers;

import com.spring.example.engine.JSoupSearchingEngine;
import com.spring.example.models.MarfeelUrlRank;
import com.spring.example.repositories.MarfeelUrlRepository;
import com.spring.example.request.RequestBean;
import com.spring.example.request.RequestBeanList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class RoutesMarfeelConfiguration {
	
	@Autowired
	JSoupSearchingEngine jSoupSearchingEngine;

    @Bean
    RouterFunction<?> routes(MarfeelUrlRepository marfeelRepository) {
        return nest(path("/marfeel"),
        		
          route(RequestPredicates.GET("/getAllUrls"), 
        		 request -> ok().body(marfeelRepository.findAll(), MarfeelUrlRank.class))		

          .andRoute(RequestPredicates.GET("/listById/{id}"),
            request -> ok().body(marfeelRepository.findById(request.pathVariable("id")), MarfeelUrlRank.class))
          
          
          .andRoute(RequestPredicates.GET("/listByRank/{rank}"),
          	request -> ok().body(marfeelRepository.findByRank(Long.parseLong(request.pathVariable("rank"))), MarfeelUrlRank.class))
        		
          .andRoute(method(HttpMethod.POST),
           request -> ok().body(jSoupSearchingEngine.processURLS(request.bodyToFlux(RequestBean.class)), MarfeelUrlRank.class))
        );
    }
}
