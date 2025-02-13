package com.example.projectapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.Repository.UserRepository;
import com.example.projectapp.databinding.FragmentLoginBinding;

import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private UserRepository userRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);  // Hide Bottom Navigation for this fragment
        }

        userRepository = new UserRepository();

        binding.signUpText.setOnClickListener(v -> navigateSignup());
        binding.loginButton.setOnClickListener(v -> handleLogin());

        return binding.getRoot();
    }

    private void navigateSignup() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_loginFragment_to_signupFragment);
    }

    private void handleLogin() {
        // Get the email and password from the input fields
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.emailInput.setError("Email is required");
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordInput.setError("Password is required");
        } else {
            // Call login API
            userRepository.loginUser(email, password, new UserRepository.LoginUserCallback() {
                @Override
                public void onSuccess(String responseBody) {
                    try {
                        // Parse the response to get the token and user data
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String token = jsonResponse.getString("token");
                        JSONObject userObject = jsonResponse.getJSONObject("user");

                        // Save the token in SharedPreferences
                        saveTokenInPreferences(token);

                        // Optionally save user data in SharedPreferences if needed
                        saveUserInPreferences(userObject);

                        // Navigate to the next screen or home screen after successful login
                        navigateToHome();
                    } catch (Exception e) {
                        e.printStackTrace();
                        binding.emailInput.setError("Error processing the response.");
                        binding.passwordInput.setError("Error processing the response.");
                    }
                }

                @Override
                public void onFailure(String error) {
                    // Handle the failure, such as a network error or invalid credentials
                    binding.emailInput.setError("Login failed: " + error);
                    binding.passwordInput.setError("Login failed: " + error);

                    // Optionally, show a toast message for more user-friendly feedback
                    Toast.makeText(requireContext(), "Login failed: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void navigateToHome() {
        // You can use a navigation controller or direct activity change
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_loginFragment_to_homeFragment);
    }

    private void saveTokenInPreferences(String token) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);  // Save token in SharedPreferences
        editor.apply();
    }

    private void saveUserInPreferences(JSONObject userObject) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putInt("userId", userObject.getInt("id"));
            editor.putString("username", userObject.getString("username"));
            editor.putString("email", userObject.getString("email"));
            editor.putString("firstName", userObject.getString("firstName"));
            editor.putString("lastName", userObject.getString("lastName"));
            editor.putString("userStatus", userObject.getString("userStatus"));
            editor.putString("bio", userObject.getString("bio"));
            editor.putString("linkedinURL", userObject.getString("linkedinURL"));
            editor.putString("skills", userObject.getString("skills"));
            editor.putString("createdAt", userObject.getString("created_at"));
            editor.putString("updatedAt", userObject.getString("updated_at"));
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
