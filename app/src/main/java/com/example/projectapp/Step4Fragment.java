package com.example.projectapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.Model.User;
import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentStep4Binding;

public class Step4Fragment extends Fragment {

    private FragmentStep4Binding binding;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate using binding
        binding = FragmentStep4Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Observe LiveData for user data to display on confirmation page
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Display user data for review
                binding.tvEmail.setText(user.getEmail());
                binding.tvUsername.setText(user.getUsername());
                binding.tvPassword.setText(user.getPassword());
                binding.tvFirstName.setText(user.getBio());
                binding.tvLastName.setText(user.getLastName());
                binding.tvBio.setText(user.getBio());
                binding.tvLinkedIn.setText(user.getLinkedinURL());

            }
        });

        // Final confirmation button
        binding.signup.setOnClickListener(v -> {
            // Log the current value of responseLiveData

            // Get the user object to register
            User user = userViewModel.getUserLiveData().getValue();

            // Proceed with registration logic
            if (user != null) {
                userViewModel.registerUser(user);
            } else {
                Log.e("UserRegistration", "User object is null, cannot proceed with registration.");
            }

            // Observe the response live data using the fragment's view lifecycle owner
            userViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
                Log.d("UserRegistration", "Registration request: " + user);

                Log.d("UserRegistration", "Registration response: " + response);
                // After registration, you can navigate to the success or login screen based on the response
            });

            // If there’s an error during registration, you can observe errorMessageLiveData as well
            userViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
                Log.e("UserRegistration", "Registration failed: " + errorMessage);
                // Handle the error message (e.g., show a toast or alert)
            });
        });

        return view;
    }
}
