package com.example.projectapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectapp.Model.User;
import com.example.projectapp.MyApplication.MyApplication;
import com.example.projectapp.Repository.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<String> errorMessageLiveData;
    private MutableLiveData<String> responseLiveData; // LiveData to store raw response
    private MutableLiveData<String> skillsLiveData; // Add this to store skills


    public UserViewModel() {
        userRepository = new UserRepository(MyApplication.getAppContext());
        userLiveData = new MutableLiveData<>(new User()); // Initialize User object
        errorMessageLiveData = new MutableLiveData<>();
        responseLiveData = new MutableLiveData<>(); // Initialize the response LiveData
        skillsLiveData = new MutableLiveData<>(); // Initialize the skills LiveData
        loadUserDetails();

    }

    // Expose LiveData
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public MutableLiveData<String> getSkillsLiveData() {
        return skillsLiveData; //
    }

    public MutableLiveData<String> getResponseLiveData() {
        return responseLiveData; // Getter for raw response body
    }

    // Call repository method to register the user
    public void registerUser(User user) {
        userRepository.registerUser(user, new UserRepository.RegisterUserCallback() {
            @Override
            public void onSuccess(String responseBody) {
                responseLiveData.setValue(responseBody); // Set the raw response body into LiveData
            }

            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error); // Update error message on failure
            }
        });
    }
    public void loadUserDetails() {
        userRepository.getUserDetails(new UserRepository.GetUserDetailsCallback() {
            @Override
            public void onSuccess(User user) {
                userLiveData.setValue(user);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
    public void updateUser(User user) {
        userRepository.updateUserDetails(user, new UserRepository.UpdateUserCallback() {
            @Override
            public void onSuccess(User updatedUser) {
                // Update LiveData with the updated user.
                userLiveData.setValue(updatedUser);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }
}
