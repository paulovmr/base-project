package com.baseproject.service.services;

import org.junit.Assert;
import org.junit.Test;

import com.baseproject.fixtures.CompanyFixture;
import com.baseproject.fixtures.FeatureFixture;
import com.baseproject.model.entities.Company;
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
	public void createWithValidationError() {
		Feature feature = FeatureFixture.CREATE_COMPANY_INVALID.entity();
		
		try {
			insert(Feature.class, feature);
		} catch (ResponseError e) {
			Assert.assertEquals(422, e.getResponse().getCode());
		}
	}

	@Test
	public void updateWithValidationError() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		Long id = insert(Feature.class, feature);
		
		feature = fetch(Feature.class, id);
		feature.setId(id);
		
		try {
			update(Feature.class, feature);
		} catch (ResponseError e) {
			Assert.assertEquals(422, e.getResponse().getCode());
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
		Long id = insert(Feature.class, FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fetch(Feature.class, id);
		
		feature.setId(999l);
		
		try {
			update(Feature.class, feature);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}

	@Test
	public void deleteUnexistent() {
		Long id = insert(Feature.class, FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fetch(Feature.class, id);
		
		feature.setId(999l);
		
		try {
			delete(Feature.class, feature);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}

	@Test
	public void deleteAll() {
		Long featureId = insert(Feature.class, FeatureFixture.CREATE_COMPANY.entity());
		fetch(Feature.class, featureId);

		Long companyId = insert(Company.class, CompanyFixture.ACME.entity());
		fetch(Company.class, companyId);
		
		browser.delete("/fixtures");
		
		try {
			fetch(Feature.class, featureId);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
		
		try {
			fetch(Company.class, companyId);
		} catch (ResponseError e) {
			Assert.assertEquals(404, e.getResponse().getCode());
		}
	}
}
