package com.baseproject.fixtures;

import com.baseproject.model.entities.Feature;
import com.baseproject.model.entities.FeatureCode;

public enum FeatureFixture {
	
	CREATE_COMPANY(new Feature(FeatureCode.CREATE_COMPANY, false));
	
	private Feature feature;

	private FeatureFixture(Feature feature) {
		this.feature = feature;
	}
	
	public Feature get() {
		return this.feature;
	}
	
	public boolean equivalent(Feature f) {
		boolean equivalent = true;

		equivalent = equivalent && this.get().getCode().equals(f.getCode());
		equivalent = equivalent && this.get().getVisible().equals(f.getVisible());
		
		return equivalent;
	}
}
