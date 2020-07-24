package ${package}.rest;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClient {

	private static RestClient restClient = null;
	private final Client client;

	private RestClient() {
		client = ClientBuilder.newClient();
	}

	public static RestClient getInstance() {
		if (restClient == null) {
			restClient = new RestClient();
		}
		return restClient;
	}

	public String getRequest(String baseUrl, String subUrl, String apiKey, String apiKeyValue) {
		Response response;
		String body = null;
		WebTarget webTarget = client.target(baseUrl).path(subUrl);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(apiKey, apiKeyValue);
		response = invocationBuilder.get();
		if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			System.err.println("Status Error: " + response.getStatus());
		} else {
			body = response.readEntity(String.class);
			System.out.println("Status " + subUrl + " " + response.getStatusInfo().getReasonPhrase());
		}
		response.close();
		return body;
	}

	public int putRequest(String baseUrl, String subUrl, String apiKey, String apiKeyValue, String json) {
		Response response;
		int status;
		WebTarget webTarget = client.target(baseUrl).path(subUrl);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(apiKey, apiKeyValue);
		response = invocationBuilder.put(Entity.json(json));
		if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			System.err.println("Status Error: " + response.getStatus());
			status = response.getStatus();
		} else {
			System.out.println("Status " + subUrl + " " + response.getStatusInfo().getReasonPhrase());
			status = response.getStatus();
		}
		response.close();
		return status;

	}

	public Response postRequest(String baseUrl, String subUrl, String authKey, String authValue, String json) {
		Response response;
		WebTarget webTarget = client.target(baseUrl).path(subUrl);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header(authKey, authValue);
		response = invocationBuilder.post(Entity.json(json));

		if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			System.err.println("Status Error: " + response.getStatus() + ": " + json);
			response = null;
		} else {
			System.out.println("Status " + subUrl + " " + response.getStatusInfo().getReasonPhrase());
		}
		return response;

	}
}
