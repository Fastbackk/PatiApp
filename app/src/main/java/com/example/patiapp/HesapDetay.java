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

import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;

public class HesapDetay extends AppCompatActivity {
    private ActivityHesapDetayBinding binding;
    private FirebaseAuth mAuth;
    private String ad, soyad, email, kullaniciadi, sifre;

    private FirebaseFirestore db;

    private FirebaseUser currentUser;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;




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
        mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance'ını başlatma
        firebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth nesnesini oluştur
        firebaseFirestore = FirebaseFirestore.getInstance();

        email = firebaseAuth.getCurrentUser().getEmail(); // Kullanıcı e-postasını al
        firebaseFirestore.collection("users").whereEqualTo("eposta", email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                kullaniciadi = (String) data.get("kullaniciadi");
                                ad = (String) data.get("ad");
                                soyad = (String) data.get("soyad");
                                email = (String) data.get("email");
                                sifre = (String) data.get("sifre");
                                binding.editTextText2.setText(ad);
                                binding.editTextText4.setText(kullaniciadi);
                                binding.editTextText3.setText(soyad);


                                Toast.makeText(HesapDetay.this, email, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HesapDetay.this, "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        binding.editTextTextEmailAddress.setText(email);
        binding.editTextTextEmailAddress.setEnabled(false);
        binding.editTextText4.setEnabled(false);



        // EditText alanlarına başlangıç değerlerini ayarlayın


        //binding.editTextTextPassword2.setText(sifre);


        // "Kayıt Ol" düğmesine OnClickListener ekleyin



        binding.kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText alanlarından güncellenmiş verileri alın
                String updatedAd = binding.editTextText2.getText().toString();
                String updatedSoyad = binding.editTextText3.getText().toString();



                // Firestore'da kullanıcı verilerini güncelleyin
                DocumentReference userRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
                Map<String, Object> updatedUserData = new HashMap<>();
                updatedUserData.put("ad", updatedAd);
                updatedUserData.put("soyad", updatedSoyad);

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
            }
        });
    }
    public void onClick(View v) {
        Intent intent = new Intent(HesapDetay.this, SifreDegis.class);
        startActivity(intent);
    }
}