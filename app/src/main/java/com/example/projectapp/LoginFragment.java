package com.example.projectapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.projectapp.MyApplication.MyApplication;
import com.example.projectapp.Repository.UserRepository;
import com.example.projectapp.databinding.FragmentLoginBinding;

import org.json.JSONException;
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

        userRepository = new UserRepository(MyApplication.getAppContext());

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
                    Log.e("LoginError", "Raw error response: " + error); // Debugging log

                    try {
                        // Remove "Error: " prefix if present
                        if (error.startsWith("Error: ")) {
                            error = error.substring(7); // Remove the first 7 characters
                        }

                        // Parse the error response as JSON
                        JSONObject jsonError = new JSONObject(error);
                        String errorMessage = jsonError.optString("error", "An unexpected error occurred.");

                        Log.e("LoginError", "Extracted error message: " + errorMessage); // Debugging log

                        // Customize messages for better UI
                        switch (errorMessage) {
                            case "Invalid credentials":
                                errorMessage = "Incorrect email or password. Please try again.";
                                break;
                            case "Your registration is pending admin approval.":
                                errorMessage = "Your account is awaiting admin approval.";
                                break;
                            case "Email is not verified. A verification email has been sent to your email address.":
                                errorMessage = "Please verify your email. A verification link has been sent.";
                                break;
                        }

                        // Update UI with error message
                        binding.emailInput.setError(errorMessage);
                        binding.passwordInput.setError(null);
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e("LoginError", "JSON Parsing failed: " + e.getMessage()); // Debugging log

                        // Fallback error message
                        String fallbackMessage = "An unexpected error occurred. Please try again.";
                        binding.emailInput.setError(fallbackMessage);
                        binding.passwordInput.setError(null);
                        Toast.makeText(requireContext(), fallbackMessage, Toast.LENGTH_SHORT).show();
                    }
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
