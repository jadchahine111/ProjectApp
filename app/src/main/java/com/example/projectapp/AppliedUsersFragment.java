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
import com.example.projectapp.Adapter.AppliedUsersAdapter;
import com.example.projectapp.Model.User;
import com.example.projectapp.ViewModels.ProjectViewModel;
import com.example.projectapp.databinding.FragmentAppliedUsersBinding;
import java.util.ArrayList;

public class AppliedUsersFragment extends Fragment {

    private FragmentAppliedUsersBinding binding;
    private ProjectViewModel projectViewModel;
    private AppliedUsersAdapter adapter;
    private int projectId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAppliedUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve projectId from arguments
        if (getArguments() != null) {
            projectId = getArguments().getInt("projectId", -1);
        }

        adapter = new AppliedUsersAdapter(requireContext(), new ArrayList<>());
        binding.appliedUsersRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.appliedUsersRecyclerView.setAdapter(adapter);

        adapter.setOnUserActionListener(new AppliedUsersAdapter.OnUserActionListener() {
            @Override
            public void onAcceptClicked(User user) {
                projectViewModel.acceptProjectApplicant(projectId, user.getId(), message -> {
                    // On success, update adapter status for this user
                    adapter.setStatusForUser(user.getId(), "Accepted");
                }, error -> {
                    // Optionally, show error feedback
                });
            }

            @Override
            public void onDeclineClicked(User user) {
                projectViewModel.declineProjectApplicant(projectId, user.getId(), message -> {
                    adapter.setStatusForUser(user.getId(), "Declined");
                }, error -> {
                    // Optionally, show error feedback
                });
            }

            @Override
            public void onViewProfileClicked(User user) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", user.getId());
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_appliedUsersFragment_to_userProfileFragment, bundle);
            }
        });

        projectViewModel = new ViewModelProvider(requireActivity()).get(ProjectViewModel.class);
        projectViewModel.loadAppliedUsersForProject(projectId);

        projectViewModel.getAppliedUsersLiveData().observe(getViewLifecycleOwner(), users -> {
            if (users != null) {
                adapter.setAppliedUsers(users);
            }
        });

        binding.backButton.setOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
