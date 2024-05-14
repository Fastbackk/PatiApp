package com.example.patiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityIlanDetayBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IlanDetay extends AppCompatActivity {
    ActivityIlanDetayBinding binding;
    private FirebaseFirestore firebaseFirestore;
    String ID;
    String kullaniciadii;

    public String username,foto,foto2;

    //cardview'de görünmeyen diğer verileri atadığım Stringleri tanımlama
    String kullaniciemail;


    private FirebaseAuth mAuth;
    String hesapturu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_detay);
        binding = ActivityIlanDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance'ını başlatma
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        String ilanbaslik= intent.getStringExtra("ilanbaslik");
        String ilanturu= intent.getStringExtra("ilanturu");
        String sehir= intent.getStringExtra("sehir");
        String date= intent.getStringExtra("date");
        //link olarak alıyor resmi
        String dowloandurl= intent.getStringExtra("dowloandurl");
        hesapturu = intent.getStringExtra("hesapturu");

       kullaniciemail= mAuth.getCurrentUser().getEmail();
       firebaseFirestore.collection("Barinak").whereEqualTo("eposta",kullaniciemail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                   if (snapshot.exists()) {
                       Map<String, Object> data = snapshot.getData();
                       kullaniciadii = (String) data.get("kurumisim");
                       kayitlimi();
                   }
               }
           }
       });
        firebaseFirestore.collection("users").whereEqualTo("eposta",kullaniciemail).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    if (snapshot.exists()) {
                        Map<String, Object> data = snapshot.getData();
                        kullaniciadii = (String) data.get("kullaniciadi");
                        kayitlimi();
                    }
                }
            }
        });











        //Tıklanın ilanın tüm bilgilerine erişiliyor.
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
                                String userpp = (String) data.get("userpp");
                                String sehir = (String) data.get("sehir");
                                String ilanturu = (String) data.get("ilanturu");
                                kullaniciemail = (String) data.get("eposta");
                                String aciklamatext = (String) data.get("aciklama");
                                String telno = (String) data.get("telno");
                                String ilce = (String) data.get("ilce");
                                String cinsiyet = (String) data.get("cinsiyet");
                                String yas = (String) data.get("yas");
                                String hayvankategori=(String) data.get("hayvankategori");
                                String hayvancinsi=(String) data.get("hayvancinsi");
                                String saglik=(String) data.get("saglik");
                                username=(String) data.get("kullaniciadi");
                                foto=(String) data.get("profil_foto");

                                // Verileri kullanarak UI güncelleyin
                                Picasso.get().load(dowloandurl).into(binding.profileHeaderImage);
                                Picasso.get().load(userpp).into(binding.profilephoto);
                                binding.textView20.setText(username);
                                binding.ilanbaslik.setText(baslik);
                                binding.ilanturu.setText(ilanturu);
                                binding.tarih.setText(date);  // Bu 'date' değerini de ayrıca yukarıdan almanız gerekecek
                                binding.epostatext.setText(kullaniciemail);
                                binding.aciklama.setText(aciklamatext);
                                binding.telefon.setText(telno);
                                binding.saglikdurumu.setText(saglik);
                                binding.yas.setText(yas);
                                binding.cinsiyet.setText(cinsiyet);
                                binding.kurulus.setText(hayvankategori);
                                binding.konum.setText(hayvancinsi);
                                binding.konum2.setText(sehir + " / " + ilce);

                            } else {
                                Toast.makeText(IlanDetay.this, "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IlanDetay.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    public void kayitlimi(){
        firebaseFirestore.collection("Kaydedilenler").whereEqualTo("kaydedenkisi",kullaniciadii).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    if (documentSnapshot.exists()) {

                        Drawable background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.maaaaaaaaaaaaaaaaaaa);
                        binding.kaydetbutton.setBackground(background);

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void profilegit(View view){
        if (hesapturu.equals("kisisel")){
            Intent intent=new Intent(IlanDetay.this, KullaniciDetay.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(IlanDetay.this, BarinakKullaniciDetay.class);
            intent.putExtra("eposta",kullaniciemail);
            startActivity(intent);
        }

    }
    public void mesajgonder(View view){
        Intent intent = new Intent(IlanDetay.this, MesajEkle.class);
        // Verileri intent ile MesajEkle aktivitesine gönder

        intent.putExtra("gidenveri", username);
        startActivity(intent);
    }
    public void kaydet(View view) {
        if (kullaniciadii==null){
            System.out.println("NULL LA VERİ");
        }
        else{
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
                                            Drawable background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.maaaaaaaaaaaaaaaaaaa);
                                            binding.kaydetbutton.setBackground(background);
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
                                            Drawable background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.save);
                                            binding.kaydetbutton.setBackground(background);
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



}