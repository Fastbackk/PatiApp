package com.example.patiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.patiapp.databinding.ActivityBarinakKullaniciDetayBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class BarinakKullaniciDetay extends AppCompatActivity {
    private ActivityBarinakKullaniciDetayBinding binding;
    private String acikadres, biyografi, eposta, ilce, kurulusyili, kurumisim, profil_foto, sehir, telno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarinakKullaniciDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent=getIntent();

        acikadres = intent.getStringExtra("acikadres");

        biyografi = intent.getStringExtra("biyografi");

        ilce = intent.getStringExtra("ilce");

        kurulusyili = intent.getStringExtra("kurulusyili");

        kurumisim = intent.getStringExtra("kurumisim");

        biyografi = intent.getStringExtra("biyografi");

        profil_foto = intent.getStringExtra("profil_foto");

        sehir = intent.getStringExtra("sehir");

        telno = intent.getStringExtra("telno");


        Picasso.get().load(profil_foto).into(binding.imageView13);
        binding.kurumisim.setText(kurumisim);
        binding.textView20.setText(biyografi);
        binding.sehirilce.setText(sehir+"/"+ilce);
        binding.telefon.setText("Telefon: "+telno);
        binding.adres.setText("Adres: "+acikadres);
        binding.yil.setText(kurulusyili+"'den beri");




    }
}