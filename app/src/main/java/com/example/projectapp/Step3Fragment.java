package com.example.projectapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentStep3Binding;

public class Step3Fragment extends Fragment {

    private FragmentStep3Binding binding;
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
        binding = FragmentStep3Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Observe LiveData for user data
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // You can update the UI with the existing data if needed
            }
        });

        // TextWatcher to capture frontID and backID
        binding.frontIdLabel.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                // Update frontID in the user model
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.backIdLabel.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                // Update backID in the user model
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        return view;
    }
}
