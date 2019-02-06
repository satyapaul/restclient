package com.restapi.client.impl;

import javax.ws.rs.core.Response;

import com.restapi.client.RESTRequestProcessor;


public class DELETERequestProcessor extends RESTRequestProcessor {


    public void execute() {
		Response response = invocationBuilder.delete();
        responseBody = response.readEntity(String.class);
        responseHeaders = response.getHeaders();
        response.close();
    }	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DELETERequestProcessor reqProcessor = new DELETERequestProcessor();

	}
	
}
