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
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentFilteredProjectsBinding;
import java.util.ArrayList;

public class FilteredProjectsFragment extends Fragment {

    private FragmentFilteredProjectsBinding binding;
    private ProjectViewModel projectViewModel;
    private ProjectAdapter projectAdapter;

    // Default filter values; these can be overridden via Bundle arguments
    private String query = "";
    private int minAmount = 0;
    private int maxAmount = Integer.MAX_VALUE;
    private int categoryId = 0; // 0 means "all categories"

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilteredProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve filter parameters from Bundle if provided
        if (getArguments() != null) {
            query = getArguments().getString("query", "");
            minAmount = getArguments().getInt("minAmount", 0);
            maxAmount = getArguments().getInt("maxAmount", Integer.MAX_VALUE);
            categoryId = getArguments().getInt("categoryId", 0);
        }

        // Setup RecyclerView with the ProjectAdapter
        projectAdapter = new ProjectAdapter(requireContext(), new ArrayList<>());
        binding.filteredRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.filteredRecyclerView.setAdapter(projectAdapter);

        // Obtain the shared ProjectViewModel
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Call the API to load filtered projects using the filter parameters
        projectViewModel.loadFilteredProjects(query, minAmount, maxAmount, categoryId);

        // Observe the filtered projects LiveData and update the adapter
        projectViewModel.getFilteredProjects().observe(getViewLifecycleOwner(), filteredList -> {
            if (filteredList != null) {
                projectAdapter.setProjects(filteredList);
            }
        });

        // Setup back button listener
        binding.backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigateUp();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
