package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void secenek1(View view){

        Intent intent=new Intent(MainActivity2.this, MainActivity.class);
        intent.putExtra("ilan","ilanhayvan");
        startActivity(intent);
        finish();


    }
    public void secenek2(View view){

        Intent intent=new Intent(MainActivity2.this, MainActivity.class);
        intent.putExtra("ilan","ilanbakici");
        startActivity(intent);
        finish();


    }
    public void secenek3(View view){

        Intent intent=new Intent(MainActivity2.this, MainActivity.class);
        intent.putExtra("ilan","ilanmama");
        startActivity(intent);
        finish();


    }

}