package com.baseproject.service.services;

import org.junit.Assert;
import org.junit.Test;

import com.baseproject.fixtures.FeatureFixture;
import com.baseproject.model.entities.Feature;
import com.baseproject.test.config.BaseTest;
import com.baseproject.test.config.Response;

public class FeatureServiceTest extends BaseTest {

	@Test
	public void createFeature() {
		Response createResponse = browser.post(FeatureFixture.CREATE_COMPANY.data(), "/features");
		Response fetchResponse = browser.get(createResponse.getLocation());
		
		Feature persistedFeature = fetchResponse.getEntity(Feature.class);
		Assert.assertTrue(FeatureFixture.CREATE_COMPANY.equivalent(persistedFeature));
	}

	@Test
	public void updateFeature() {
		Response createResponse = browser.post(FeatureFixture.CREATE_COMPANY.data(), "/features");
		Response fetchResponse = browser.get(createResponse.getLocation());
		
		Feature persistedFeature = fetchResponse.getEntity(Feature.class);
		Assert.assertTrue(FeatureFixture.CREATE_COMPANY.equivalent(persistedFeature));
	}
}
