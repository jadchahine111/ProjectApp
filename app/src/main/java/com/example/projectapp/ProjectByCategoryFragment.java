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
import com.example.projectapp.databinding.FragmentProjectByCategoryBinding;
import java.util.ArrayList;

public class ProjectByCategoryFragment extends Fragment {

    private FragmentProjectByCategoryBinding binding;
    private ProjectViewModel projectViewModel;
    private ProjectAdapter projectAdapter;
    private int categoryId = 0; // Default value

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProjectByCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup RecyclerView
        projectAdapter = new ProjectAdapter(requireContext(), new ArrayList<>());
        binding.projectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.projectsRecyclerView.setAdapter(projectAdapter);

        // Obtain shared ProjectViewModel
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Retrieve categoryId from bundle arguments
        if (getArguments() != null) {
            categoryId = getArguments().getInt("categoryId", 0);
        }

        // Call API to load projects by category
        projectViewModel.loadProjectsByCategory(categoryId);

        // Observe filtered projects LiveData
        projectViewModel.getFilteredProjects().observe(getViewLifecycleOwner(), filteredList -> {
            if (filteredList != null) {
                projectAdapter.setProjects(filteredList);
            }
        });

        // Setup back button listener if needed
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
