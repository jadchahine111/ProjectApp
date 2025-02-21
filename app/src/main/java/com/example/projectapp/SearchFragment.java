package com.example.projectapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.projectapp.Adapter.CategoryAdapter;
import com.example.projectapp.Model.Category;
import com.example.projectapp.ViewModels.CategoryViewModel;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private CategoryViewModel categoryViewModel;
    private ProjectViewModel projectViewModel;
    private CategoryAdapter categoryAdapter;
    private List<Category> fullCategoryList = new ArrayList<>(); // Store the full category list

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup RecyclerView for categories
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        categoryAdapter = new CategoryAdapter(requireContext(), new ArrayList<>());
        binding.categoryRecyclerView.setAdapter(categoryAdapter);

        // Obtain ViewModels (shared with the activity)
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Observe categories LiveData
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                fullCategoryList = new ArrayList<>(categories);  // Save the full list of categories
                categoryAdapter.setCategories(categories);
            }
        });

        // Search bar text change listener
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCategories(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed.
            }
        });

        // When a category is clicked, navigate to ProjectsByCategoryFragment
        categoryAdapter.setOnCategoryClickListener(category -> {
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", category.getId());
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_searchFragment_to_projectsByCategoryFragment, bundle);
        });

        // Optionally, set up filter button to navigate to a more advanced filter screen
        binding.filterButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigate(R.id.action_searchFragment_to_searchFilterFragment);
        });
    }

    private void filterCategories(String query) {
        List<Category> filteredList = new ArrayList<>();
        for (Category category : fullCategoryList) {
            if (category.getCategoryName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(category);
            }
        }
        categoryAdapter.setCategories(filteredList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
