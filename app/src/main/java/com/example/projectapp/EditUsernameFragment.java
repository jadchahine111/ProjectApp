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
import com.example.projectapp.Model.User;
import com.example.projectapp.ViewModels.UserViewModel;
import com.example.projectapp.databinding.FragmentEditUsernameBinding;

public class EditUsernameFragment extends Fragment {

    private FragmentEditUsernameBinding binding;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUsernameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain shared UserViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        User currentUser = userViewModel.getUserLiveData().getValue();
        if (currentUser != null && !TextUtils.isEmpty(currentUser.getUsername())) {
            binding.editedOwnerUsername.setText(currentUser.getUsername());
        }

        binding.saveButton.setOnClickListener(v -> {
            String newUsername = binding.editedOwnerUsername.getText().toString().trim();
            if (TextUtils.isEmpty(newUsername)) {
                Toast.makeText(requireContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentUser != null) {
                currentUser.setUsername(newUsername);
                userViewModel.updateUser(currentUser);
            }
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.profileFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
