package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.databinding.FragmentSuccessEmailVerificationBinding;

public class SuccessEmailVerificationFragment extends Fragment {

    private FragmentSuccessEmailVerificationBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using View Binding
        binding = FragmentSuccessEmailVerificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideBottomNavigation();
        setupClickListeners();
    }

    private void hideBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }
    }

    private void setupClickListeners() {
        // Set up click listeners for all navigation actions
        binding.goToLogin.setOnClickListener(v -> navigateTo(R.id.action_successEmailVerificationFragment_to_loginFragment));


    }

    private void navigateTo(int actionId) {
        // Unified navigation method to avoid repetitive code
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(actionId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Avoid memory leaks by nullifying the binding
        binding = null;
    }
}
