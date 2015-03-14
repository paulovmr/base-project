package com.baseproject.service.services;

import java.util.List;

import org.junit.Test;

import com.baseproject.model.entities.User;
import com.baseproject.service.data.UserData;
import com.baseproject.test.config.BaseTest;
import com.baseproject.test.config.Response;

public class UserServiceTest extends BaseTest {

	@Test
	public void createUser() {
		User u = new User();
		u.setUsername("paulovmr");
		insert(User.class, u);
		Response response = browser.get("/users");
		List<UserData> users = response.getEntities(UserData.class);
		
		for (UserData user : users) {
			System.out.println("Usuario: " + user.getUsername());
		}
	}
}
