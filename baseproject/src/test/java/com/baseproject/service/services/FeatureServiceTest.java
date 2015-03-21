package com.baseproject.service.services;

import org.junit.Assert;
import org.junit.Test;

import com.baseproject.fixtures.FeatureFixture;
import com.baseproject.model.entities.Feature;
import com.baseproject.service.data.FeatureData;
import com.baseproject.test.config.BaseTest;
import com.baseproject.util.http.ResponseError;

public class FeatureServiceTest extends BaseTest {

	@Test
	public void createFeature() {
		id = browser.post(FeatureFixture.CREATE_COMPANY.data(), "/features").getId();

		Assert.assertTrue(lastResponse().created());
		Assert.assertTrue(lastResponse().hasLocation());
				
		Feature persistedFeature = fx.fetch(Feature.class, id);
		Assert.assertTrue(FeatureFixture.CREATE_COMPANY.equivalent(persistedFeature));
	}

	@Test
	public void updateFeature() {
		id = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fx.fetch(Feature.class, id);
		feature.setVisible(true);
		
		browser.put(FeatureData.buildForTest(feature), "/features/%d", feature.getId());
		Assert.assertTrue(lastResponse().ok());
		
		Feature persistedFeature = fx.fetch(Feature.class, id);
		Assert.assertTrue(persistedFeature.equals(feature));
	}

	@Test
	public void deleteFeature() {
		id = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		
		browser.delete("/features/%d", id);
		Assert.assertTrue(lastResponse().ok());

		try {
			fx.fetch(Feature.class, id);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().notFound());
		}
	}

	@Test
	public void createFeatureWithValidationError() {
		browser.post(FeatureFixture.CREATE_COMPANY_INVALID.data(), "/features");
		Assert.assertTrue(lastResponse().validationError());
	}

	@Test
	public void updateFeatureWithValidationError() {
		id = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = FeatureFixture.CREATE_COMPANY_INVALID.entity();
		feature.setId(id);
		
		browser.put(FeatureData.buildForTest(feature), "/features/%d", feature.getId());
		Assert.assertTrue(lastResponse().validationError());
	}

	@Test
	public void fetchUnexistentFeature() {
		browser.get("/features/%d", 1);
		Assert.assertTrue(lastResponse().notFound());
	}

	@Test
	public void updateUnexistentFeature() {
		id = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fx.fetch(Feature.class, id);
		feature.setId(999l);
		
		browser.put(FeatureData.buildForTest(feature), "/features/%d", feature.getId());
		Assert.assertTrue(lastResponse().notFound());
	}

	@Test
	public void deleteUnexistentFeature() {
		browser.delete("/features/%d", 999);
		Assert.assertTrue(lastResponse().notFound());
	}
}
