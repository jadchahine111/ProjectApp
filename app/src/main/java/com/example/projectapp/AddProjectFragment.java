package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.Model.Category;
import com.example.projectapp.Model.Project;
import com.example.projectapp.ViewModels.CategoryViewModel;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentAddProjectBinding;

import java.util.ArrayList;
import java.util.List;

public class AddProjectFragment extends Fragment {

    private FragmentAddProjectBinding binding;
    private CategoryViewModel categoryViewModel;
    private ProjectViewModel projectViewModel;
    private final List<Category> categoryList = new ArrayList<>();
    private int selectedCategoryId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddProjectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        binding.backButton.setOnClickListener(v -> navController.popBackStack());

        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Observe success message to show a Toast
        projectViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null && !successMessage.isEmpty()) {
                // Ensure the fragment's view is still attached before showing the Toast
                if (isAdded()) {
                    // Show Toast
                    Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show();
                    // Reset the success message in ViewModel after showing the Toast
                    projectViewModel.resetSuccessMessage();
                }
            }
        });

        categoryViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryList.clear();
                categoryList.addAll(categories);

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
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!categoryList.isEmpty()) {
                    selectedCategoryId = categoryList.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        categoryViewModel.loadCategories();

        binding.addButton.setOnClickListener(v -> {
            String title = binding.projectTitle.getText().toString().trim();
            String description = binding.projectDescriptionDetails.getText().toString().trim();
            String skillsNeeded = binding.skillsNeeded.getText().toString().trim();
            String amountText = binding.amount.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty() || amountText.isEmpty() || skillsNeeded.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountText);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            Project newProject = new Project(title, description, amount, selectedCategoryId, skillsNeeded);
            projectViewModel.addProject(newProject);
        });

        // Observe error messages
        projectViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
