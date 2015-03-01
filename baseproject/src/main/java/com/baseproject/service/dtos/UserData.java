package com.baseproject.service.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.baseproject.model.entities.Company;
import com.baseproject.model.entities.Profile;
import com.baseproject.model.entities.User;
import com.baseproject.util.utils.PersistenceUtils;


public class UserData {

	private Long id;
	
	private String name;
	
	private String username;
	
	private String password;
	
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

	public static List<User> fetch(List<UserData> userDatas) {
		return userDatas.stream().map(u -> User.repository().fetch(u.getId())).collect(Collectors.toList());
	}

	public static User build(UserData userData) {
		User user = new User();
		
		user.setId(userData.getId());
		user.setName(userData.getName());
		user.setUsername(userData.getUsername());
		user.setPassword(userData.getPassword());
		
		if (userData.getProfile() != null) {
			user.setProfile(Profile.repository().fetch(userData.getProfile().getId()));
		}
		
		if (userData.getCompany() != null) {
			user.setCompany(Company.repository().fetch(userData.getCompany().getId()));
		}
		
		return user;
	}

	public static List<User> build(List<UserData> userDatas) {
		return userDatas.stream().map(u -> build(u)).collect(Collectors.toList());
	}

	public static UserData unbuild(User user) {
		return new UserData(user);
	}

	public static List<UserData> unbuild(List<User> users) {
		return users.stream().map(u -> unbuild(u)).collect(Collectors.toList());
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
