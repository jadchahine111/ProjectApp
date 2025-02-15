package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectapp.Adapter.NotificationAdapter;
import com.example.projectapp.Adapter.UserProjectsAdapter;
import com.example.projectapp.ViewModels.NotificationViewModel;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentUserPostedProjectsBinding;

import java.util.ArrayList;

public class UserPostedProjectsFragment extends Fragment {

    private FragmentUserPostedProjectsBinding binding;

    private ProjectViewModel projectViewModel;

    private UserProjectsAdapter userProjectsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserPostedProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);


        // Setup the RecyclerView and Adapter
        setupRecyclerView();

        // Observe data from ViewModel
        observeViewModel();


        setupListeners();
        hideBottomNavigation();


    }

    private void hideBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }
    }

    private void setupListeners() {
        binding.backButton.setOnClickListener(v -> navigateToProjects());
    }

    private void navigateToProjects() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.myProjectsFragment);
    }

    private void navigateToProjectDetails() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.fragmentProjectDetails);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding to null to avoid memory leaks
        binding = null;
    }

    private void setupRecyclerView() {
        // 1. Initialize the adapter with an empty list (or pass a list if you have one)
        userProjectsAdapter = new UserProjectsAdapter(requireContext(), new ArrayList<>());

        // 2. Attach a layout manager
        binding.userPostedProjectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 3. Set the adapter
        binding.userPostedProjectsRecyclerView.setAdapter(userProjectsAdapter);
    }

    private void observeViewModel() {
        // Observe the list of projects
        projectViewModel.getAllUserActiveProjects().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                userProjectsAdapter.setProjects(projects);
            }
        });


        // Observe any error message
        projectViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                // Show a toast, or set an error text, etc.
                // Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
                binding.specializationTitle.setText(errorMsg);
            }
        });
    }
}
