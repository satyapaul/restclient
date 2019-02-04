package com.restapi.client;

import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;


public class POSTRequestProcessor extends RESTRequestProcessor {

	private String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	// default constructor
	public POSTRequestProcessor() {
	}

	
	// constructor where no proxy settings are required. 
	public POSTRequestProcessor(String baseUri, LinkedList<String> pathList,
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
	public POSTRequestProcessor(String baseUri, LinkedList<String> pathList,
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


	@Override
    public void execute() {
    	Response response = invocationBuilder.post(Entity.json(input));
        responseBody = response.readEntity(String.class);
        responseHeaders = response.getHeaders();
        response.close();
    }	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		POSTRequestProcessor reqProcessor = new POSTRequestProcessor();

	}
	
}
