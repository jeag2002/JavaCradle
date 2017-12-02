package com.spring.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class MarfeelUrlRank {

	private String url;
	private long rank;
	@Id private String id;
	
	
	public MarfeelUrlRank(){
		this.url = "";
		this.rank = 0;
	}
	
	public MarfeelUrlRank(String _id, String _url, long _rank){
		this.id = _id;
		this.url = _url;
		this.rank = _rank;
	}	
	
	public MarfeelUrlRank(String _url, long _rank){
		this.url = _url;
		this.rank = _rank;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getRank() {
		return rank;
	}
	public void setRank(long rank) {
		this.rank = rank;
	}
	
	public String toString(){
		return "MarfeelUrlRank ".concat("url: ").concat(" ").concat(this.url).concat(" ").concat("rank: ").concat(String.valueOf(this.rank));
	}
	
}
