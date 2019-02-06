package com.restapi.client;

import java.net.URI;
import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.restapi.client.interfaces.RequestProcessor;
import com.restapi.client.interfaces.ResponseProcessor;

public abstract class RESTRequestProcessor implements RequestProcessor, ResponseProcessor {

	// REST API attributes for calling a REST API
	private String baseUri; 
	private LinkedList<String> pathList;
	private Map<String, String> queryParams; 
	private Map<String, String> headers;
	private String mediaType; 
	private Map<String, String> authDetails; 
	private int authType;

	// Network setup for making a call to the REST API
	private String proxy; 
	private String proxyUser; 
	private String proxyPassword;

	// Invocation builder used for actual runtime 
	protected Invocation.Builder invocationBuilder;

	// Response from REST API call
	protected String responseBody;
	protected MultivaluedMap<String, Object> responseHeaders;
	protected int responseStatus;

	
	// default construtor
	public RESTRequestProcessor() {

	}

	public int getResponseStatus() {
		return responseStatus;
	}




	// constructor where no proxy settings are required. to be used for GET & DELETE
	public RESTRequestProcessor(String baseUri, LinkedList<String> pathList,
			Map<String, String> queryParams, Map<String, String> headers,
			String mediaType, Map<String, String> authDetails,
			int authType
			) {
		this.baseUri = baseUri; 
		this.pathList = pathList;
		this.queryParams = queryParams; 
		this.headers = headers;
		this.mediaType = mediaType; 
		this.authDetails = authDetails; 
		this.authType = authType;
	}

	// constructor where proxy settings are required. to be used for GET & DELETE
	public RESTRequestProcessor(String baseUri, LinkedList<String> pathList,
			Map<String, String> queryParams, Map<String, String> headers,
			String mediaType, Map<String, String> authDetails,
			int authType, String proxy, String proxyUser, String proxyPassword
			) {
		this.baseUri = baseUri; 
		this.pathList = pathList;
		this.queryParams = queryParams; 
		this.headers = headers;
		this.mediaType = mediaType; 
		this.authDetails = authDetails; 
		this.authType = authType;

		this.proxy = proxy; 
		this.proxyUser = proxyUser; 
		this.proxyPassword = proxyPassword ;
	}

	public void initialize() {
		Client client = ClientBuilder.newClient();
		client = setAutheticationConfiguration(client, authDetails, authType);

		URI uri = UriBuilder.fromUri(baseUri).build();    
		// No support for UriTemplate, it doesn't matter much
		WebTarget target = client.target(uri);
		target = setResourcePath(target, pathList);
		target = setQueryParams(target, queryParams);

		Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_PLAIN_TYPE);
		invocationBuilder = setHeaderDetails(invocationBuilder, headers);
		invocationBuilder = invocationBuilder.accept(mediaType); //MediaType.APPLICATION_JSON
		this.invocationBuilder =  invocationBuilder;
	}

	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public MultivaluedMap<String, Object> getResponseHeaders() {
		return responseHeaders;
	}
	
	public void setResponseHeaders(MultivaluedMap<String, Object> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	public String getBaseUri() {
		return baseUri;
	}
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
	public LinkedList<String> getPathList() {
		return pathList;
	}
	public void setPathList(LinkedList<String> pathList) {
		this.pathList = pathList;
	}
	public Map<String, String> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public Map<String, String> getAuthDetails() {
		return authDetails;
	}
	public void setAuthDetails(Map<String, String> authDetails) {
		this.authDetails = authDetails;
	}
	public int getAuthType() {
		return authType;
	}
	public void setAuthType(int authType) {
		this.authType = authType;
	}
	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public String getProxyUser() {
		return proxyUser;
	}
	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}
	public String getProxyPassword() {
		return proxyPassword;
	}
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}



	/**
	 * Http Proxy server settings, required for the API caller to go out of corporate
	 * network and make a call to an external REST API Service.
	 * @param client
	 * @param proxy
	 * @param user
	 * @param password
	 * @return
	 */
	protected Client setHttpProxyConfiguration(Client client, String proxy, String user, String password) {
		ClientConfig config = new ClientConfig();
		config.property(ClientProperties.PROXY_URI, proxy);
		config.property(ClientProperties.PROXY_USERNAME,user);
		config.property(ClientProperties.PROXY_PASSWORD,password);
		client = ClientBuilder.newClient(config);
		return client;
	}

	
	/**
	 * Query parameters to passed while calling a REST API. Query params are provided as
	 * name value pair in a Map.
	 * @param target
	 * @param queryParams
	 * @return
	 */
	protected WebTarget setQueryParams(WebTarget target, Map<String, String> queryParams) {
		for(String paramName : queryParams.keySet()) {
			target = target.queryParam(paramName, queryParams.get(paramName));
		}
		return target;
	}


	/**
	 * API Request Header values. Header values are provided as name value map to the method.
	 * @param invocationBuilder
	 * @param headers
	 * @return
	 */
	protected Invocation.Builder setHeaderDetails(Invocation.Builder invocationBuilder, Map<String, String> headers) {
		for(String headerName : headers.keySet()) {
			invocationBuilder = invocationBuilder.header(headerName, headers.get(headerName));
		}
		return invocationBuilder;
	}

	/**
	 * Relative path w.r.t. base uri for calling the REST API. Method takes the individual
	 * pieces of the relative path as list 
	 * @param target
	 * @param pathList
	 * @return
	 */
	protected WebTarget setResourcePath(WebTarget target, LinkedList<String> pathList) {
		for(String path : pathList) {
			target = target.path(path);
		}
		return target;
	}


	/**
	 * REST API Authentication settings
	 * @param client
	 * @param authDetails
	 * @param authType
	 * @return
	 */
	protected Client setAutheticationConfiguration(Client client, Map<String, String> authDetails, int authType) {
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
			// DO NOTHING for now, investigate why uncommenting above line of code throws exception

		}
		return client;
	}

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


	public static void main(String args[]) {
		System.out.println("Hello");
	}

}
