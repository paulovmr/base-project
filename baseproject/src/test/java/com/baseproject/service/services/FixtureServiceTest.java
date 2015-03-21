package com.baseproject.service.services;

import org.junit.Assert;
import org.junit.Test;

import com.baseproject.fixtures.CompanyFixture;
import com.baseproject.fixtures.FeatureFixture;
import com.baseproject.model.entities.Company;
import com.baseproject.model.entities.Feature;
import com.baseproject.test.config.BaseTest;
import com.baseproject.util.http.ResponseError;

public class FixtureServiceTest extends BaseTest {

	@Test
	public void createAndFetchEntity() {
		id = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fx.fetch(Feature.class, id);
		Assert.assertTrue(FeatureFixture.CREATE_COMPANY.equivalent(feature));
	}

	@Test
	public void updateAndFetchEntity() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		
		id = fx.insert(feature);
		feature = fx.fetch(Feature.class, id);
		
		feature.setVisible(true);
		fx.update(feature);

		Feature updatedFeature = fx.fetch(Feature.class, feature.getId());
		
		Assert.assertEquals(updatedFeature.getVisible(), true);
	}

	@Test
	public void deleteAndFetchEntity() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		
		id = fx.insert(feature);
		feature = fx.fetch(Feature.class, id);
		
		fx.delete(feature);

		try {
			fx.fetch(Feature.class, id);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().notFound());
		}
	}

	@Test
	public void createEntityWithValidationError() {
		Feature feature = FeatureFixture.CREATE_COMPANY_INVALID.entity();
		
		try {
			id = fx.insert(feature);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().validationError());
		}
	}

	@Test
	public void updateEntityWithValidationError() {
		Feature feature = FeatureFixture.CREATE_COMPANY.entity();
		id = fx.insert(feature);
		
		feature = FeatureFixture.CREATE_COMPANY_INVALID.entity();
		feature.setId(id);
		
		try {
			fx.update(feature);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().validationError());
		}
	}

	@Test
	public void fetchUnexistentEntity() {
		try {
			fx.fetch(Feature.class, 999l);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().notFound());
		}
	}

	@Test
	public void updateUnexistentEntity() {
		id = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fx.fetch(Feature.class, id);
		
		feature.setId(999l);
		
		try {
			fx.update(feature);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().notFound());
		}
	}

	@Test
	public void deleteUnexistentEntity() {
		id = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		Feature feature = fx.fetch(Feature.class, id);
		
		feature.setId(999l);
		
		try {
			fx.delete(feature);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().notFound());
		}
	}

	@Test
	public void deleteAllEntities() {
		Long featureId = fx.insert(FeatureFixture.CREATE_COMPANY.entity());
		fx.fetch(Feature.class, featureId);

		Long companyId = fx.insert(CompanyFixture.ACME.entity());
		fx.fetch(Company.class, companyId);
		
		browser.delete("/fixtures");
		
		try {
			fx.fetch(Feature.class, featureId);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().notFound());
		}
		
		try {
			fx.fetch(Company.class, companyId);
			Assert.fail();
		} catch (ResponseError e) {
			Assert.assertTrue(lastResponse().notFound());
		}
	}
}
