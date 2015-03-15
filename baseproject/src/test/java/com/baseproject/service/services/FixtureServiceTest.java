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
		Long id = insert(Feature.class, FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fetch(Feature.class, id);
		Assert.assertTrue(FeatureFixture.CREATE_COMPANY.equivalent(feature));
	}

	@Test
	public void updateAndFetch() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		Long id = insert(Feature.class, feature);
		feature = fetch(Feature.class, id);
		
		feature.setVisible(true);
		update(Feature.class, feature);

		Feature updatedFeature = fetch(Feature.class, id);
		
		Assert.assertEquals(updatedFeature.getVisible(), true);
	}

	@Test
	public void deleteAndFetch() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		Long id = insert(Feature.class, feature);
		feature = fetch(Feature.class, id);
		
		feature.setVisible(true);
		delete(Feature.class, feature);

		try {
			fetch(Feature.class, id);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}

	@Test
	public void fetchUnexistent() {
		try {
			fetch(Feature.class, 999l);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}

	@Test
	public void updateUnexistent() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		
		try {
			update(Feature.class, feature);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}

	@Test
	public void deleteUnexistent() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		
		try {
			delete(Feature.class, feature);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}

	@Test
	public void deleteAll() {
		Long id = insert(Feature.class, FeatureFixture.CREATE_COMPANY.entity());
		fetch(Feature.class, id);
		
		browser.delete("/fixtures");
		
		try {
			fetch(Feature.class, id);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}
}
