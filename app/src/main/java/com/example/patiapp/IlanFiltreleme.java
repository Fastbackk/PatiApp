package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityIlanFiltrelemeBinding;
import com.example.patiapp.databinding.ActivityMainBinding;
import com.example.patiapp.databinding.FragmentIlanlarBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class IlanFiltreleme extends AppCompatActivity {
    private ActivityIlanFiltrelemeBinding binding;
    ArrayList<Post> ilanArrayList;
    Adapter adapter;

    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIlanFiltrelemeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        ilanArrayList=new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String ilanturu = intent.getStringExtra("ilanturu");
        String hayvankategori = intent.getStringExtra("hayvankategori");
        String sehir = intent.getStringExtra("sehir");
        String ilce = intent.getStringExtra("ilce");





        //getData();


    }
}