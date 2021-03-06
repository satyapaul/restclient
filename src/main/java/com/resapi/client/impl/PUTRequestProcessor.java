package com.restapi.client.impl;

import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.restapi.client.RESTRequestProcessor;


public class PUTRequestProcessor extends RESTRequestProcessor { 

	private String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	// default constructor
	public PUTRequestProcessor() {
	}
	
	// constructor where no proxy settings are required. 
	public PUTRequestProcessor(String baseUri, LinkedList<String> pathList,
			Map<String, String> queryParams, Map<String, String> headers,
			String mediaType, Map<String, String> authDetails,
			int authType, String input
			) {
		super(baseUri, pathList,
				queryParams, headers,
				mediaType, authDetails,
				authType);
		this.input = input;

	}

	// constructor where proxy settings are required. 
	public PUTRequestProcessor(String baseUri, LinkedList<String> pathList,
			Map<String, String> queryParams, Map<String, String> headers,
			String mediaType, Map<String, String> authDetails,
			int authType, String proxy, String proxyUser, String proxyPassword, String input
			) {
		super(baseUri, pathList,
				queryParams, headers,
				mediaType, authDetails,
				authType, proxy, proxyUser, proxyPassword);

		this.input = input;
	}



	public void execute() {
    	Response response = invocationBuilder.put(Entity.json(input));
        responseBody = response.readEntity(String.class);
        responseHeaders = response.getHeaders();
        response.close();
    }	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PUTRequestProcessor reqProcessor = new PUTRequestProcessor();

	}
	
}
