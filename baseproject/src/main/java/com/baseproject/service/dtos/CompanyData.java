package com.baseproject.service.dtos;

import com.baseproject.model.entities.Company;

public class CompanyData {

	private Long id;
	
	private String name;

	public CompanyData(Company company) {
		this.id = company.getId();
		this.name = company.getName();
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
}
