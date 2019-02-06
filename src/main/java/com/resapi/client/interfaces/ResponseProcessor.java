package com.restapi.client.interfaces;


import javax.ws.rs.core.MultivaluedMap;

public interface ResponseProcessor {
	public String getResponseBody();
	public MultivaluedMap<String, Object> getResponseHeaders();
	public int getResponseStatus();
}
