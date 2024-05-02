package com.example.patiapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.patiapp.databinding.ActivityHesapDetayBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;

public class HesapDetay extends AppCompatActivity {
    private ActivityHesapDetayBinding binding;
    private String ad, soyad, email, kullaniciadi, sifre;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHesapDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Firebase Authentication ve Firestore'u başlatın
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        Intent intent = getIntent();
        ad = intent.getStringExtra("ad");
        soyad = intent.getStringExtra("soyad");
        kullaniciadi = intent.getStringExtra("kullaniciadi");
        email = intent.getStringExtra("email");
        sifre = intent.getStringExtra("sifre");

        // EditText alanlarına başlangıç değerlerini ayarlayın
        binding.editTextText2.setText(ad);
        binding.editTextText4.setText(kullaniciadi);
        binding.editTextText3.setText(soyad);
        binding.editTextTextEmailAddress.setText(email);
        //binding.editTextTextPassword2.setText(sifre);


        // "Kayıt Ol" düğmesine OnClickListener ekleyin
        binding.kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText alanlarından güncellenmiş verileri alın
                String updatedAd = binding.editTextText2.getText().toString();
                String updatedSoyad = binding.editTextText3.getText().toString();
                String updatedKullaniciadi = binding.editTextText4.getText().toString();
                String updatedEmail = binding.editTextTextEmailAddress.getText().toString();
                //String updateSifre = binding.editTextTextPassword2.getText().toString();

                // Firestore'da kullanıcı verilerini güncelleyin
                DocumentReference userRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
                Map<String, Object> updatedUserData = new HashMap<>();
                updatedUserData.put("ad", updatedAd);
                updatedUserData.put("soyad", updatedSoyad);
                updatedUserData.put("kullaniciadi", updatedKullaniciadi);
                updatedUserData.put("eposta", updatedEmail);
                //updatedUserData.put("sifre", updateSifre);

                userRef.set(updatedUserData, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(HesapDetay.this, "Kullanıcı verileri başarıyla güncellendi!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(HesapDetay.this, "Kullanıcı verileri güncellenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


//Authcention değiştirme
                String newEmail = binding.editTextTextEmailAddress.getText().toString();
                //String newPassword = binding.editTextTextPassword2.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    user.updateEmail(newEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(HesapDetay.this, "E-Posta güncellendi.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
/*/
                // Şifre güncelleme işlemi
                if (!newPassword.isEmpty()) {
                    currentUser.updatePassword(newPassword)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(HesapDetay.this, "Şifre güncellendi.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(HesapDetay.this, "Şifre güncelleme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

 */
            }
        });
    }
}