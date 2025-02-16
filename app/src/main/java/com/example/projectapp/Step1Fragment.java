package com.example.projectapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentStep1Binding;

public class Step1Fragment extends Fragment {

    private FragmentStep1Binding binding;
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
        binding = FragmentStep1Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Observe LiveData for user data
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // You can use the user data for updating the UI if needed
                binding.email.setText(user.getEmail());
                binding.username.setText(user.getUsername());
                binding.password.setText(user.getPassword());
                binding.retypePassword.setText(user.getRetypePassword());

                // If the User object already has data, populate the fields
            }
        });

        // Observe LiveData for error messages
        userViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                // Show error message
                Log.e("UserRepository", errorMessage); // Tag is "UserRepository", but you can change it as needed
            }
        });

        // Save data to ViewModel using TextWatchers
        binding.email.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                if (userViewModel.getUserLiveData().getValue() != null) {
                    userViewModel.getUserLiveData().getValue().setEmail(s.toString());
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.username.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                if (userViewModel.getUserLiveData().getValue() != null) {
                    userViewModel.getUserLiveData().getValue().setUsername(s.toString());
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                if (userViewModel.getUserLiveData().getValue() != null) {
                    userViewModel.getUserLiveData().getValue().setPassword(s.toString());
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.retypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (userViewModel.getUserLiveData().getValue() != null) {
                    userViewModel.getUserLiveData().getValue().setRetypePassword(s.toString());
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        return view;
    }
}
