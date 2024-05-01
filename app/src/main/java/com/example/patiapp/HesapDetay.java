package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.patiapp.databinding.ActivityHesapDetayBinding;

public class HesapDetay extends AppCompatActivity {
    private ActivityHesapDetayBinding binding;
    String ad;
    String soyad;
    String email;
    String kullaniciadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHesapDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent=getIntent();
        ad=intent.getStringExtra("ad");
        soyad=intent.getStringExtra("soyad");
        kullaniciadi=intent.getStringExtra("kullaniciadi");
        email=intent.getStringExtra("email");

        binding.editTextText2.setText(ad);
        binding.editTextText4.setText(kullaniciadi);
        binding.editTextText3.setText(soyad);
        binding.editTextTextEmailAddress.setText(email);








    }
}