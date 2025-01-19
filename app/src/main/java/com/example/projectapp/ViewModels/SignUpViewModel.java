package com.example.projectapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> retypePassword = new MutableLiveData<>();

    private MutableLiveData<String> firstName = new MutableLiveData<>();
    private MutableLiveData<String> lastName = new MutableLiveData<>();
    private MutableLiveData<String> bio = new MutableLiveData<>();
    private MutableLiveData<String> skills = new MutableLiveData<>();

    private MutableLiveData<String> linkedinURL = new MutableLiveData<>();


    // Getters and setters for each field
    public MutableLiveData<String> getEmail() { return email; }

    public MutableLiveData<String> getUsername() { return username; }
    public MutableLiveData<String> getPassword() { return password; }
    public MutableLiveData<String> getFirstName() { return firstName; }
    public MutableLiveData<String> getLastName() { return lastName; }
    public MutableLiveData<String> getBio() { return bio; }
    public MutableLiveData<String> getLinkedinURL() { return linkedinURL; }

    public MutableLiveData<String> getRetypePassword() { return retypePassword; }

    public MutableLiveData<String> getSkills() {
        return skills;
    }

}

