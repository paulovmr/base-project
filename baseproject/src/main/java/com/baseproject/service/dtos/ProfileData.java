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

	public static List<Profile> fetch(List<ProfileData> profileDatas) {
		return profileDatas.stream().map(p -> Profile.repository().fetch(p.getId())).collect(Collectors.toList());
	}

	public static Profile build(ProfileData profileData) {
		Profile profile = new Profile();
		
		profile.setId(profileData.getId());
		profile.setName(profileData.getName());
		
		if (profileData.getFeatures() != null) {
			profile.setFeatures(FeatureData.fetch(profileData.getFeatures()));
		}
		
		return profile;
	}

	public static List<Profile> build(List<ProfileData> profileDatas) {
		return profileDatas.stream().map(p -> build(p)).collect(Collectors.toList());
	}

	public static ProfileData unbuild(Profile profile) {
		return new ProfileData(profile);
	}

	public static List<ProfileData> unbuild(List<Profile> profiles) {
		return profiles.stream().map(p -> unbuild(p)).collect(Collectors.toList());
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
