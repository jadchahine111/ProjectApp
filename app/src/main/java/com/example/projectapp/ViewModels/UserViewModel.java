package com.example.projectapp.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
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
    private MutableLiveData<User> otherUserLiveData;

    private MutableLiveData<String> verificationStatusLiveData;  // Add this for verification status


    public UserViewModel() {
        userRepository = new UserRepository(MyApplication.getAppContext());
        userLiveData = new MutableLiveData<>(new User()); // Initialize User object
        otherUserLiveData = new MutableLiveData<>();
        errorMessageLiveData = new MutableLiveData<>();
        responseLiveData = new MutableLiveData<>(); // Initialize the response LiveData
        skillsLiveData = new MutableLiveData<>(); // Initialize the skills LiveData
        verificationStatusLiveData = new MutableLiveData<>();  // Initialize LiveData for verification status
        loadUserDetails();

    }

    public LiveData<String> getVerificationStatus() {
        return verificationStatusLiveData;  // Expose verification status LiveData
    }

    // Add method to check email verification status
    public void checkEmailVerification(String email) {
        userRepository.checkEmailVerificationStatus(email, new UserRepository.EmailVerificationCallback() {
            @Override
            public void onSuccess(String status) {
                verificationStatusLiveData.postValue(status);
            }

            @Override
            public void onFailure(String error) {
                errorMessageLiveData.postValue(error);
            }
        });
    }


    public void setSkills(String skills) {
        // Update skillsLiveData
        skillsLiveData.setValue(skills);

        // Update the skills field in the User object
        User user = userLiveData.getValue();
        if (user != null) {
            user.setSkills(skills);
            userLiveData.setValue(user); // Update userLiveData
        }
    }

    // Expose LiveData
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }
    public MutableLiveData<User> getOtherUserLiveData() {
        return otherUserLiveData;
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
                Log.d("UserViewModel", "Registration success: " + responseBody);
            }

            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error); // Update error message on failure
                Log.e("UserViewModel", "Registration failed: " + error);

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
    public void loadOtherUserDetails(int id) {
        userRepository.getOtherUserDetailsById(id, new UserRepository.GetOtherUserDetailsCallback() {
            @Override
            public void onSuccess(User user) {
                otherUserLiveData.setValue(user);
            }
            @Override
            public void onFailure(String error) {
                errorMessageLiveData.setValue(error);
            }
        });
    }


}
