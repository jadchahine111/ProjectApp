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

import com.example.projectapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show Bottom Navigation
        showBottomNavigation();

        // Set up notification button click listener
        setupListeners();
    }

    /**
     * Displays the Bottom Navigation view in MainActivity.
     */
    private void showBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        }
    }

    /**
     * Sets up the notification button to navigate to the Notification Fragment.
     */
    private void setupListeners() {
        binding.notificationButton.setOnClickListener(v -> navigateToNotificationFragment());
        binding.projectCard.setOnClickListener(v -> navigateToProjectDetailsFragment());

    }

    /**
     * Navigates to the Notification Fragment.
     */
    private void navigateToNotificationFragment() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.action_homeFragment_to_notificationFragment);
    }

    private void navigateToProjectDetailsFragment() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.action_homeFragment_to_projectDetailsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Prevent memory leaks by nullifying the binding reference
        binding = null;
    }
}
