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
import com.example.projectapp.Model.Project;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentUserArchivedProjectsBinding;
import java.util.ArrayList;

public class UserArchivedProjectsFragment extends Fragment {

    private FragmentUserArchivedProjectsBinding binding;
    private ProjectViewModel projectViewModel;
    private UserProjectsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserArchivedProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize adapter with the layout for archived projects
        adapter = new UserProjectsAdapter(requireContext(), new ArrayList<>(), R.layout.user_archived_projects_item_view);
        binding.userArchivedProjectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.userArchivedProjectsRecyclerView.setAdapter(adapter);

        // Set the unarchive action listener on the adapter
        adapter.setOnProjectActionListener(new UserProjectsAdapter.OnProjectActionListener() {
            @Override
            public void onArchiveClicked(Project project) {



            }
            @Override
            public void onUnarchiveClicked(Project project) {
                projectViewModel.unarchiveProject(project.getId());
                projectViewModel.loadUserArchivedProjects();
            }

            @Override
            public void onUnfavoriteClicked(Project project) {

            }

            @Override
            public void onManageClicked(Project project) {
                Bundle bundle = new Bundle();
                bundle.putInt("projectId", project.getId());  // Pass project ID to details screen

                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_global_manageFragment, bundle);
            }
        });

        // Obtain the shared ProjectViewModel from the Activity
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        projectViewModel.loadUserArchivedProjects();

        projectViewModel.getArchivedProjectsLiveData().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                adapter.setProjects(projects);
            }
        });

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
