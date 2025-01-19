package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityUserFavoritedProjectsBinding;
import com.example.projectapp.databinding.ActivityUserPostedProjectsBinding;

public class UserPostedProjectsActivity extends AppCompatActivity {

    private ActivityUserPostedProjectsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserPostedProjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}