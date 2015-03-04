package com.baseproject.service.services;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import com.baseproject.test.config.BaseTest;

public class UserServiceTest extends BaseTest {

	@Test
	public void test() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8081/baseproject/api/users");
		CloseableHttpResponse response = null;
		
		try {
			response = httpClient.execute(httpGet);
		    System.out.println(response.getEntity().toString());
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		System.out.println("Test runned!");
	}
}
