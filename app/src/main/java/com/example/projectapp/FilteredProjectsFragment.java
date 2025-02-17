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
    private int categoryId = 0; // Default category (0 = All)

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilteredProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup RecyclerView with ProjectAdapter
        projectAdapter = new ProjectAdapter(requireContext(), new ArrayList<>());
        binding.filteredRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.filteredRecyclerView.setAdapter(projectAdapter);

        // Obtain the shared ProjectViewModel
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Retrieve categoryId from bundle arguments
        if (getArguments() != null) {
            categoryId = getArguments().getInt("categoryId", 0);
        }

        // Use the ViewModel's filter method to filter projects by category (with no API call)
        // We pass an empty query and default amount values.
        projectViewModel.filterProjects("", 0, Integer.MAX_VALUE, categoryId);

        // Observe filtered projects LiveData and update adapter
        projectViewModel.getFilteredProjects().observe(getViewLifecycleOwner(), filteredList -> {
            if (filteredList != null) {
                projectAdapter.setProjects(filteredList);
            }
        });

        // Set up back button listener
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
