package com.baseproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.validation.NotEmpty;
import com.baseproject.util.validation.ValidationException;

@Entity
public class Users extends BaseEntity<Users> {

	private static final long serialVersionUID = 64264926208955788L;
	
	@Transient
	private static final transient Repository<Users> REPOSITORY = new Repository<Users>(Users.class);

	@NotEmpty
	@Column(nullable = false, length = 200)
	private String name;

	@NotEmpty
	@Column(nullable = false, length = 200)
	private String username;

	@NotEmpty
	@Column(nullable = false, length = 100)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private Profile profile;
	
	public static Users createUser() {
		return new Users();
	}
	
	public static Repository<Users> repository() {
		return REPOSITORY;
	}
	
	private Users() {
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
	
	public String getPassword() {
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

	@Override
	protected Repository<Users> getRepository() {
		return REPOSITORY;
	}

	@Override
	public Users update(Users e) throws ValidationException {
		setName(e.getName());
		setUsername(e.getUsername());
		setProfile(e.getProfile());
		
		return this.save();
	}
}