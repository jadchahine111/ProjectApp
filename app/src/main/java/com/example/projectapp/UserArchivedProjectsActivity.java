package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityUserAppliedProjectsBinding;
import com.example.projectapp.databinding.ActivityUserArchivedProjectsBinding;

public class UserArchivedProjectsActivity extends AppCompatActivity {

    private ActivityUserArchivedProjectsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserArchivedProjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}