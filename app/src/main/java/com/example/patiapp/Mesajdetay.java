package com.example.patiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityMesajdetayBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Mesajdetay extends AppCompatActivity {
    ActivityMesajdetayBinding binding;
    ArrayList<Post> ilanArrayList;
    private FirebaseFirestore firebaseFirestore;
    String ID;




    public String mesajbaslik, mesaj, username, gonderenemail, alici;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_detay);
        binding = ActivityMesajdetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();

        //verileri alma
        Intent intent=getIntent();
        username= intent.getStringExtra("username");
        mesaj= intent.getStringExtra("mesaj");
        mesajbaslik= intent.getStringExtra("mesajbaslik");
        alici= intent.getStringExtra("alici");
        gonderenemail = intent.getStringExtra("gonderenemail");


/*
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = dateFormat.parse(date);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());*/

        firebaseFirestore.collection("Messages").whereEqualTo("mesajbaslik", mesajbaslik)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                ID= snapshot.getId();
                                String username = (String) data.get("username");
                                String mesajbaslik = (String) data.get("mesajbaslik");
                                String mesaj = (String) data.get("mesaj");
                                String gonderenemail = (String) data.get("gonderenemail");
                                String alici = (String) data.get("alici");


                                // Verileri kullanarak UI ggonderenemailüncelleyin

                                binding.username.setText(username);
                                binding.mesajbaslik.setText(mesajbaslik);
                                binding.mesaj.setText(mesaj);
                                binding.gonderenemail.setText(gonderenemail);
                                binding.alici.setText(alici);
                                //binding.tarih.setText(date);


                                // Diğer işlemler
                                System.out.println("Veriler başarıyla yüklendi: " + username);
                            } else {
                                Toast.makeText(Mesajdetay.this, "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Mesajdetay.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




        //verileri kullanma

        binding.username.setText(username);
        binding.mesajbaslik.setText(mesajbaslik);
        binding.mesaj.setText(mesaj);
        binding.gonderenemail.setText(gonderenemail);
        binding.alici.setText(alici);
        //binding.tarih.setText(date);
        System.out.println("işte burada "+username);
        System.out.println("işte burada "+mesajbaslik);
        System.out.println("işte burada "+mesaj);
        System.out.println("işte burada "+gonderenemail);
        System.out.println("işte burada "+alici);



        binding.kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(ID);

            }
        });
        binding.kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent'i oluştur ve MesajEkle aktivitesine git
                Intent intent = new Intent(Mesajdetay.this, MesajEkle.class);
                // Verileri intent ile MesajEkle aktivitesine gönder
                intent.putExtra("gidenveri", username);
                startActivity(intent);
            }
        });
    }
}