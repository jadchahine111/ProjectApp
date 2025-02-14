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
import com.example.projectapp.databinding.FragmentEditFirstNameBinding;

public class EditFirstNameFragment extends Fragment {

    private FragmentEditFirstNameBinding binding;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditFirstNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain UserViewModel from the activity (shared instance)
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Prepopulate the EditText with the current first name if available
        User currentUser = userViewModel.getUserLiveData().getValue();
        if (currentUser != null && !TextUtils.isEmpty(currentUser.getFirstName())) {
            binding.editedOwnerFirstname.setText(currentUser.getFirstName());
        }

        // Set up Save button listener
        binding.saveButton.setOnClickListener(v -> {
            String newFirstName = binding.editedOwnerFirstname.getText().toString().trim();
            if (TextUtils.isEmpty(newFirstName)) {
                Toast.makeText(requireContext(), "First name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentUser != null) {
                currentUser.setFirstName(newFirstName);
                userViewModel.updateUser(currentUser);
            }
            // Navigate back to ProfileFragment
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
