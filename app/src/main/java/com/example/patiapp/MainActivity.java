package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.patiapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        replaceFragment(new AramaFragment());
        binding.bottomNavigationView.setBackground(null);

        //Hata var
       /* binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case (R.id.fav):
                    replaceFragment(new FavFragment());
                    break;

                case (R.id.paw):
                    replaceFragment(new Ilanlar());
                    break;

                case (R.id.search):
                    replaceFragment(new AramaFragment());
                    break;

                case (R.id.message):
                    replaceFragment(new MessagesFragment());
                    break;
            }
            return true;
        });*/





    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}