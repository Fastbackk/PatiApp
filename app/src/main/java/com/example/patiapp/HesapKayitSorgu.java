package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HesapKayitSorgu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesap_kayit_sorgu);
    }
    public void barinak(View view){
        Intent intent=new Intent(HesapKayitSorgu.this,BarinakHesapOlustur.class);
        startActivity(intent);
        finish();
    }
    public void kisisel(View view){
        Intent intent=new Intent(HesapKayitSorgu.this,HesapKayit.class);
        startActivity(intent);
        finish();
    }
    public void back(View view){
        Intent intent=new Intent(HesapKayitSorgu.this,HesapGiris.class);
        startActivity(intent);
        finish();

    }
}