package com.restapi.client.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import com.restapi.client.RESTRequestProcessor;


public class GETRequestProcessor extends RESTRequestProcessor {

	public GETRequestProcessor() {
		super();
	}
	
	public GETRequestProcessor(String baseUri, LinkedList<String> pathList,
			Map<String, String> queryParams, Map<String, String> headers,
			String mediaType, Map<String, String> authDetails,
			int authType
			) {
		super(baseUri, pathList,
				queryParams, headers,
				mediaType, authDetails,
				authType);
	}
	
	public GETRequestProcessor(String baseUri, LinkedList<String> pathList,
			Map<String, String> queryParams, Map<String, String> headers,
			String mediaType, Map<String, String> authDetails,
			int authType, String proxy, String proxyUser, String proxyPassword
			) {
		super(baseUri, pathList,
				queryParams, headers,
				mediaType, authDetails,
				authType, proxy, proxyUser, proxyPassword);
	}

    public void execute() {
		Response response = invocationBuilder.get(Response.class);
        responseBody = response.readEntity(String.class);
        responseHeaders = response.getHeaders();
        responseStatus = response.getStatus();
        
        response.close();
    }	
	
	public static void main(String[] args) {
		//testGETAPI1();
		
	}
	
	public void testGETAPI1() {
		String baseUri = "https://virtserver.swaggerhub.com/satyapaul/samplepetstore/1.0.0/pet/findByStatus"; 
		LinkedList<String> pathList = new LinkedList<String>();
		Map<String, String> queryParams =  new HashMap<String, String>() {{
    		put("status", "available");
    	}};
    	
		Map<String, String> headers = new HashMap<String, String>() {{
    		put("some-header", "value");
    	}};
    	
    	String mediaType = MediaType.APPLICATION_JSON;
    	
		Map<String, String> authDetails = new HashMap<String, String>() {{
    		put("username", "password");
    	}};
		int authType = -1;		
    	
    	System.out.println(headers.toString());
		GETRequestProcessor reqProcessor = new GETRequestProcessor(baseUri, pathList, queryParams, headers, mediaType, authDetails, authType);
		reqProcessor.initialize();
		reqProcessor.execute();
		System.out.println(reqProcessor.getResponseBody());
		System.out.println( reqProcessor.getResponseHeaders());
		
		
		//output
		/**
		 * 
				[ {
				  "id" : 0,
				  "category" : {
				    "id" : 0,
				    "name" : "string"
				  },
				  "name" : "doggie",
				  "photoUrls" : [ "string" ],
				  "tags" : [ {
				    "id" : 0,
				    "name" : "string"
				  } ],
				  "status" : "available"
				} ]
		 */
	}
	
	public void testGETAPI2() {
		String baseUri = "https://virtserver.swaggerhub.com"; 
		LinkedList<String> pathList = new LinkedList<String>() {{
			add("satyapaul");
			add("samplepetstore");
			add("1.0.0");
			add("pet");
			add("findByStatus");
		}};
		Map<String, String> queryParams =  new HashMap<String, String>() {{
    		put("status", "available");
    	}};
    	
    	Map<String, String> headers = new HashMap<String, String>();
    	
    	String mediaType = MediaType.APPLICATION_XML; //MediaType.APPLICATION_JSON;
    	
    	Map<String, String> authDetails = new HashMap<String, String>();
		int authType = -1;		
    	
		GETRequestProcessor reqProcessor = new GETRequestProcessor(baseUri, pathList, queryParams, headers, mediaType, authDetails, authType);
		reqProcessor.initialize();
		reqProcessor.execute();
		System.out.println(reqProcessor.getResponseStatus());
		System.out.println(reqProcessor.getResponseBody());
		System.out.println( reqProcessor.getResponseHeaders());
		
		
		//output
		/**
		 * <?xml version='1.1' encoding='UTF-8'?>
		 * <Pet>
		 * 	<id>0</id>
		 * 	<Category>
		 * 		<id>0</id>
		 * 		<name>string</name>
		 * 	</Category>
		 * 	<name>doggie</name>
		 * 	<photoUrl>
		 * 		<photoUrls>string</photoUrls>
		 * 	</photoUrl>
		 * 	<tag>
		 * 		<Tag>
		 * 			<id>0</id>
		 * 			<name>string</name>
		 * 		</Tag>
		 * 	</tag>
		 * 	<status>available</status>
		 * 	</Pet>
		 */
	}	
}
