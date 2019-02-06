package com.restapi.client.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.restapi.client.RESTRequestProcessor;


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


    public void execute() {
    	Response response = invocationBuilder.post(Entity.json(input));
        responseBody = response.readEntity(String.class);
        responseHeaders = response.getHeaders();
        response.close();
    }	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		POSTRequestProcessor reqProcessor = new POSTRequestProcessor();
		
		reqProcessor.testAPI1();

	}
	
	void testAPI1() {
		//curl -X POST 
		//"https://virtserver.swaggerhub.com/satyapaul/samplepetstore/1.0.0/store/order" 
		// -H "accept: application/json" -H "Content-Type: application/json" -d 
		// {"id":0,"petId":0,"quantity":0,"shipDate":"2019-02-05T00:44:07.992Z","status":"placed","complete":false}
		
		String baseUri = "https://virtserver.swaggerhub.com/satyapaul/samplepetstore/1.0.0/store/order"; 
		String inputs= "{\"id\":0,\"petId\":0,\"quantity\":0,\"shipDate\":\"2019-02-05T00:44:07.992Z\",\"status\":\"placed\",\"complete\":false}";
		LinkedList<String> pathList = new LinkedList<String>();
		Map<String, String> queryParams =  new HashMap<String, String>(); 
    	
		Map<String, String> headers = new HashMap<String, String>();
    	
    	String mediaType = MediaType.APPLICATION_JSON;
    	
		Map<String, String> authDetails = new HashMap<String, String>();
		int authType = -1;		
    	
    	POSTRequestProcessor reqProcessor = new POSTRequestProcessor(baseUri, pathList, queryParams, headers, mediaType, authDetails, authType, inputs);
		reqProcessor.initialize();
		reqProcessor.execute();
		System.out.println(reqProcessor.getResponseBody());
		System.out.println( reqProcessor.getResponseHeaders());
	}
	
}
