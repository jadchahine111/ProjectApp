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
import com.example.projectapp.databinding.FragmentUserFavoritedProjectsBinding;
import java.util.ArrayList;

public class UserFavoritedProjectsFragment extends Fragment {

    private FragmentUserFavoritedProjectsBinding binding;
    private ProjectViewModel projectViewModel;
    private UserProjectsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserFavoritedProjectsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize adapter with the favorited projects item layout.
        // Ensure that your layout contains a view with id "unfavorite_button".
        adapter = new UserProjectsAdapter(requireContext(), new ArrayList<>(), R.layout.user_favorited_projects_item_view);
        binding.userFavoritedProjectsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.userFavoritedProjectsRecyclerView.setAdapter(adapter);

        // Set the favorite (or in this case, "remove from favorites") click listener.
        adapter.setOnProjectActionListener(new UserProjectsAdapter.OnProjectActionListener() {
            @Override
            public void onArchiveClicked(Project project) {
                // Not used in this fragment.
            }

            @Override
            public void onUnarchiveClicked(Project project) {
                // Not used in this fragment.
            }

            @Override
            public void onUnfavoriteClicked(Project project) {
                // Call your ViewModel to remove this project from favorites.
                projectViewModel.remProjectFromFav(project.getId());
                projectViewModel.loadUserFavoritedProjects();
                projectViewModel.clearSuccessMessage();
            }

            @Override
            public void onManageClicked(Project project) {
                Bundle bundle = new Bundle();
                bundle.putInt("projectId", project.getId());  // Pass project ID to details screen

                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_global_manageFragment, bundle);
            }
        });

        // Obtain the shared ProjectViewModel
        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        projectViewModel.loadUserFavoritedProjects();
        projectViewModel.getFavoritedProjectsLiveData().observe(getViewLifecycleOwner(), projects -> {
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
