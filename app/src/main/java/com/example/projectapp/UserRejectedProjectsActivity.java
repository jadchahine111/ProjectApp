package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityUserProfileBinding;
import com.example.projectapp.databinding.ActivityUserRejectedProjectsBinding;

public class UserRejectedProjectsActivity extends AppCompatActivity {

    private ActivityUserRejectedProjectsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserRejectedProjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}