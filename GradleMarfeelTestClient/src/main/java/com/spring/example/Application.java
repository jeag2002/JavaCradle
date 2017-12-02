package com.spring.example;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import com.spring.example.models.MarfeelUrlRank;
import com.spring.example.models.RequestBean;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;



/*
 https://howtoprogram.xyz/2017/10/28/reactive-http-client-spring-5-webclient/
 https://github.com/bclozel/spring-reactive-university/blob/master/src/main/java/com/example/integration/gitter/GitterClient.java
 */


public class Application {
	
	private static final ExchangeFunction exchange = ExchangeFunctions.create(new ReactorClientHttpConnector());

	private static Flux<RequestBean> getPayload() {
        return Flux.just(new RequestBean("http://www.google.com"));
    }
	
	
	
	public static void main(String[] args) throws Exception {
        
    	
		URI uri = URI.create("http://localhost:8081/marfeel");
		ClientRequest request = null;
		Mono<String> urls = null;
		
		
		URI uri_1 = URI.create("http://localhost:8081/marfeel/listByRank/1"); 
		request = ClientRequest.method(HttpMethod.GET, uri_1).header("Accept", "application/json").build();
		urls = exchange.filter(basicAuthentication("tom","password")).exchange(request).flatMap(response->response.bodyToMono(String.class));
		System.out.println(urls.block());
		
		/*
		request = ClientRequest.method(HttpMethod.POST, uri).header("Accept", "application/json").body(getPayload(), RequestBean.class).build();
		urls = exchange.filter(basicAuthentication("tom","password")).exchange(request).flatMap(response->response.bodyToMono(String.class));
		System.out.println(urls.block());
		*/
	
		WebClient client = WebClient
				.builder()
				.baseUrl("http://localhost:8081")
				.defaultHeader("Accept","application/json")
				.defaultHeader("Authorization", "Basic " + Base64Utils.encodeToString("tom:password".getBytes()))
				.build();
		
		List<RequestBean> rL = new ArrayList<RequestBean>();
		rL.add(new RequestBean("www.google.com"));
		
		client.post()
		      .uri("/marfeel")
		      .body(BodyInserters.fromObject(rL))
		      .retrieve()
		      .bodyToFlux(MarfeelUrlRank.class)
		      //.exchange()
		      //.flatMap( clientResponse -> clientResponse.bodyToMono(MarfeelUrlRank.class) )
		      //.block();
			  //data
			  .subscribe(System.out::println);
			  Thread.sleep(1000);
		
			  
    }
}
