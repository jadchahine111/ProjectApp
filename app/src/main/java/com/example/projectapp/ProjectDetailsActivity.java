package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityFilterSearchBinding;
import com.example.projectapp.databinding.ActivityProjectDetailsBinding;

public class ProjectDetailsActivity extends AppCompatActivity {

    private ActivityProjectDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProjectDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}