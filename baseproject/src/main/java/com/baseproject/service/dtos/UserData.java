package com.baseproject.service.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.baseproject.model.entities.User;
import com.baseproject.util.utils.PersistenceUtils;


public class UserData {

	private Long id;
	
	private String name;
	
	private String username;
	
	private ProfileData profile;
	
	private CompanyData company;

	public UserData(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.username = user.getUsername();
		
		if (PersistenceUtils.isLoaded(user.getProfile())) {
			this.profile = new ProfileData(user.getProfile());
		}
		
		if (PersistenceUtils.isLoaded(user.getCompany())) {
			this.company = new CompanyData(user.getCompany());
		}		
	}

	public static UserData build(User user) {
		return new UserData(user);
	}

	public static List<UserData> build(List<User> users) {
		return users.stream().map(u -> new UserData(u)).collect(Collectors.toList());
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ProfileData getProfile() {
		return profile;
	}

	public void setProfile(ProfileData profile) {
		this.profile = profile;
	}

	public CompanyData getCompany() {
		return company;
	}

	public void setCompany(CompanyData company) {
		this.company = company;
	}
}
