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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projectapp.Adapter.CategoryAdapter;
import com.example.projectapp.ViewModels.CategoryViewModel;
import com.example.projectapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private CategoryViewModel categoryViewModel;
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

        // Set up RecyclerView for categories
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        categoryAdapter = new CategoryAdapter(requireContext(), new ArrayList<>());
        binding.categoryRecyclerView.setAdapter(categoryAdapter);

        // Obtain the CategoryViewModel (scoped to the activity if shared)
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        // Observe the filtered categories LiveData
        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryAdapter.setCategories(categories);
            }
        });

        // Optionally, observe error messages
        categoryViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                // Display error (e.g., via Toast)
            }
        });

        // Attach a TextWatcher to the search bar
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter categories as user types
                categoryViewModel.filterCategories(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
