package com.example.patiapp;

import androidx.annotation.NonNull;
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

    public String username,foto,foto2;
    String userpp;

    //cardview'de görünmeyen diğer verileri atadığım Stringleri tanımlama
    String kullaniciemail;

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
                                kayitlimi();
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
                                userpp=(String) data.get("userpp");
                                //verileri kullanma
                                Picasso.get().load(dowloandurl).into(binding.profileHeaderImage);
                                Picasso.get().load(userpp).into(binding.profilephoto);
                                binding.ilanbaslik.setText(ilanbaslik);
                                binding.ilanturu.setText(ilanturu);
                                binding.textView20.setText(username);
                                binding.tarih.setText(date);
                                binding.epostatext.setText(kullaniciemail);
                                binding.konum.setText(mamamiktar);
                                binding.aciklama.setText(aciklamatext);
                                binding.telefon.setText(telno);
                                binding.kurulus.setText(hayvankategori);
                                binding.konum2.setText(sehir+" / "+ilce);
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












    }
    public void kayitlimi(){
        firebaseFirestore.collection("Kaydedilenler").whereEqualTo("kaydedilenilanID",ID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        Intent intent=new Intent(MamaBagisiDetay.this, KullaniciDetay.class);
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
                .whereEqualTo("kaydedenkisi", username)
                .whereEqualTo("kaydedilenilanID", ilanID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        // Sorgulama başarılı, sonuçları kontrol et

                        if (task.getResult().isEmpty()) {
                            // Eğer sonuç yoksa, yani daha önce bu veri eklenmemişse yeni veriyi ekle
                            Map<String, Object> kaydedilenler = new HashMap<>();
                            kaydedilenler.put("kaydedenkisi", username);
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