package com.example.projectapp.Model;

public class User {
    private String email;
    private String username;
    private String password;

    private String retypePassword;

    private String firstName;
    private String lastName;
    private String bio;
    private String skills;
    private String linkedinURL;

    // Constructor
    public User(String email, String username, String password, String firstName, String lastName, String bio, String skills, String linkedinURL, String retypePassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.skills = skills;
        this.linkedinURL = linkedinURL;
        this.retypePassword = retypePassword;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    // Getters and Setters for each field
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getLinkedinURL() { return linkedinURL; }
    public void setLinkedinURL(String linkedinURL) { this.linkedinURL = linkedinURL; }

    public String getRetypePassword() { return retypePassword; }
    public void setRetypePassword(String retypePassword) { this.retypePassword = retypePassword; }


}
