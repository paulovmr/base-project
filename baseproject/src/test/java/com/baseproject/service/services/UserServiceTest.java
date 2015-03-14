package com.baseproject.service.services;

import java.util.List;

import org.junit.Test;

import com.baseproject.model.entities.User;
import com.baseproject.service.data.UserData;
import com.baseproject.test.config.BaseTest;
import com.baseproject.util.utils.JsonUtils;
import com.baseproject.util.validation.ValidationException;

public class UserServiceTest extends BaseTest {

	@Test
	public void createUser() {
		User u = new User();
		u.setUsername("paulovmr");
		try {
			insert(User.class, u);
		} catch (ValidationException e) {
			System.out.println(JsonUtils.toJson(e.getValidationFailures()));
		}
		List<UserData> users = browser.getn(UserData.class, "/users");
		
		for (UserData user : users) {
			System.out.println("Usuario: " + user.getUsername());
		}
	}
}
