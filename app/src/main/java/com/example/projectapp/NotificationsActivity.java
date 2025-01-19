package com.example.projectapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectapp.databinding.ActivityLoginBinding;
import com.example.projectapp.databinding.ActivityNotificationsBinding;

public class NotificationsActivity extends AppCompatActivity {

    private ActivityNotificationsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}