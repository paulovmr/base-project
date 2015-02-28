package com.baseproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.validation.NotEmpty;

@Entity(name = "companies")
public class Company extends BaseEntity<Company> {
	
	private static final long serialVersionUID = -6962657441501642397L;

	@Transient
	private static final transient Repository<Company> REPOSITORY = new Repository<Company>(Company.class);

	@NotEmpty
	@Column(nullable = false, length = 200)
	private String name;
	
	public static Repository<Company> repository() {
		return REPOSITORY;
	}
	
	public Company() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Repository<Company> getRepository() {
		return REPOSITORY;
	}
}