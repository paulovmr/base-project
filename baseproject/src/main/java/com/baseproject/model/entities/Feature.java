package com.baseproject.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.validation.NotNull;
import com.baseproject.util.validation.Unique;

@Entity(name = "features")
public class Feature extends BaseEntity<Feature> {
	
	private static final long serialVersionUID = 3890390287004990551L;

	@Transient
	private static final transient Repository<Feature> REPOSITORY = new Repository<Feature>(Feature.class);

	@NotNull
	@Unique(key = "code")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private FeatureCode code;

	@NotNull
	@Column(nullable = false)
	private Boolean visible;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "features_profiles", joinColumns = {
		@JoinColumn(name = "feature_id", nullable = false, updatable = false)
	}, inverseJoinColumns = {
		@JoinColumn(name = "profile_id", nullable = false, updatable = false) 
	})
	private List<Profile> profiles;
	
	public Feature() {
	}
	
	public static Repository<Feature> repository() {
		return REPOSITORY;
	}

	@Override
	public void prepareForPersist() {
	}

	@Override
	public void prepareForUpdate() {
	}
	
	public FeatureCode getCode() {
		return code;
	}
	
	public void setCode(FeatureCode code) {
		this.code = code;
	}
	
	public Boolean getVisible() {
		return visible;
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public Repository<Feature> getRepository() {
		return REPOSITORY;
	}
}