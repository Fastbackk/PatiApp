package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.patiapp.databinding.ActivityHesapDetayBinding;
import com.example.patiapp.databinding.ActivitySifreDegisBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SifreDegis extends AppCompatActivity {
    private ActivitySifreDegisBinding binding;
    private String ad, soyad, email, kullaniciadi, sifre;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySifreDegisBinding.inflate(getLayoutInflater());
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
      //  binding.editTextText2.setText(ad);
      //  binding.editTextText4.setText(kullaniciadi);
     //   binding.editTextText3.setText(soyad);
     //   binding.editTextTextEmailAddress.setText(email);
        //binding.editTextTextPassword2.setText(sifre);


        // "Kayıt Ol" düğmesine OnClickListener ekleyin


/*/
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


        binding.kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = binding.editTextPassword.getText().toString();

                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SifreDegis.this, "Şifre güncellendi.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}
