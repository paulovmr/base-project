package com.baseproject.service.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.baseproject.model.entities.Profile;
import com.baseproject.util.utils.PersistenceUtils;

public class ProfileData {
	
	private Long id;
	
	private String name;
	
	private List<FeatureData> features;

	public ProfileData(Profile profile) {
		this.id = profile.getId();
		this.name = profile.getName();
		
		if (PersistenceUtils.isLoaded(profile.getFeatures())) {			
			this.features = profile.getFeatures().stream().map(f -> new FeatureData(f)).collect(Collectors.toList());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FeatureData> getFeatures() {
		return features;
	}

	public void setFeatures(List<FeatureData> features) {
		this.features = features;
	}
}
