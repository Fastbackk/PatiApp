package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.patiapp.databinding.ActivityBarinakKullaniciDetayBinding;
import com.example.patiapp.databinding.ActivityHesapKayitBinding;

public class BarinakKullaniciDetay extends AppCompatActivity {
    private ActivityBarinakKullaniciDetayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarinakKullaniciDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}