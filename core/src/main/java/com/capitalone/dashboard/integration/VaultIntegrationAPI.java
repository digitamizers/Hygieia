package com.capitalone.dashboard.integration;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import com.capitalone.dashboard.misc.VaultAccessException;
import com.capitalone.dashboard.util.Supplier;

@Component
public class VaultIntegrationAPI {
	private static final Logger LOGGER = LoggerFactory.getLogger(VaultIntegrationAPI.class);
	private RestOperations restOperations;

	public VaultIntegrationAPI(Supplier<RestOperations> restOperationsSupplier) {
		this.restOperations = restOperationsSupplier.get();
	}
	
	public VaultIntegrationAPI()
	{
		
	}

	/**
	 * get credentials from Vault using assetId and VaultAccessTOken
	 * 
	 * @param assetId
	 * @param vaultAccessToken
	 * @return response from vault with credentials data
	 * @throws JSONException 
	 */
	public JSONObject getDetailsFromVault(String assetId, String vaultAccessToken) throws JSONException {
		// TODO Auto-generated method stub
		String url = null;
		HttpMethod method = HttpMethod.POST;
		JSONObject requestBody = null;
		ResponseEntity<String> result = null;
		JSONObject response = null;
		try {
			if (url == null) {
				url = "http://ec2-34-196-246-23.compute-1.amazonaws.com:8150/api/viewsecret/";
			}
			requestBody = new JSONObject();
			requestBody.put("key_path", "secret/" + assetId);// "key_path":"secret/test"
			result = invokeAPIWithAccessToken(url, method, requestBody.toString(), vaultAccessToken);
			if (result.getStatusCode() == HttpStatus.OK) {
				response = new JSONObject(result.getBody());
			} else {
				response = new JSONObject("{\"response\":{\"data\": {},\"status\":\"Failed\"}}");
			}
			if ("Success".equalsIgnoreCase(response.getJSONObject("response").getString("status"))) {
				response = response.getJSONObject("response").getJSONObject("data").getJSONObject("json_data");
			}
		} catch (Exception e) {
			// TODO: handle exception
			response = new JSONObject("{\"response\":{\"data\": {},\"status\":\"Failed\"}}");
		}
		return response;
	}

	/**
	 * Create authorization header and call Rest API
	 * 
	 * @param url
	 *            endpointUrl
	 * @param method
	 *            Http Method
	 * @param requestBody
	 * @param accessToken
	 * @return Response
	 * @throws SpeedOpsCommonException
	 */
	public ResponseEntity<String> invokeAPIWithAccessToken(String url, HttpMethod method, String assetId,
			String accessToken) throws VaultAccessException {
		ResponseEntity<String> result = null;
		JSONObject requestBody = null;
		try {
			requestBody = new JSONObject();
			requestBody.put("key_path", "secret/" + assetId);// "key_path":"secret/test"
			LOGGER.info("URL : " + url);
			LOGGER.info("Request Body : " + requestBody);
			HttpEntity<String> entity = new HttpEntity<String>(requestBody.toString(), createHeaders(accessToken));

			result = restOperations.exchange(url, method, entity, String.class);
		} catch (HttpClientErrorException e) {
			// TODO: handle exception

			LOGGER.error("Localized Message : " + e.getLocalizedMessage());
			LOGGER.error("Message : " + e.getMessage());
			LOGGER.error("Cause : " + e.getCause());
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				throw new VaultAccessException("Session Expired! Please login and try again.", 401);
			} else {
				result = new ResponseEntity<String>(e.getResponseBodyAsString(), e.getStatusCode());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Localized Message : " + e.getLocalizedMessage());
			LOGGER.error("Message : " + e.getMessage());
			LOGGER.error("Cause : " + e.getCause());

			throw new VaultAccessException("Session Expired! Please login and try again.", 501);
		}

		return result;
	}

	private HttpHeaders createHeaders(String accessToken) {

		HttpHeaders headers = new HttpHeaders();
		if (accessToken != null) {
			headers.add("Authorization", "Bearer " + accessToken);
		}
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		return headers;
	}
}
