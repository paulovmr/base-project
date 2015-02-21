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

import org.codehaus.jackson.annotate.JsonIgnore;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.validation.NotEmpty;
import com.baseproject.util.validation.ValidationException;

@Entity(name = "features")
public class Feature extends BaseEntity<Feature> {
	
	private static final long serialVersionUID = 3890390287004990551L;

	@Transient
	private static final transient Repository<Feature> REPOSITORY = new Repository<Feature>(Feature.class);

	@NotEmpty
	@Column(nullable = false, length = 200)
	private String name;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "features_profiles", joinColumns = {
		@JoinColumn(name = "feature_id", nullable = false, updatable = false)
	}, inverseJoinColumns = {
		@JoinColumn(name = "profile_id", nullable = false, updatable = false) 
	})
	private List<Profile> profiles;
	
	public static Repository<Feature> repository() {
		return REPOSITORY;
	}
	
	public Feature() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public List<Profile> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	protected Repository<Feature> getRepository() {
		return REPOSITORY;
	}

	@Override
	public Feature update(Feature e) throws ValidationException {
		setName(e.getName());
		setProfiles(e.getProfiles());
		
		return this.save();
	}
}