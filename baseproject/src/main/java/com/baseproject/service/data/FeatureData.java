package com.baseproject.service.data;

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

	public static List<Feature> fetch(List<FeatureData> featureDatas) {
		return featureDatas.stream().map(f -> Feature.repository().fetch(f.getId())).collect(Collectors.toList());
	}

	public static Feature build(FeatureData featureData) {
		Feature feature = new Feature();
		
		feature.setId(featureData.getId());
		feature.setCode(featureData.getCode());
		
		if (featureData.getProfiles() != null) {
			feature.setProfiles(ProfileData.fetch(featureData.getProfiles()));
		}
		
		return feature;
	}

	public static List<Feature> build(List<FeatureData> featureDatas) {
		return featureDatas.stream().map(f -> build(f)).collect(Collectors.toList());
	}

	public static FeatureData unbuild(Feature feature) {
		return new FeatureData(feature);
	}

	public static List<FeatureData> unbuild(List<Feature> features) {
		return features.stream().map(f -> unbuild(f)).collect(Collectors.toList());
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
