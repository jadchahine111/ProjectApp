package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityEditFirstNameBinding;
import com.example.projectapp.databinding.ActivityEditLastNameBinding;

public class EditLastNameActivity extends AppCompatActivity {

    private ActivityEditLastNameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditLastNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}