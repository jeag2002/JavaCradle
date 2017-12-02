package com.spring.example.engine;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spring.example.models.MarfeelUrlRank;

@Component
public class JSoupSearchUrl {
	
	private static final String TAG_HTML = "TITLE";
	private static final String tag_look_1 = "noticias";
	private static final String tag_look_2 = "news";
	
	private final Logger logger = LoggerFactory.getLogger(JSoupSearchUrl.class);	
	
	public MarfeelUrlRank processJSoupURL(MarfeelUrlRank URL){
			
		try{
		long score = 0;
		Document doc = Jsoup.connect(URL.getUrl()).get();
		Elements title = doc.select(TAG_HTML);
		Elements title_1 = doc.select(TAG_HTML.toLowerCase());
		
		if (title.size()>0){
			for(Element leaf: title){
				//if ((leaf.text().indexOf(tag_look_1)!=-1) || (leaf.text().indexOf(tag_look_2)!=-1)){
					score++;
				//}
			}
		}
		
		else if (title_1.size()>0){
			for(Element leaf: title_1){
				//if ((leaf.text().indexOf(tag_look_1)!=-1) || (leaf.text().indexOf(tag_look_2)!=-1)){
					score++;
				//}
			}
		}
		
		title = null;
		title_1 = null;
		doc = null;
		
		URL.setRank(score);
		
		logger.info("[JSoupSearchURL]:: processing {}",URL.toString());
		
		}catch(IOException e){
			logger.warn("[JSoupSearchURL]:: error when connect with {} message{}",URL.toString(),e.getMessage());
			URL.setRank(0);
		}
		
		return URL;
	}
	

}
