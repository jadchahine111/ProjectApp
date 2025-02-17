package com.example.projectapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout using view binding
        binding = FragmentProjectDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        if (getArguments() != null) {
            int projectId = getArguments().getInt("projectId");

            // Fetch project details using the ViewModel
            projectViewModel.fetchProjectDetails(projectId);

            // Observe project details
            projectViewModel.getProjectDetails().observe(getViewLifecycleOwner(), new Observer<Project>() {
                @Override
                public void onChanged(Project project) {
                    if (project != null) {
                        // Set the data to the views
                        binding.projectTitle.setText(project.getTitle());
                        binding.projectAdditionalDetails.setText(project.getDescription());
                        binding.projectDescriptionDetails.setText(project.getSkillsNeeded());

                        // Update button text and make it unclickable if already saved
                        if (project.isSaved()) {
                            binding.saveButton.setText("Saved");
                            binding.saveButton.setEnabled(false);
                        } else {
                            binding.saveButton.setText("Save");
                            binding.saveButton.setEnabled(true);

                            // Handle click event to save project
                            binding.saveButton.setOnClickListener(v -> {
                                projectViewModel.addProjectToFav(project);
                            });
                        }
                    }
                }
            });

            // Observe success message
            projectViewModel.getSuccessMessageLiveData().observe(getViewLifecycleOwner(), successMessage -> {
                if (successMessage != null) {
                    Toast.makeText(requireContext(), "Project added to favorites!", Toast.LENGTH_SHORT).show();
                    binding.saveButton.setText("Saved");
                    binding.saveButton.setEnabled(false);
                    projectViewModel.clearSuccessMessage(); // Clear the message after showing
                }
            });

            projectViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
                if (errorMessage != null) {
                    Toast.makeText(requireContext(), "Failed to save project: " + errorMessage, Toast.LENGTH_SHORT).show();
                    projectViewModel.clearErrorMessage(); // Clear the error message after showing
                }
            });

        }

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
        // Nullify binding to avoid memory leaks
        binding = null;
    }
}
