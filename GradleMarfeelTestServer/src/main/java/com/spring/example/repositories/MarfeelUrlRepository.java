package com.spring.example.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.spring.example.models.MarfeelUrlRank;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MarfeelUrlRepository extends ReactiveMongoRepository<MarfeelUrlRank, String> {
	Flux<MarfeelUrlRank> findByUrl(String url);
	Flux<MarfeelUrlRank> findByRank(long rank);
}
