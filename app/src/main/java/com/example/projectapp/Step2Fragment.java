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

public class Step2Fragment extends Fragment {



    private SignUpViewModel signUpViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step2, container, false);

        // Initialize input fields
        EditText firstNameInput = view.findViewById(R.id.first_name);
        EditText lastNameInput = view.findViewById(R.id.last_name);
        EditText bioInput = view.findViewById(R.id.bio);
        EditText linkedinUrlInput = view.findViewById(R.id.linkedin_url);

        // Restore data from ViewModel
        firstNameInput.setText(signUpViewModel.getFirstName().getValue());
        lastNameInput.setText(signUpViewModel.getLastName().getValue());
        bioInput.setText(signUpViewModel.getBio().getValue());
        linkedinUrlInput.setText(signUpViewModel.getLinkedinURL().getValue());

        // Save data to ViewModel
        firstNameInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getFirstName().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        lastNameInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getLastName().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        bioInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getBio().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        linkedinUrlInput.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                signUpViewModel.getLinkedinURL().setValue(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return view;
    }
}
