package com.baseproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Table;

import com.baseproject.model.common.Entity;
import com.baseproject.util.validation.NotEmpty;

@javax.persistence.Entity
@Table(name = "UserTable")
public class User extends Entity {

	private static final long serialVersionUID = 64264926208955788L;

	@NotEmpty
	@Column(nullable = false)
	private String name;
	
	@SuppressWarnings("unused")
	private User() {
	}
	
	public User(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}