package com.example.projectapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.Model.Project;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentProjectDetailsBinding;

public class ProjectDetailsFragment extends Fragment {

    private FragmentProjectDetailsBinding binding;
    private ProjectViewModel projectViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProjectDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the ProjectViewModel shared with the activity
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Retrieve the projectId passed via arguments
        if (getArguments() != null) {
            int projectId = getArguments().getInt("projectId");

            // Fetch project details using the ViewModel
            projectViewModel.fetchProjectDetails(projectId);

            // Observe the project details LiveData
            projectViewModel.getProjectDetails().observe(getViewLifecycleOwner(), project -> {
                if (project != null) {
                    binding.projectTitle.setText(project.getTitle());
                    binding.projectAdditionalDetails.setText(project.getDescription());
                    binding.projectDescriptionDetails.setText(project.getSkillsNeeded());

                    // Update Save button state for favoriting
                    if (project.isSaved()) {
                        binding.saveButton.setText("Saved");
                        binding.saveButton.setEnabled(false);
                    } else {
                        binding.saveButton.setText("Save");
                        binding.saveButton.setEnabled(true);
                        binding.saveButton.setOnClickListener(v -> {
                            projectViewModel.addProjectToFav(project);
                        });
                    }

                    // Update Apply button state
                    if (project.isApplied()) {
                        binding.applyButton.setText("Applied");
                        binding.applyButton.setEnabled(false);
                    } else {
                        binding.applyButton.setText("Apply");
                        binding.applyButton.setEnabled(true);
                        binding.applyButton.setOnClickListener(v -> {
                            projectViewModel.applyToProject(projectId);  // Trigger API call to apply
                        });
                    }
                }
            });

            // Observe success messages and update buttons accordingly
            projectViewModel.getSuccessMessageLiveData().observe(getViewLifecycleOwner(), successMessage -> {
                if (successMessage != null) {
                    Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show();
                    projectViewModel.clearSuccessMessage(); // Clear after showing

                    String lowerCaseMsg = successMessage.toLowerCase();
                    // If the message indicates the project was applied for
                    if (lowerCaseMsg.contains("applied")) {
                        binding.applyButton.setText("Applied");
                        binding.applyButton.setEnabled(false);
                    }
                    // If the message indicates the project was favorited (or saved)
                    if (lowerCaseMsg.contains("saved") || lowerCaseMsg.contains("favorited")) {
                        binding.saveButton.setText("Saved");
                        binding.saveButton.setEnabled(false);
                    }
                }
            });

            // Observe error messages and display as Toast
            projectViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
                if (!TextUtils.isEmpty(errorMessage)) {
                    Toast.makeText(requireContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    projectViewModel.clearErrorMessage(); // Clear after showing
                }
            });
        }

        // Set up the back button to pop the back stack
        binding.backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.popBackStack();
        });

        hideBottomNavigation();
    }

    private void hideBottomNavigation() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}