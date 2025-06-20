package com.nirmal.project.dto;

import java.util.Date;

public class CategoryResponse {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private Integer createdBy;

    public CategoryResponse () {}

    public CategoryResponse(Integer id, String name, String description, Boolean isActive, Integer createdBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdBy = createdBy;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}
