package com.example.projectapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
                binding.tvPassword.setText(user.getPassword());

                binding.tvFirstName.setText(user.getFirstName());
                binding.tvLastName.setText(user.getLastName());
                binding.tvBio.setText(user.getBio());
                binding.tvLinkedIn.setText(user.getLinkedinURL());
            }
        });

        // Final confirmation button
        binding.signup.setOnClickListener(v -> {
            // Get user data from ViewModel
            User user = userViewModel.getUserLiveData().getValue();
            if (user == null) {
                Toast.makeText(getContext(), "Please complete the form", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate the data
            if (isValidUserData(user)) {
                // Call the registerUser method from ViewModel to submit the data
                userViewModel.registerUser(user);
            } else {
                Toast.makeText(getContext(), "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe the registration result
        userViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            // Handle success response
            Log.d("Step4Fragment", "Registration successful: " + response);
            Toast.makeText(getContext(), "Sign-up successful", Toast.LENGTH_SHORT).show();
            // Optionally, navigate to another screen after successful registration
        });

        userViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), error -> {
            // Handle error response
            Log.e("Step4Fragment", "Registration failed: " + error);
            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private boolean isValidUserData(User user) {
        return user.getEmail() != null && !user.getEmail().isEmpty() &&
                user.getUsername() != null && !user.getUsername().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty() &&
                user.getRetypePassword() != null && !user.getRetypePassword().isEmpty() &&
                user.getFirstName() != null && !user.getFirstName().isEmpty() &&
                user.getLastName() != null && !user.getLastName().isEmpty() &&
                user.getPassword().equals(user.getRetypePassword());  // Add this validation
    }

}
