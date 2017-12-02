package com.spring.example.request;

import java.util.ArrayList;
import java.util.List;

public class RequestBeanList {
	
	public List<RequestBean> rB = new ArrayList<RequestBean>();

	public List<RequestBean> getrB() {
		return rB;
	}

	public void setrB(List<RequestBean> rB) {
		this.rB = rB;
	}
	
	

}
