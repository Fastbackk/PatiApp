package com.example.patiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

    }

    public void button(View view) {
        String eposta = binding.editTextTextEmailAddress2.getText().toString();
        String isim = binding.editTextText.getText().toString();
        String soyisim = binding.editTextText2.getText().toString();
        String sifre = binding.editTextTextPassword.getText().toString();
        String sifretkr = binding.editTextTextPassword2.getText().toString();

        String telefonNumarasiStr = binding.editTextPhone.getText().toString();
        String gunStr = binding.editTextNumber2.getText().toString();
        String ayStr = binding.editTextNumber3.getText().toString();
        String yilStr = binding.editTextNumber4.getText().toString();

        if (eposta.isEmpty() || sifre.isEmpty() || isim.isEmpty() || soyisim.isEmpty() || telefonNumarasiStr.isEmpty()
                || gunStr.isEmpty() || ayStr.isEmpty() || yilStr.isEmpty()) {
            Toast.makeText(this, "Boş Alan Bırakmayınız!", Toast.LENGTH_SHORT).show();
        } else{
            int telefonNumarasi = 0;
            int gun = 0;
            int ay = 0;
            int yil = 0;
            try {
                telefonNumarasi = Integer.parseInt(telefonNumarasiStr);
                gun = Integer.parseInt(gunStr);
                ay = Integer.parseInt(ayStr);
                yil = Integer.parseInt(yilStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Geçersiz Sayısal Değer!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!sifretkr.equals(sifre)) {
                Toast.makeText(this, "Şifreler aynı değil!", Toast.LENGTH_SHORT).show();
            }
            else{
                auth.createUserWithEmailAndPassword(eposta, sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }




        }
    }


}