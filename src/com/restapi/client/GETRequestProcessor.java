package com.restapi.client;

import javax.ws.rs.core.Response;


public class GETRequestProcessor extends RESTRequestProcessor {


	@Override
    public void execute() {
		Response response = invocationBuilder.get(Response.class);
        responseBody = response.readEntity(String.class);
        responseHeaders = response.getHeaders();
        response.close();
    }	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		GETRequestProcessor reqProcessor = new GETRequestProcessor();

	}
	
}
