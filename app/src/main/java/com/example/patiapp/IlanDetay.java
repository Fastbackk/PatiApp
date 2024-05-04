package com.example.patiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityIlanDetayBinding;
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

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IlanDetay extends AppCompatActivity {
    ActivityIlanDetayBinding binding;
    ArrayList<Post> ilanArrayList;
    private FirebaseFirestore firebaseFirestore;
    String ID;


public String username;

    //cardview'de görünmeyen diğer verileri atadığım Stringleri tanımlama
    String kullaniciemail;
    String aciklamatext;
    String telno;
    String ilce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_detay);
        binding = ActivityIlanDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();

        //verileri alma
        Intent intent=getIntent();
        String ilanbaslik= intent.getStringExtra("ilanbaslik");
        String ilanturu= intent.getStringExtra("ilanturu");
        String sehir= intent.getStringExtra("sehir");
        String date= intent.getStringExtra("date");
        //link olarak alıyor resmi
        String dowloandurl= intent.getStringExtra("dowloandurl");



/*
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = dateFormat.parse(date);
        Timestamp timestamp = new Timestamp(parsedDate.getTime());*/

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
                                String kullaniciemail = (String) data.get("email");
                                String aciklamatext = (String) data.get("aciklama");
                                String telno = (String) data.get("telno");
                                String ilce = (String) data.get("ilce");
                                String hayvankategori=(String) data.get("hayvankategori");
                                String hayvancinsi=(String) data.get("hayvancinsi");
                                username=(String) data.get("kullaniciadi");

                                // Verileri kullanarak UI güncelleyin
                                Picasso.get().load(dowloandurl).into(binding.imageView6);
                                binding.textView7.setText(baslik);
                                binding.textView8.setText(ilanturu);
                                binding.textView5.setText(date);  // Bu 'date' değerini de ayrıca yukarıdan almanız gerekecek
                                binding.textView12ass.setText(kullaniciemail);
                                binding.textView13.setText(aciklamatext);
                                binding.textView12ss.setText(telno);
                                binding.textView10.setText(hayvankategori);
                                binding.textView11.setText(hayvancinsi);
                                binding.textView4.setText(sehir + " / " + ilce);

                                // Diğer işlemler
                                System.out.println("Veriler başarıyla yüklendi: " + kullaniciemail);
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




        //verileri kullanma
        Picasso.get().load(dowloandurl).into(binding.imageView6);
        binding.textView7.setText(ilanbaslik);
        binding.textView8.setText(ilanturu);
        binding.textView5.setText(date);
        binding.textView12ass.setText(kullaniciemail);
        binding.textView13.setText(aciklamatext);
        binding.textView12ss.setText(telno);
        binding.textView4.setText(sehir+" / "+ilce);
        System.out.println("işte burada "+kullaniciemail);

        binding.mesajgonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IlanDetay.this, MesajEkle.class);
                // Verileri intent ile MesajEkle aktivitesine gönder
                intent.putExtra("gidenveri", username);
                startActivity(intent);

            }
        });







    }
    public void kaydet(View view){
        Map<String, Object> Kaydedilenler = new HashMap<>();
        Kaydedilenler.put("kaydedenkisi",username );
        Kaydedilenler.put("kaydedilenilanID", ID);

        firebaseFirestore.collection("Kaydedilenler").add(Kaydedilenler)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("eklendi");
                })
                .addOnFailureListener(e -> {
                    System.out.println("eklenemedi");
                });






    }



}