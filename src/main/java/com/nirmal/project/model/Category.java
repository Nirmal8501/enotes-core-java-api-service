package com.nirmal.project.model;

import java.util.Date;

public class Category extends BaseModel{

	private Integer id;
	private String name;
	private String description;
	
	public Category() {
		
	}
	
	public Category(Integer id, String name, String description, Boolean isActive, Boolean isDeleted, Integer createdBy, Date createdOn, Integer updatedBy, Date updatedOn) {
	    super(isActive, isDeleted, createdBy, createdOn, updatedBy, updatedOn);  // Call BaseModel constructor
	    this.id = id;
	    this.name = name;
	    this.description = description;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", isActive=" + getIsActive() +
				", isDeleted=" + getIsDeleted() +
				", createdBy=" + getCreatedBy() +
				", createdOn=" + getCreatedOn() +
				", updatedBy=" + getUpdatedBy() +
				", updatedOn=" + getUpdatedOn() +
				'}';
	}
}
