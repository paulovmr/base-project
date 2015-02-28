package com.baseproject.service.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.baseproject.model.entities.Feature;
import com.baseproject.model.entities.FeatureCode;
import com.baseproject.util.utils.PersistenceUtils;

public class FeatureData {
	
	private Long id;
	
	private FeatureCode code;
	
	private List<ProfileData> profiles;

	public FeatureData(Feature feature) {
		this.id = feature.getId();
		this.code = feature.getCode();
		
		if (PersistenceUtils.isLoaded(feature.getProfiles())) {			
			this.profiles = feature.getProfiles().stream().map(p -> new ProfileData(p)).collect(Collectors.toList());
		}
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public FeatureCode getCode() {
		return code;
	}

	public void setCode(FeatureCode code) {
		this.code = code;
	}
	
	public List<ProfileData> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<ProfileData> profiles) {
		this.profiles = profiles;
	}
}
