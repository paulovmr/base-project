package com.baseproject.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.util.validation.NotEmpty;

@Entity
@Table(name = "UserTable")
public class User extends BaseEntity {

	private static final long serialVersionUID = 64264926208955788L;

	@NotEmpty
	@Column(nullable = false)
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}