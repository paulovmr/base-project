package com.baseproject.service.security;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baseproject.model.entities.Company;
import com.baseproject.model.entities.User;
import com.baseproject.util.utils.DateUtils;
import com.baseproject.util.utils.JsonUtils;
import com.baseproject.util.utils.TwoWayEncryptionUtils;

public class Token implements Principal {

	private Long userId;
	
	private String userUsername;
	
	private String userName;
	
	private Long companyId;
	
	private String companyName;
	
	private List<String> features;
	
	public Date creationDate;

	private Token(Long userId, String userUsername, String userName,
			Long companyId, String companyName, List<String> features,
			Date creationDate) {
		super();
		this.userId = userId;
		this.userUsername = userUsername;
		this.userName = userName;
		this.companyId = companyId;
		this.companyName = companyName;
		this.features = features;
		this.creationDate = creationDate;
	}

	public static Token fromUser(User user) {
		Long userId = user.getId();
		String userUsername = user.getUsername();
		String userName = user.getName();
		Long companyId = user.getCompany().getId();
		String companyName = user.getCompany().getName();
		List<String> features = user.getProfile().getFeatures().stream().map(f -> f.getCode().name()).collect(Collectors.toList());
		Date creationDate = DateUtils.now();
		
		Token token = new Token(userId, userUsername, userName, companyId, companyName, features, creationDate);
		
		return token;
	}

	public static Token fromEncryptedJson(String encryptedJson) {
		String json = TwoWayEncryptionUtils.decrypt(encryptedJson);
		
		return JsonUtils.fromJson(json, Token.class);
	}
	
	public String encrypted() {
		String json = JsonUtils.toJson(this);
		
		return TwoWayEncryptionUtils.encrypt(json);
	}
	
	public User getUser() {
		User user = new User();
		
		user.setId(userId);
		user.setUsername(userUsername);
		user.setName(userName);
		user.setCompany(getCompany());
		
		return user;
	}
	
	public Company getCompany() {
		Company company = new Company();
		
		company.setId(companyId);
		company.setName(companyName);
		
		return company;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public String getUserName() {
		return userName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public List<String> getFeatures() {
		return features;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public String getName() {
		return getUserName();
	}
}
