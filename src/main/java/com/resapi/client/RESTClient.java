package com.restapi.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class RESTClient {

	// Ref: https://jersey.github.io/documentation/latest/client.html#d0e4207
	
	
    // BASIC: Basic preemptive authentication. In preemptive mode the authentication 
    // information is send always with each HTTP request. This mode is more usual than 
    // the following non-preemptive mode (if you require BASIC authentication you will 
    // probably use this preemptive mode). This mode must be combined with usage of 
    // SSL/TLS as the password is send only BASE64 encoded.
    private static final int BASIC_AUTHENTICATION = 0;
    
    // BASIC NON-PREEMPTIVE:Basic non-preemptive authentication. In non-preemptive mode 
    // the authentication information is added only when server refuses the request with 
    // 401 status code and then the request is repeated with authentication information. 
    // This mode has negative impact on the performance. The advantage is that it does 
    // not send credentials when they are not needed. This mode must be combined with 
    // usage of SSL/TLS as the password is send only BASE64 encoded.    
    private static final int BASIC_NON_PREEMPTIVE_AUTHENTICATION = 1;
    
    // DIGEST: Http digest authentication. Does not require usage of SSL/TLS.
    private static final int DIGEST_AUTHENTICATION = 2;
    
    // UNIVERSAL: Combination of basic and digest authentication. The feature works in 
    // non-preemptive mode which means that it sends requests without authentication 
    // information. If 401 status code is returned, the request is repeated and an 
    // appropriate authentication is used based on the authentication requested in the 
    // response (defined in WWW-Authenticate HTTP header. The feature remembers which 
    // authentication requests were successful for given URI and next time tries to 
    // preemptively authenticate against this URI with latest successful authentication 
    // method.
    
    // only one set of username & password
    private static final int UNIVERSAL_AUTHENTICATION_1 = 3;
    
    // with different password for basic and digest
    private static final int UNIVERSAL_AUTHENTICATION_2 = 4;
    
    public static void main(String[] args) {
    	
    	String responseJSON = testGETApi();
    	System.out.println(responseJSON);
    }

    //private void 
    public static String testGETApi() {
    	String requestURL = "https://virtserver.swaggerhub.com/satyapaul/samplepetstore/1.0.0/pet/findByStatus";
    	String mediaType = MediaType.APPLICATION_JSON;
    	
    	Map<String, String> authDetails = new HashMap<String, String>() {{
    		put("username", "password");
    	}};
    	
    	Map<String, String> headers = new HashMap<String, String>() {{
    		put("some-header", "value");
    	}};

    	Map<String, String> queryParams = new HashMap<String, String>() {{
    		put("status", "available");
    	}};
    	
    	System.out.println(headers.toString());
    	
    	return executeGETApi(requestURL, queryParams, headers, mediaType, authDetails, -1);
    }
    
    public static String testPOSTApi() {

    	String requestURL = "https://virtserver.swaggerhub.com/satyapaul/samplepetstore/1.0.0/pet";
    	String mediaType = MediaType.APPLICATION_JSON;
    	
    	Map<String, String> authDetails = new HashMap<String, String>() {{
    		put("username", "password");
    	}};
    	
    	Map<String, String> headers = new HashMap<String, String>() {{
    		put("some-header", "value");
    	}};

    	Map<String, String> queryParams = new HashMap<String, String>() {{
    		put("status", "available");
    	}};
    	
    	String input = "{\"id\":0,\"category\":{\"id\":0,\"name\":\"string\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
    	
    	System.out.println(headers.toString());
    	
    	return executePOSTApi(requestURL, queryParams, headers, input, mediaType, authDetails, -1);
    }
    
    
    public static String executeGETApi(String uri, 
    		Map<String, String> queryParams, 
    		Map<String, String> headers, 
    		String mediaType, 
    		Map<String, String> authDetails, 
    		int authType) {

    	Client client = ClientBuilder.newClient();
    	client = setAutheticationConfiguration(client, authDetails, authType);
    	
    	URI _uri = UriBuilder.fromUri(uri).build();
    	WebTarget target = client.target(_uri);
    	target = setQueryParams(target, queryParams);
    	
    	Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
    	invocationBuilder = setHeaderDetails(invocationBuilder, headers);
    	invocationBuilder = invocationBuilder.accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder.get(Response.class);
    	
    			
        String body = response.readEntity(String.class);
        System.out.println(response.getHeaders());

        response.close();
        
        return body;
    	
    }

    
    public static String executePOSTApi(String uri, 
    		Map<String, String> queryParams, 
    		Map<String, String> headers, 
    		String input,
    		String mediaType, 
    		Map<String, String> authDetails, 
    		int authType) {

    	Client client = ClientBuilder.newClient();
    	client = setAutheticationConfiguration(client, authDetails, authType);
    	
    	URI _uri = UriBuilder.fromUri(uri).build();
    	WebTarget target = client.target(_uri);
    	target = setQueryParams(target, queryParams);
    	
    	Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
    	invocationBuilder = setHeaderDetails(invocationBuilder, headers);
    	invocationBuilder = invocationBuilder.accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder.post(Entity.json(input));
    	
    			
        String body = response.readEntity(String.class);
        System.out.println(response.getHeaders());

        response.close();
        
        return body;
    	
    }

    public static String executePUTApi(String uri, 
    		Map<String, String> queryParams, 
    		Map<String, String> headers, 
    		String input,
    		String mediaType, 
    		Map<String, String> authDetails, 
    		int authType) {

    	Client client = ClientBuilder.newClient();
    	client = setAutheticationConfiguration(client, authDetails, authType);
    	
    	URI _uri = UriBuilder.fromUri(uri).build();
    	WebTarget target = client.target(_uri);
    	target = setQueryParams(target, queryParams);
    	
    	Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
    	invocationBuilder = setHeaderDetails(invocationBuilder, headers);
    	invocationBuilder = invocationBuilder.accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder.put(Entity.json(input));
    			
        String body = response.readEntity(String.class);
        System.out.println(response.getHeaders());
        response.close();
        return body;
    }
    
    public static String executeDELETEApi(String uri, 
    		Map<String, String> queryParams, 
    		Map<String, String> headers, 
    		String input,
    		String mediaType, 
    		Map<String, String> authDetails, 
    		int authType) {

    	Client client = ClientBuilder.newClient();
    	client = setAutheticationConfiguration(client, authDetails, authType);
    	
    	URI _uri = UriBuilder.fromUri(uri).build();
    	WebTarget target = client.target(_uri);
    	target = setQueryParams(target, queryParams);
    	
    	Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
    	invocationBuilder = setHeaderDetails(invocationBuilder, headers);
    	invocationBuilder = invocationBuilder.accept(MediaType.APPLICATION_JSON);
    	Response response = invocationBuilder.delete();
    			
        String body = response.readEntity(String.class);
        System.out.println(response.getHeaders());
        response.close();
        return body;
    }
        
    private static Client setHttpProxyConfiguration(Client client, String proxy, String user, String password) {
    	
    	ClientConfig config = new ClientConfig();
//        config.connectorProvider(new ApacheConnectorProvider());
        config.property(ClientProperties.PROXY_URI, proxy);
        config.property(ClientProperties.PROXY_USERNAME,user);
        config.property(ClientProperties.PROXY_PASSWORD,password);
        client = ClientBuilder.newClient(config);
        
        return client;
    }
    private static WebTarget setQueryParams(WebTarget target, Map<String, String> queryParams) {

    	for(String paramName : queryParams.keySet()) {
    		target = target.queryParam(paramName, queryParams.get(paramName));
    	}
    	return target;
    }
    
    
    private static Invocation.Builder setHeaderDetails(Invocation.Builder invocationBuilder, Map<String, String> headers) {

    	for(String headerName : headers.keySet()) {
    		invocationBuilder = invocationBuilder.header(headerName, headers.get(headerName));
    	}

    	return invocationBuilder;
    }
    

    private static Client setAutheticationConfiguration(Client client, Map<String, String> authDetails, int authType) {
    	HttpAuthenticationFeature feature = null;
    	
    	switch (authType) {
    	
    		case BASIC_AUTHENTICATION:
    			String user = authDetails.get("BASIC_USERNAME");
    			String password = authDetails.get("BASIC_PASSWORD");
    			feature = HttpAuthenticationFeature.basic(user, password);
    			
    		case BASIC_NON_PREEMPTIVE_AUTHENTICATION:
    			user = authDetails.get("BASIC_NON_PREEMPTIVE_USERNAME");
    			password = authDetails.get("BASIC_NON_PREEMPTIVE_PASSWORD");
    			feature = HttpAuthenticationFeature.basicBuilder()
                .nonPreemptive()
                .credentials(user, password)
                .build();
    	    	client = client.register(feature);
    		case DIGEST_AUTHENTICATION:
    			user = authDetails.get("DIGEST_USERNAME");
    			password = authDetails.get("DIGEST_PASSWORD");
    			feature = HttpAuthenticationFeature.digest(user, password);
    			client = client.register(feature);
    		case UNIVERSAL_AUTHENTICATION_1:
    			user = authDetails.get("UNIVERSAL_USERNAME");
    			password = authDetails.get("UNIVERSAL_PASSWORD");

    			feature = HttpAuthenticationFeature.universal(user, password); 
    			client = client.register(feature);
    		case UNIVERSAL_AUTHENTICATION_2:
    			//Example of building the feature in universal mode with different credentials for basic and digest:
    			user = authDetails.get("UNIVERSAL_BASIC_USERNAME");
    			password = authDetails.get("UNIVERSAL_BASIC_PASSWORD");
    			String adminUser = authDetails.get("UNIVERSAL_DIGEST_USERNAME");
    			String adminPassword = authDetails.get("UNIVERSAL_DIGEST_PASSWORD");
    			feature = HttpAuthenticationFeature.universalBuilder()      
    					 .credentialsForBasic(user, password)      
    					 .credentials(adminUser, adminPassword)      
    					 .build();
    			client = client.register(feature);
    		default:
    			//feature = HttpAuthenticationFeature.basicBuilder().build();
    			// DO NOTHING for now, investigate why above line of code throws exception

    	}
    	
    	return client;
    }

}