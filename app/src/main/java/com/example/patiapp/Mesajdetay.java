package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.patiapp.databinding.ActivityMesajdetayBinding;

public class Mesajdetay extends AppCompatActivity {
    private ActivityMesajdetayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajdetay);
    }
}