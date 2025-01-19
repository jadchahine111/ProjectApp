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

import com.example.projectapp.ViewModels.SignUpViewModel;
import com.example.projectapp.databinding.FragmentStep1Binding;

public class Step1Fragment extends Fragment {

    private FragmentStep1Binding binding;
    private SignUpViewModel signUpViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate using binding
        binding = FragmentStep1Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Restore data from ViewModel
        binding.email.setText(signUpViewModel.getEmail().getValue());
        binding.username.setText(signUpViewModel.getUsername().getValue());
        binding.password.setText(signUpViewModel.getPassword().getValue());
        binding.retypePassword.setText(signUpViewModel.getRetypePassword().getValue());

        // Save data to ViewModel using TextWatchers
        binding.email.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getEmail().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.username.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getUsername().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getPassword().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.retypePassword.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getRetypePassword().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return view;
    }
}
