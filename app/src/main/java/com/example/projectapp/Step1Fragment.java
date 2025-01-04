package com.example.projectapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.ViewModels.SignUpViewModel;

public class Step1Fragment extends Fragment {


    private SignUpViewModel signUpViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step1, container, false);

        EditText emailInput = view.findViewById(R.id.email);
        EditText usernameInput = view.findViewById(R.id.username);
        EditText passwordInput = view.findViewById(R.id.password);
        EditText retypePasswordInput = view.findViewById(R.id.retype_password);

        // Restore data from ViewModel
        emailInput.setText(signUpViewModel.getEmail().getValue());
        usernameInput.setText(signUpViewModel.getUsername().getValue());
        passwordInput.setText(signUpViewModel.getPassword().getValue());

        // Save data to ViewModel
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getEmail().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        usernameInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getUsername().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getPassword().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        retypePasswordInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getRetypePassword().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return view;
    }

}
