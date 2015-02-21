package com.baseproject.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.validation.NotEmpty;
import com.baseproject.util.validation.NotNull;
import com.baseproject.util.validation.ValidationException;

@Entity(name = "users")
public class User extends BaseEntity<User> {

	private static final long serialVersionUID = 64264926208955788L;
	
	@Transient
	private static final transient Repository<User> REPOSITORY = new Repository<User>(User.class);

	@NotEmpty
	@Column(nullable = false, length = 200)
	private String name;

	@NotEmpty
	@Column(nullable = false, length = 200)
	private String username;

	@NotEmpty
	@Column(nullable = false, length = 100)
	private String password;

	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
	private Profile profile;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, optional = false)
	private Company company;
	
	public static Repository<User> repository() {
		return REPOSITORY;
	}
	
	public User() {
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
	
	public String encodedPassword() {
		return password;
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
	protected Repository<User> getRepository() {
		return REPOSITORY;
	}

	@Override
	public User update(User e) throws ValidationException {
		setName(e.getName());
		setUsername(e.getUsername());
		setProfile(e.getProfile());
		
		return this.save();
	}
}