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
import com.example.projectapp.databinding.FragmentStep2Binding;

public class Step2Fragment extends Fragment {

    private FragmentStep2Binding binding;
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
        binding = FragmentStep2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Restore data from ViewModel
        binding.firstName.setText(signUpViewModel.getFirstName().getValue());
        binding.lastName.setText(signUpViewModel.getLastName().getValue());
        binding.bio.setText(signUpViewModel.getBio().getValue());
        binding.linkedinUrl.setText(signUpViewModel.getLinkedinURL().getValue());

        // Save data to ViewModel using TextWatchers
        binding.firstName.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getFirstName().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.lastName.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getLastName().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.bio.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getBio().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.linkedinUrl.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getLinkedinURL().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return view;
    }
}
