package com.baseproject.model.common;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

	private List<Condition> conditions;
	
	private Conditions() {
		conditions = new ArrayList<>();
	}
	
	public static Conditions create() {
		return new Conditions();
	}
	
	public Conditions and(String field, String operator, Object value) {
		conditions.add(new Condition(field, operator, value));
		return this;
	}
	
	public List<Condition> getConditions() {
		return conditions;
	}
}
