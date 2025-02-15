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

import com.example.projectapp.Adapter.ProjectAdapter;
import com.example.projectapp.Model.Project;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProjectViewModel projectViewModel;
    private ProjectAdapter projectAdapter;

    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show Bottom Navigation
        showBottomNavigation();

        // Initialize ViewModel
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Set welcome text and subtext (username & email)
                binding.ownerUsername.setText(user.getUsername());
            }
        });
        // Setup the RecyclerView and Adapter
        setupRecyclerView();

        // Observe data from ViewModel
        observeViewModel();

        // Setup click listeners (notification button, etc.)
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
     * Sets up the RecyclerView with a ProjectAdapter.
     */
    private void setupRecyclerView() {
        // 1. Initialize the adapter with an empty list (or pass a list if you have one)
        projectAdapter = new ProjectAdapter(requireContext(), new ArrayList<>());

        // 2. Attach a layout manager
        binding.projectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 3. Set the adapter
        binding.projectsRecyclerView.setAdapter(projectAdapter);
    }

    /**
     * Observes the LiveData from the ProjectViewModel to update UI automatically.
     */
    private void observeViewModel() {
        // Observe the list of projects
        projectViewModel.getFilteredProjects().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                projectAdapter.setProjects(projects);
            }
        });


        // Observe any error message
        projectViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                // Show a toast, or set an error text, etc.
                // Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
                binding.recentProjectsText.setText(errorMsg);
            }
        });
    }

    /**
     * Sets up click listeners (e.g., notification button).
     */
    private void setupListeners() {
        binding.notificationButton.setOnClickListener(v -> navigateToNotificationFragment());
    }

    /**
     * Navigates to the Notification Fragment.
     */
    private void navigateToNotificationFragment() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.action_homeFragment_to_notificationFragment);
    }

    /**
     * Navigates to the Project Details Fragment, potentially passing project data/ID.
     */
    private void navigateToProjectDetailsFragment(Project project) {
        // Example navigation with SafeArgs or direct
        // Bundle bundle = new Bundle();
        // bundle.putInt("projectId", project.getId());
        // NavController navController = Navigation.findNavController(binding.getRoot());
        // navController.navigate(R.id.action_homeFragment_to_projectDetailsFragment, bundle);

        // For now, direct
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
