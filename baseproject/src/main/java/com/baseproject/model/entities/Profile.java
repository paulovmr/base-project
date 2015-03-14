package com.baseproject.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.validation.Length;
import com.baseproject.util.validation.NotEmpty;

@Entity(name = "profiles")
public class Profile extends BaseEntity<Profile> {
	
	@Transient
	private static final transient Repository<Profile> REPOSITORY = new Repository<Profile>(Profile.class);

	@NotEmpty
	@Length(max = 255)
	@Column(nullable = false, length = 255)
	private String name;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "features_profiles", joinColumns = { 
		@JoinColumn(name = "profile_id", nullable = false, updatable = false) 
	}, inverseJoinColumns = {
		@JoinColumn(name = "feature_id", nullable = false, updatable = false)
	})
	private List<Feature> features;
	
	public Profile() {
	}
	
	public static Repository<Profile> repository() {
		return REPOSITORY;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Feature> getFeatures() {
		return features;
	}
	
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	@Override
	public Repository<Profile> getRepository() {
		return REPOSITORY;
	}
}