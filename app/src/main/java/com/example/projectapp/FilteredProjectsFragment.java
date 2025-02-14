package com.example.projectapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectapp.Adapter.ProjectAdapter;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentFilteredProjectsBinding;

import java.util.ArrayList;

public class FilteredProjectsFragment extends Fragment {

    private FragmentFilteredProjectsBinding binding;
    private ProjectViewModel projectViewModel;
    private ProjectAdapter projectAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilteredProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Setup RecyclerView + same adapter class
        projectAdapter = new ProjectAdapter(requireContext(), new ArrayList<>());
        binding.filteredRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.filteredRecyclerView.setAdapter(projectAdapter);

        // 2. Obtain the same ProjectViewModel (scoped to activity or however you set it up)
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // 3. Observe the filtered list
        projectViewModel.getFilteredProjects().observe(getViewLifecycleOwner(), filteredList -> {
            if (filteredList != null) {
                projectAdapter.setProjects(filteredList);
            }
        });

        // (Optional) if you want a “Back” or “Close” button
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
