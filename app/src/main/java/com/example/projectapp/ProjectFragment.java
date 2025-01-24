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
import com.example.projectapp.databinding.FragmentMyProjectsSectionBinding;


public class ProjectFragment extends Fragment {

    private FragmentMyProjectsSectionBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyProjectsSectionBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
        hideBottomNavigation();
    }

    private void setupClickListeners() {
        // Set up click listeners for all navigation actions
        binding.goActive.setOnClickListener(v -> navigateTo(R.id.action_projectFragment_to_myActiveProjectsFragment));
        binding.goArchived.setOnClickListener(v -> navigateTo(R.id.action_projectFragment_to_myArchivedProjectsFragment));
        binding.goApplied.setOnClickListener(v -> navigateTo(R.id.action_projectFragment_to_myAppliedApplicationsFragment));
        binding.goAccepted.setOnClickListener(v -> navigateTo(R.id.action_projectFragment_to_myAcceptedApplicationsFragment));
        binding.goRejected.setOnClickListener(v -> navigateTo(R.id.action_projectFragment_to_myRejectedApplicationsFragment));
        binding.goSaved.setOnClickListener(v -> navigateTo(R.id.action_projectFragment_to_mySavedProjectsFragment));

    }

    private void hideBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        }
    }

    private void navigateTo(int actionId) {
        // Unified navigation method to avoid repetitive code
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(actionId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}