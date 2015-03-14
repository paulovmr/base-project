package com.baseproject.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.utils.OneWayEncryptionUtils;
import com.baseproject.util.validation.Length;
import com.baseproject.util.validation.NotEmpty;
import com.baseproject.util.validation.NotNull;
import com.baseproject.util.validation.Unique;

@Entity(name = "users")
public class User extends BaseEntity<User> {

	@Transient
	private static final transient Repository<User> REPOSITORY = new Repository<User>(User.class);

	@NotEmpty
	@Length(max = 255)
	@Column(nullable = false, length = 255)
	private String name;

	@NotEmpty
	@Length(max = 255)
	@Unique(key = "username")
	@Column(nullable = false, length = 255)
	private String username;

	@NotEmpty
	@Length(max = 255)
	@Column(nullable = false, length = 255)
	private String password;

	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
	private Profile profile;

	@NotNull
	@Unique(key = "username")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
	private Company company;
	
	public User() {
	}
	
	public static Repository<User> repository() {
		return REPOSITORY;
	}

	@Override
	public void prepareForPersist() {
		super.prepareForPersist();
		setPassword(OneWayEncryptionUtils.encode(password));
	}

	public String encodedPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public Repository<User> getRepository() {
		return REPOSITORY;
	}
}