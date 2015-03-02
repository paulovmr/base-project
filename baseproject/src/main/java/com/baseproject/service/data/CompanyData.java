package com.baseproject.service.data;

import java.util.List;
import java.util.stream.Collectors;

import com.baseproject.model.entities.Company;

public class CompanyData {

	private Long id;
	
	private String name;

	public CompanyData(Company company) {
		this.id = company.getId();
		this.name = company.getName();
	}

	public static List<Company> fetch(List<CompanyData> companyDatas) {
		return companyDatas.stream().map(c -> Company.repository().fetch(c.getId())).collect(Collectors.toList());
	}

	public static Company build(CompanyData companyData) {
		Company company = new Company();
		
		company.setId(companyData.getId());
		company.setName(companyData.getName());
		
		return company;
	}

	public static List<Company> build(List<CompanyData> companyDatas) {
		return companyDatas.stream().map(c -> build(c)).collect(Collectors.toList());
	}

	public static CompanyData unbuild(Company company) {
		return new CompanyData(company);
	}

	public static List<CompanyData> unbuild(List<Company> companies) {
		return companies.stream().map(c -> unbuild(c)).collect(Collectors.toList());
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
