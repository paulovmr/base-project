package com.baseproject.test.config;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.http.Header;
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
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.baseproject.util.utils.JsonUtils;

public class Browser {
	
	private static final String BASE_URL = "http://localhost:8081/baseproject/api";

	public Response get(String url, Object... params) {
		HttpGet httpGet = new HttpGet(buildURL(url, params));
		return doRequest(httpGet, null, null);
	}

	public Response post(Object entity, String url, Object... params) {
		return post(MediaType.APPLICATION_JSON, entity, url, params);
	}

	public Response put(Object entity, String url, Object... params) {
		return put(MediaType.APPLICATION_JSON, entity, url, params);
	}

	public Response delete(String url, Object... params) {
		HttpDelete httpDelete = new HttpDelete(buildURL(url, params));
		return doRequest(httpDelete, null, null);
	}

	public Response post(String contentType, Object entity, String url, Object... params) {
		HttpPost httpPost = new HttpPost(buildURL(url, params));		
		return doRequest(httpPost, entity, contentType);
	}

	public Response put(String contentType, Object entity, String url, Object... params) {
		HttpPut httpPut = new HttpPut(buildURL(url, params));		
		return doRequest(httpPut, entity, contentType);
	}

	private String buildURL(String url, Object... params) {
		if (url.startsWith("http")) {
			return String.format(url, params);
		}
		
		return BASE_URL + String.format(url, params);
	}

	private Response doRequest(HttpRequestBase method, Object entity, String contentType) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String json = JsonUtils.toJson(entity);
		
		ResponseHandler<String> handler = new ResponseHandler<String>() {
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            }
        };
		
		try {
			if (entity != null) {
				method.addHeader("Content-Type", contentType);
				HttpEntity httpEntity = new ByteArrayEntity(json.getBytes("UTF-8"));
				((HttpEntityEnclosingRequestBase) method).setEntity(httpEntity);
			}
			
			response = httpClient.execute(method);
			
			int code = response.getStatusLine().getStatusCode();
			String responseEntity = handler.handleResponse(response);
			Header location = response.getLastHeader("Location");
		    
			if (location != null) {
				return new Response(code, responseEntity, location.getValue());
			} else {
				return new Response(code, responseEntity);
			}
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
