package com.spring.example.models;

public class RequestBean {
	String url;
	
	public RequestBean() {
    }
	
	public RequestBean(String _url){
		url = _url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
