package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.patiapp.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        replaceFragment(new Ilanlar());
        binding.bottomNavigationView.setBackground(null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new Ilanlar())
                .commit();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            //mesaj
            if (itemId == R.id.fav) {
                replaceFragment(new FavFragment());
            }
            //ilanlar
            else if (itemId == R.id.paw) {
                replaceFragment(new Ilanlar());
            }
            //arama
            else if (itemId == R.id.search) {
                replaceFragment(new AramaFragment());

            }
            //hesabım
            else if (itemId == R.id.message) {
                replaceFragment(new MessagesFragment());

            }
            //ilan yükle
            else if (itemId == R.id.add) {
                Intent intentt = new Intent(MainActivity.this, IlanYukleme.class);
                startActivity(intentt);
                finish();
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}