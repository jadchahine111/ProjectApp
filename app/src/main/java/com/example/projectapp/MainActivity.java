package com.example.projectapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.projectapp.Retrofit.ApiClient;
import com.example.projectapp.Retrofit.ApiInterface;
import com.example.projectapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private View relativeLayout; // Add this line to reference the RelativeLayout

    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the Retrofit instance and ApiInterface
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        // Check if the user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);



        // Retrieve NavHostFragment using binding
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Ensure the initial fragment is loaded via NavController
            if (savedInstanceState == null) {
                // Navigate to MainAuthFragment initially
                navController.navigate(R.id.welcomeFragment);
            }
        }

        // If token is available, navigate to HomeFragment
        if (token != null) {
            navController.navigate(R.id.homeFragment);  // Replace with your HomeFragment's ID
        } else {
            navController.navigate(R.id.loginFragment);  // Replace with your LoginFragment's ID
        }

        // Set up Bottom Navigation View
        bottomNavigationView = binding.bottomNavigationView;

        // Set up navigation with the Bottom Navigation View
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Handle Bottom Navigation Item Selection
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        binding.addButton.setOnClickListener(v -> {
            // Navigate to AddProjectFragment
            navController.navigate(R.id.addProjectFragment); // Replace with the correct fragment ID
        });
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        // Handle fragment navigation based on bottom navigation item selection
        if (item.getItemId() == R.id.nav_home) {
            navController.navigate(R.id.homeFragment);
        } else if (item.getItemId() == R.id.nav_search) {
            navController.navigate(R.id.searchFragment);
        } else if (item.getItemId() == R.id.nav_my_profile) {
            navController.navigate(R.id.profileFragment);
        } else if (item.getItemId() == R.id.nav_my_section) {
            navController.navigate(R.id.myProjectsFragment);
        } else {
            return false;
        }
        return true;
    }

    // Method to control visibility of the Bottom Navigation View and the entire RelativeLayout
    public void setBottomNavigationVisibility(boolean isVisible) {
        if (isVisible) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            binding.addButton.setVisibility(View.VISIBLE);// Show the RelativeLayout
        } else {
            bottomNavigationView.setVisibility(View.GONE);
            binding.addButton.setVisibility(View.GONE); // Hide the entire RelativeLayout
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle the up navigation
        if (navController != null) {
            return navController.navigateUp() || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }
}
