package com.baseproject.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.util.validation.Length;
import com.baseproject.util.validation.NotEmpty;

@Entity(name = "companies")
public class Company extends BaseEntity<Company> {
	
	@Transient
	private static final transient Repository<Company> REPOSITORY = new Repository<Company>(Company.class);

	@NotEmpty
	@Length(max = 255)
	@Column(nullable = false, length = 255)
	private String code;

	@NotEmpty
	@Length(max = 255)
	@Column(nullable = false, length = 255)
	private String name;
	
	public Company() {
	}
	
	public static Repository<Company> repository() {
		return REPOSITORY;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Repository<Company> getRepository() {
		return REPOSITORY;
	}
}