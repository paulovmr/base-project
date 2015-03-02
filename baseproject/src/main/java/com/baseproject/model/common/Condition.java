package com.baseproject.model.common;

public class Condition {
	
	private String field;
	
	private String operator;
	
	private Object value;
	
	public Condition(String field, String operator, Object value) {
		this.field = field;
		this.operator = operator;
		this.value = value;
	}
	
	public String getField() {
		return field;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public Object getValue() {
		return value;
	}
}
