package com.example.patiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityIlanDetayBinding;
import com.example.patiapp.databinding.ActivityMamaBagisiDetayBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MamaBagisiDetay extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    String ID;
    String kullaniciadii;

    public String username,foto,foto2;

    //cardview'de görünmeyen diğer verileri atadığım Stringleri tanımlama
    String kullaniciemail;
    int kayitkackere;
    String aciklamatext;
    String telno;
    String ilce;
    private FirebaseAuth mAuth;
    private ActivityMamaBagisiDetayBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMamaBagisiDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        //verileri alma
        Intent intent=getIntent();
        String ilanbaslik= intent.getStringExtra("ilanbaslik");
        String ilanturu= intent.getStringExtra("ilanturu");
        String sehir= intent.getStringExtra("sehir");
        String date= intent.getStringExtra("date");
        //link olarak alıyor resmi
        String dowloandurl= intent.getStringExtra("dowloandurl");
        String hesapturu = intent.getStringExtra("hesapturu");


        firebaseFirestore.collection("Ilanlar").whereEqualTo("ilanbaslik", ilanbaslik)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                ID= snapshot.getId();
                                System.out.println(ID);
                                String baslik = (String) data.get("ilanbaslik");
                                String dowloandurl = (String) data.get("dowloandurl");
                                String sehir = (String) data.get("sehir");
                                String ilanturu = (String) data.get("ilanturu");
                                kullaniciemail = (String) data.get("eposta");
                                String aciklamatext = (String) data.get("aciklama");
                                String telno = (String) data.get("telno");
                                String ilce = (String) data.get("ilce");
                                String mamamiktar = (String) data.get("mamamiktar");
                                String hayvankategori=(String) data.get("hayvankategori");
                                username=(String) data.get("kullaniciadi");
                                foto=(String) data.get("profil_foto");
                                //verileri kullanma
                                Picasso.get().load(dowloandurl).into(binding.imageView6);

                                binding.textView7.setText(ilanbaslik);
                                binding.textView8.setText(ilanturu);
                                binding.textView8.setBackgroundColor(Color.parseColor("#38b832"));
                                binding.textView12.setText(username);
                                binding.textView5.setText(date);
                                binding.textView12ass.setText(kullaniciemail);
                                binding.textView121.setText(mamamiktar);
                                binding.textView13.setText(aciklamatext);
                                binding.textView12ss.setText(telno);
                                binding.textView10.setText(hayvankategori);
                                binding.textView4.setText(sehir+" / "+ilce);

                                Toast.makeText(MamaBagisiDetay.this, kullaniciemail, Toast.LENGTH_SHORT).show();
                                firebaseFirestore.collection("users").whereEqualTo("eposta", kullaniciemail)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                                    if (documentSnapshot.exists()) {
                                                        Map<String, Object> data = documentSnapshot.getData();
                                                        foto2 = (String) data.get("profil_foto");
                                                        Picasso.get().load(foto2).into(binding.imageView12);
                                                        //kullaniciadii = (String) data.get("kullaniciadi");


                                                    }

                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println("Veriler yüklenemedi!");

                                            }
                                        });


                                // Diğer işlemler
                                System.out.println("Veriler başarıyla yüklendi: " + kullaniciemail);
                            } else {
                                Toast.makeText(MamaBagisiDetay.this, "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MamaBagisiDetay.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



        FirebaseUser user = mAuth.getCurrentUser();
        String useremail = user.getEmail();
        System.out.println(useremail);

        firebaseFirestore.collection("users").whereEqualTo("eposta", useremail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> data = documentSnapshot.getData();
                                //foto2 = (String) data.get("profil_foto");
                                //Picasso.get().load(foto2).into(binding.imageView12);
                                kullaniciadii = (String) data.get("kullaniciadi");


                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Veriler yüklenemedi!");

                    }
                });


        new CountDownTimer(2000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                binding.textView12.setText(username);
            }
        }.start();




    }
    public void profilegit(View view){
        Intent intent=new Intent(this, KullaniciDetay.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void mesajgonder(View view){
        Intent intent = new Intent(MamaBagisiDetay.this, MesajEkle.class);
        // Verileri intent ile MesajEkle aktivitesine gönder

        intent.putExtra("gidenveri", username);
        startActivity(intent);
    }
    public void kaydet(View view) {
        String kaydedilmisID;// kaydedilenler tablomdaki document'in ID'si

        String ilanID = ID; // İlan ID'sini al

        // Firestore'da "Kaydedilenler" koleksiyonunda "kaydedenkisi" ve "kaydedilenilanID" alanlarını sorgula
        firebaseFirestore.collection("Kaydedilenler")
                .whereEqualTo("kaydedenkisi", kullaniciadii)
                .whereEqualTo("kaydedilenilanID", ilanID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        // Sorgulama başarılı, sonuçları kontrol et

                        if (task.getResult().isEmpty()) {
                            // Eğer sonuç yoksa, yani daha önce bu veri eklenmemişse yeni veriyi ekle
                            Map<String, Object> kaydedilenler = new HashMap<>();
                            kaydedilenler.put("kaydedenkisi", kullaniciadii);
                            kaydedilenler.put("kaydedilenilanID", ilanID);

                            firebaseFirestore.collection("Kaydedilenler").add(kaydedilenler)
                                    .addOnSuccessListener(documentReference -> {
                                        System.out.println("Eklendi");
                                    })
                                    .addOnFailureListener(e -> {
                                        System.out.println("Eklenemedi: " + e.getMessage());
                                    });
                        } else {
                            // Eğer sonuç varsa, yani daha önce bu veri eklenmişse belgeyi sil
                            DocumentSnapshot document = task.getResult().getDocuments().get(0); // İlk belgeyi al
                            firebaseFirestore.collection("Kaydedilenler").document(document.getId()).delete()
                                    .addOnSuccessListener(aVoid -> {
                                        System.out.println("Veri silindi.");
                                    })
                                    .addOnFailureListener(e -> {
                                        System.out.println("Silinemedi: " + e.getMessage());
                                    });

                        }
                    } else {
                        // Sorgulama başarısız oldu
                        System.out.println("Sorgulama başarısız: " + task.getException().getMessage());
                    }
                });
    }
}