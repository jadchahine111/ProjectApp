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
import com.example.projectapp.Adapter.CategoryAdapter;
import com.example.projectapp.ViewModels.CategoryViewModel;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentSearchBinding;
import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private CategoryViewModel categoryViewModel;
    private ProjectViewModel projectViewModel;
    private CategoryAdapter categoryAdapter;

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
                categoryAdapter.setCategories(categories);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
