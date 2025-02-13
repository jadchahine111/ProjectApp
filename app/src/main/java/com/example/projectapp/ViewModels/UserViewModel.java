package com.example.projectapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectapp.Model.User;
import com.example.projectapp.Repository.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> errorMessageLiveData;

    private MutableLiveData<String> skillsLiveData; // Add this to store skills


    public UserViewModel() {
        userRepository = new UserRepository();
        userLiveData = new MutableLiveData<>(new User()); // Initialize User object
        errorMessageLiveData = new MutableLiveData<>();
        skillsLiveData = new MutableLiveData<>(); // Initialize the skills LiveData

    }

    // Expose LiveData
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public MutableLiveData<String> getSkillsLiveData() {
        return skillsLiveData; // Getter for skills
    }

    // Call repository method to register the user
    public void registerUser(User user) {
        userRepository.registerUser(user, new UserRepository.RegisterUserCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.setValue(user); // Update user data on success
            }

            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error); // Update error message on failure
            }
        });
    }
}
