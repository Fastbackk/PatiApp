package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.patiapp.databinding.ActivityIlanlarKendiBinding;
import com.google.firebase.auth.FirebaseAuth;

public class IlanlarKendi extends AppCompatActivity {
    private ActivityIlanlarKendiBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIlanlarKendiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        mAuth = FirebaseAuth.getInstance();
        String UserEmail=mAuth.getCurrentUser().getEmail();




    }
}