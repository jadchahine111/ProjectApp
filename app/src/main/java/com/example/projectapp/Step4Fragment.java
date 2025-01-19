package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.projectapp.ViewModels.SignUpViewModel;
import com.example.projectapp.databinding.FragmentStep4Binding;

public class Step4Fragment extends Fragment {

    private FragmentStep4Binding binding;
    private SignUpViewModel signUpViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize ViewModel
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate using binding
        binding = FragmentStep4Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Observe ViewModel data and bind to TextViews
        signUpViewModel.getEmail().observe(getViewLifecycleOwner(), binding.tvEmail::setText);
        signUpViewModel.getUsername().observe(getViewLifecycleOwner(), binding.tvUsername::setText);
        signUpViewModel.getPassword().observe(getViewLifecycleOwner(), binding.tvPassword::setText);

        signUpViewModel.getFirstName().observe(getViewLifecycleOwner(), binding.tvFirstName::setText);
        signUpViewModel.getLastName().observe(getViewLifecycleOwner(), binding.tvLastName::setText);
        signUpViewModel.getBio().observe(getViewLifecycleOwner(), binding.tvBio::setText);
        signUpViewModel.getLinkedinURL().observe(getViewLifecycleOwner(), binding.tvLinkedIn::setText);

        return view;
    }
}
