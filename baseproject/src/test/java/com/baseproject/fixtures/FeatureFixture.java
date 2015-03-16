package com.baseproject.fixtures;

import com.baseproject.model.entities.Feature;
import com.baseproject.model.entities.FeatureCode;
import com.baseproject.service.data.FeatureData;

public enum FeatureFixture {

	CREATE_COMPANY_INVALID(new Feature(null, false)),
	CREATE_COMPANY(new Feature(FeatureCode.CREATE_COMPANY, false));
	
	private Feature feature;

	private FeatureFixture(Feature feature) {
		this.feature = feature;
	}
	
	public Feature entity() {
		return this.feature;
	}
	
	public FeatureData data() {
		return FeatureData.buildForTest(feature);
	}
	
	public boolean equivalent(Feature f) {
		boolean equivalent = true;

		equivalent = equivalent && this.entity().getCode().equals(f.getCode());
		equivalent = equivalent && this.entity().getVisible().equals(f.getVisible());
		
		return equivalent;
	}
}
