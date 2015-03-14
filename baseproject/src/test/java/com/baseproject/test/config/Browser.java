package com.baseproject.test.config;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.baseproject.util.utils.JsonUtils;
import com.baseproject.util.validation.ValidationException;
import com.baseproject.util.validation.ValidationFailure;

public class Browser {
	
	private static final String BASE_URL = "http://localhost:8081/baseproject/api";

	@SuppressWarnings("unchecked")
	public <T> List<T> getn(Class<T> clazz, String url, Object... params) {
		try {
			HttpGet httpGet = new HttpGet(BASE_URL + String.format(url, params));
			String json = doRequest(httpGet, null);
			return (List<T>) JsonUtils.fromJson(json, List.class);
		} catch (ValidationException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T get(Class<T> clazz, String url, Object... params) {
		try {
			HttpGet httpGet = new HttpGet(BASE_URL + String.format(url, params));
			String json = doRequest(httpGet, null);
			return (T) JsonUtils.fromJson(json, clazz);
		} catch (ValidationException e) {
			throw new RuntimeException(e);
		}
	}

	public void post(Object entity, String url, Object... params) throws ValidationException {
		HttpPost httpPost = new HttpPost(BASE_URL + String.format(url, params));		
		doRequest(httpPost, entity);
	}

	public void put(Object entity, String url, Object... params) throws ValidationException {
		HttpPut httpPut = new HttpPut(BASE_URL + String.format(url, params));		
		doRequest(httpPut, entity);
	}

	public void delete(String url, Object... params) {
		try {
			HttpDelete httpDelete = new HttpDelete(BASE_URL + String.format(url, params));
			doRequest(httpDelete, null);
		} catch (ValidationException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private String doRequest(HttpRequestBase method, Object entity) throws ValidationException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String json = JsonUtils.toJson(entity);
		
		ResponseHandler<String> handler = new ResponseHandler<String>() {
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300 || status == 422) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
		
		try {
			if (entity != null) {
				method.addHeader("Content-Type", MediaType.TEXT_PLAIN);
				HttpEntity httpEntity = new StringEntity(json);
				((HttpEntityEnclosingRequestBase) method).setEntity(httpEntity);
			}
			
			response = httpClient.execute(method);
			int code = response.getStatusLine().getStatusCode();
			String responseEntity = handler.handleResponse(response);
		    
		    if (code == 422) {
		    	throw new ValidationException((List<ValidationFailure>) JsonUtils.fromJson(responseEntity, List.class));
		    }
		    
		    return responseEntity;
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (method != null) {
					method.releaseConnection();
				}
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
