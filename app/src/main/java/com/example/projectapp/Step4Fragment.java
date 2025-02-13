package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
            // Proceed with registration logic
            userViewModel.registerUser(userViewModel.getUserLiveData().getValue());
            // Navigate to success or login screen
        });

        return view;
    }
}
