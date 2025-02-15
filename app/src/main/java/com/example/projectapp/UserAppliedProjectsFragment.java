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
import com.example.projectapp.Adapter.UserProjectsAdapter;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentUserAppliedProjectsBinding;
import java.util.ArrayList;

public class UserAppliedProjectsFragment extends Fragment {

    private FragmentUserAppliedProjectsBinding binding;
    private ProjectViewModel projectViewModel;
    private UserProjectsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserAppliedProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize adapter with the layout for applied projects
        adapter = new UserProjectsAdapter(requireContext(), new ArrayList<>(), R.layout.user_applied_projects_item_view);
        binding.userAppliedProjectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.userAppliedProjectsRecyclerView.setAdapter(adapter);

        // Obtain the shared ProjectViewModel (scoped to the Activity)
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);

        // Load applied projects (this method should update LiveData in the ViewModel)
        projectViewModel.loadUserAppliedProjects();

        // Observe the LiveData for applied projects and update the adapter
        projectViewModel.getAppliedProjectsLiveData().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                adapter.setProjects(projects);
            }
        });

        // Set up navigation listeners
        binding.backButton.setOnClickListener(v -> navigateToProjects());

    }

    private void navigateToProjects() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.myProjectsFragment);
    }

    private void navigateToProjectDetails() {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(R.id.fragmentProjectDetails);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
