package com.example.patiapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityIlanDetayBinding;
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

public class IlanDetay extends AppCompatActivity {
    ActivityIlanDetayBinding binding;
    ArrayList<Post> ilanArrayList;
    private FirebaseFirestore firebaseFirestore;




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
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Toast.makeText(IlanDetay.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if(value!=null){

                            for (DocumentSnapshot snapshot:value.getDocuments()){
                                Map<String,Object> data=snapshot.getData();

                                assert data != null;
                                String baslik=(String) data.get("ilanbaslik");
                                String dowloandurl=(String) data.get("dowloandurl");
                                String sehir=(String) data.get("sehir");
                                String ilanturu=(String) data.get("ilanturu");

                                kullaniciemail=(String)  data.get("email");
                                aciklamatext=(String)  data.get("aciklama");
                                telno=(String)  data.get("telno");
                                ilce=(String)  data.get("ilce");





                              /*  Post ilan=new Post(baslik,dowloandurl,sehir,ilanturu,date);
                                ilanArrayList.add(ilan);*/
                            }
                        }
                        else {
                            System.out.println( error.getLocalizedMessage());
                        }
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



    }

}