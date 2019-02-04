package com.restapi.client;

import javax.ws.rs.core.Response;


public class DELETERequestProcessor extends RESTRequestProcessor {


	@Override
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
