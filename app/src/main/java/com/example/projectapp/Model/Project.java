package com.example.projectapp.Model;

import com.google.gson.annotations.SerializedName;

public class Project {
    private int id;
    private String title;
    private String description;
    private String skillsNeeded;
    private String status;
    private int categoryId;
    private int userId;
    private int amount;
    private String createdAt;
    @SerializedName("jsonKey")
    private String updatedAt;

    public Project(String title, String description, int amount, int categoryId, String skillsNeeded) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.categoryId = categoryId;
        this.skillsNeeded = skillsNeeded;
    }

    @SerializedName("jsonKey")


    // Getters and Setters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillsNeeded() {
        return skillsNeeded;
    }

    public void setSkillsNeeded(String skillsNeeded) {
        this.skillsNeeded = skillsNeeded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
