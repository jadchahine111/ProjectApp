package com.example.projectapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.Model.Category;
import com.example.projectapp.ViewModels.CategoryViewModel;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentSearchFilterBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterFragment extends Fragment {

    private FragmentSearchFilterBinding binding;
    private CategoryViewModel categoryViewModel;
    private ProjectViewModel projectViewModel;

    private final List<Category> categoryList = new ArrayList<>();
    private int selectedCategoryId = 0; // 0 = "All" categories

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Obtain both ViewModels (shared with other fragments/activities)
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // 2. Observe categories from CategoryViewModel
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            // Clear old data
            categoryList.clear();

            // Add an "All" category at position 0
            Category catAll = new Category();
            catAll.setId(0);
            catAll.setCategoryName("All");
            categoryList.add(catAll);

            // Add categories from the API
            if (categories != null) {
                categoryList.addAll(categories);
            }

            // Build a list of names for the spinner
            List<String> categoryNames = new ArrayList<>();
            for (Category c : categoryList) {
                categoryNames.add(c.getCategoryName());
            }

            // Create Spinner adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    categoryNames
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinner.setAdapter(adapter);

            // Handle spinner selection
            binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(
                        AdapterView<?> parent,
                        View view,
                        int position,
                        long id
                ) {
                    // store the selected category ID
                    selectedCategoryId = categoryList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });
        });

        // 3. If categories not loaded yet, call loadCategories()
        categoryViewModel.loadCategories();

        // Back button → navigate up or popBackStack
        binding.backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigateUp();
        });

        // 4. "Apply Filter" button
        //    → Filter the projects, then go to FilteredProjectsFragment
        binding.goToLogin.setOnClickListener(v -> applyFilterAndNavigate());
    }

    /**
     * Reads user inputs, calls filterProjects, and navigates to the new fragment.
     */
    private void applyFilterAndNavigate() {
        // 1. Gather user inputs
        String query = binding.searchBar.getText().toString().trim();

        int min = 0;
        if (!TextUtils.isEmpty(binding.minAmount.getText())) {
            min = Integer.parseInt(binding.minAmount.getText().toString());
        }

        int max = Integer.MAX_VALUE;
        if (!TextUtils.isEmpty(binding.maxAmount.getText())) {
            max = Integer.parseInt(binding.maxAmount.getText().toString());
        }

        // 2. Call filter method in ProjectViewModel
        projectViewModel.filterProjects(query, min, max, selectedCategoryId);

        // 3. Navigate to FilteredProjectsFragment (instead of navigateUp)
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.action_searchFilterFragment_to_filteredProjectsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
