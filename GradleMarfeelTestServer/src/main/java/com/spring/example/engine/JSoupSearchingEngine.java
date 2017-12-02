package com.spring.example.engine;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.example.models.MarfeelUrlRank;
import com.spring.example.request.RequestBean;
import com.spring.example.request.RequestBeanList;
import com.spring.example.utils.StringUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;


@Service
public class JSoupSearchingEngine {
	
	@Autowired 
	JSoupSearchUrl jSSUrl;
	
	@Autowired
	private com.spring.example.repositories.MarfeelUrlRepository repository;
	
	private final Logger logger = LoggerFactory.getLogger(JSoupSearchingEngine.class);
	
	
	public Flux<com.spring.example.models.MarfeelUrlRank> getAllList(){
		return this.repository.findAll().sort();
	}
	
	
	
	public Flux<com.spring.example.models.MarfeelUrlRank> processURLS(Flux<RequestBean> lRBean){
		
		try{
		
			//1-Flux<RequestBean> to List<RequestBean>
			List<RequestBean> llRBean = lRBean.collectList().block();
			
			logger.warn("[INPUT TYPE] " + llRBean.toString());
			
			//2-Flux RequestBean to List of MarfeelUrlRank
			List<com.spring.example.models.MarfeelUrlRank>data = llRBean.stream().map(p->(new MarfeelUrlRank(p.getUrl(),0))).collect(Collectors.toList());
			
			logger.warn("[TRANSFORM DATA] " +  data.toString());
			
			//3-List of MarfeelUrlRank to Flux of MarfeelUrlRank and Filter by Rank (JSoup)
			Flux<com.spring.example.models.MarfeelUrlRank>dataFlux = Flux.fromIterable(data).map(p->jSSUrl.processJSoupURL(p)).filter(p->(p.getRank()!=0));
			
			List<com.spring.example.models.MarfeelUrlRank> listFluxFilter = dataFlux.collectList().block();
			logger.warn("[CALCULATE DATA] " + listFluxFilter.toString());
			
			//4-Filter MarfeelUrlRank by URL
			Flux<com.spring.example.models.MarfeelUrlRank>dataFlux_1 =  dataFlux.filter(p->repository.findByUrl(p.getUrl()).collectList().block().isEmpty());
			
			List<com.spring.example.models.MarfeelUrlRank> listFluxFilter_1 = dataFlux_1.collectList().block();
			logger.warn("[FILTER DATA] " + listFluxFilter_1.toString());
			
			
			//4-Process MarfeelUrlData	
			//4.1-Process JSoup for one URL
			//4.2-Filter by rank different to 0
			//4.3-Insert Parallel in database
			dataFlux.
			fromIterable(listFluxFilter_1).
			//create(fluxSink -> {for(MarfeelUrlRank mUR: listFluxFilter) {fluxSink.next(mUR);}}).
			//just(listFluxFilter.get(0)).
			log().
			//subscribeOn(Schedulers.parallel()).
			//limitRate(2).
			//elapsed().
			parallel().
			runOn(Schedulers.parallel()).
			subscribe(element->{
				logger.info("[PROCESSING DATA] --> ({})",element.toString());
				repository.insert(element).subscribe();	
			});
			/*
			subscribe(
					new Subscriber<MarfeelUrlRank>() {
					@Override
					public void onSubscribe(Subscription s) {
						logger.info("[ONSUSCRIBE]");
					}
				    
					@Override
				    public void onNext(MarfeelUrlRank  o) {
						logger.info("[ONNEXT]");
						MarfeelUrlRank t = o;
						logger.info("[ONNEXT] -- INSERT NEW DATA (" + t.toString() + ")");
						repository.insert(t).subscribe();
					}
					
				    @Override
					public void onError(Throwable t) {
				    	logger.info("[ONERROR]",t);
					}
					
				    @Override
				    public void onComplete() {
				    	logger.info("[ONCOMPLETE]");
					}
	
					});
			*/
			return dataFlux;
			
		}catch(Exception e){
			logger.warn("Error when processing data {}",e.getMessage());
			return null;
		}
		
	}
	

}
