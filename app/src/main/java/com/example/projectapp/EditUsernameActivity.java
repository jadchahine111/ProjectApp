package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityEditLastNameBinding;
import com.example.projectapp.databinding.ActivityEditUsernameBinding;

public class EditUsernameActivity extends AppCompatActivity {

    private ActivityEditUsernameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditUsernameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}