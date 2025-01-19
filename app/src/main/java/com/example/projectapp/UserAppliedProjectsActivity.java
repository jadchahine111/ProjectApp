package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityUserAcceptedProjectsBinding;
import com.example.projectapp.databinding.ActivityUserAppliedProjectsBinding;

public class UserAppliedProjectsActivity extends AppCompatActivity {

    private ActivityUserAppliedProjectsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserAppliedProjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}