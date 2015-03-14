package com.baseproject.service.services;

import org.junit.Assert;
import org.junit.Test;

import com.baseproject.fixtures.FeatureFixture;
import com.baseproject.model.entities.Feature;
import com.baseproject.test.config.BaseTest;
import com.baseproject.test.config.ResponseError;

public class FixtureServiceTest extends BaseTest {

	@Test
	public void createAndFetch() {
		Long id = insert(Feature.class, FeatureFixture.CREATE_COMPANY.get());
		Feature feature = fetch(Feature.class, id);
		Assert.assertTrue(FeatureFixture.CREATE_COMPANY.equivalent(feature));
	}

	@Test
	public void updateAndFetch() {
		Feature feature = FeatureFixture.CREATE_COMPANY.get();
		Long id = insert(Feature.class, feature);
		feature = fetch(Feature.class, id);
		
		feature.setVisible(true);
		update(Feature.class, feature);

		Feature updatedFeature = fetch(Feature.class, id);
		
		Assert.assertEquals(updatedFeature.getVisible(), true);
	}

	@Test(expected = ResponseError.class)
	public void deleteAndFetch() {
		Feature feature = FeatureFixture.CREATE_COMPANY.get();
		Long id = insert(Feature.class, feature);
		feature = fetch(Feature.class, id);
		
		feature.setVisible(true);
		delete(Feature.class, feature);

		fetch(Feature.class, id);
	}
}
