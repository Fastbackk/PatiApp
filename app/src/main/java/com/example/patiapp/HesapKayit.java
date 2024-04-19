package com.example.patiapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityHesapKayitBinding;
import com.example.patiapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HesapKayit extends AppCompatActivity {
    private ActivityHesapKayitBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHesapKayitBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        auth=FirebaseAuth.getInstance();
    }

    public void kayit(View view){
        String ad=binding.editTextText2.getText().toString();
        String soyad=binding.editTextText3.getText().toString();
        String eposta=binding.editTextTextEmailAddress.getText().toString();
        String sifre=binding.editTextTextPassword2.getText().toString();


        if (ad.equals("") || eposta.equals("")|| soyad.equals("")|| sifre.equals("")) {
            Toast.makeText(this, "Boş Alan bırakmayınız", Toast.LENGTH_SHORT).show();
        }
        else{

            auth.createUserWithEmailAndPassword(eposta, sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(HesapKayit.this, HesapGiris.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HesapKayit.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}