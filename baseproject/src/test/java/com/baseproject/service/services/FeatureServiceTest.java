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
		Response create = browser.post(FeatureFixture.CREATE_COMPANY.data(), "/features");
		Response fetch = browser.get(create.getLocation());
		
		Feature persistedFeature = fetch.getEntity(Feature.class);
		Assert.assertTrue(FeatureFixture.CREATE_COMPANY.equivalent(persistedFeature));
	}
}
