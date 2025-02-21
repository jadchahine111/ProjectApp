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
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain both ViewModels (shared with the Activity)
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Observe categories and set up the spinner as before
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            categoryList.clear();
            // Add an "All" category at position 0
            Category catAll = new Category();
            catAll.setId(0);
            catAll.setCategoryName("All");
            categoryList.add(catAll);
            if (categories != null) {
                categoryList.addAll(categories);
            }
            List<String> categoryNames = new ArrayList<>();
            for (Category c : categoryList) {
                categoryNames.add(c.getCategoryName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    categoryNames
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinner.setAdapter(adapter);
            binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCategoryId = categoryList.get(position).getId();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        });

        categoryViewModel.loadCategories();

        // Back button → navigate up
        binding.backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigateUp();
        });

        // "Apply Filter" button: now call the API-based filter method
        binding.goToLogin.setOnClickListener(v -> applyFilterAndNavigate());
    }

    private void applyFilterAndNavigate() {
        // Gather user inputs
        String query = binding.searchBar.getText().toString().trim();

        int min = 0;
        if (!TextUtils.isEmpty(binding.minAmount.getText())) {
            min = Integer.parseInt(binding.minAmount.getText().toString());
        }

        int max = Integer.MAX_VALUE;
        if (!TextUtils.isEmpty(binding.maxAmount.getText())) {
            max = Integer.parseInt(binding.maxAmount.getText().toString());
        }

        // Call the API-based filter method in the ProjectViewModel
        projectViewModel.filterProjectsApi(query, min, max, selectedCategoryId);

        // Navigate to FilteredProjectsFragment
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.action_searchFilterFragment_to_filteredProjectsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}