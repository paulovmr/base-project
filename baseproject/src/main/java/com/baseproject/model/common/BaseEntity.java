package com.baseproject.model.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Date updatedAt;
	
	@Column(nullable = false)
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void updateEntity(Date date) {
		if (getId() == null) {
			this.createdAt = date;
		}
		
		this.updatedAt = date;
	}
}