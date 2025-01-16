package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectapp.ViewModels.SignUpViewModel;

public class Step4Fragment extends Fragment {

    private SignUpViewModel signUpViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step4, container, false);

        // Bind Views (Step 1)
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvUsername = view.findViewById(R.id.tv_username);
        TextView tvPassword = view.findViewById(R.id.tv_password);

        // Bind Views (Step 2)
        TextView tvFirstName = view.findViewById(R.id.tv_firstName);
        TextView tvLastName = view.findViewById(R.id.tv_lastName);
        TextView tvBio = view.findViewById(R.id.tv_bio);
        TextView tvLinkedIn = view.findViewById(R.id.tv_linkedIn);

        // Observe ViewModel data for Step 1 and set it to TextViews
        signUpViewModel.getEmail().observe(getViewLifecycleOwner(), tvEmail::setText);
        signUpViewModel.getUsername().observe(getViewLifecycleOwner(), tvUsername::setText);
        signUpViewModel.getPassword().observe(getViewLifecycleOwner(), tvPassword::setText);

        // Observe ViewModel data for Step 2 and set it to TextViews
        signUpViewModel.getFirstName().observe(getViewLifecycleOwner(), tvFirstName::setText);
        signUpViewModel.getLastName().observe(getViewLifecycleOwner(), tvLastName::setText);
        signUpViewModel.getBio().observe(getViewLifecycleOwner(), tvBio::setText);
        signUpViewModel.getLinkedinURL().observe(getViewLifecycleOwner(), tvLinkedIn::setText);

        return view;
    }
}
