package com.example.patiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityBarinakKullaniciDetayBinding;
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
import java.util.Locale;
import java.util.Map;


public class BarinakKullaniciDetay extends AppCompatActivity {
    private ActivityBarinakKullaniciDetayBinding binding;
    private String eposta;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> ilanArrayList;
    AdapterYedek adapter;
    String username;
    public String date;
    String kurumisimi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarinakKullaniciDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ilanArrayList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(BarinakKullaniciDetay.this));
        adapter = new AdapterYedek(ilanArrayList);
        binding.recyclerView.setAdapter(adapter);
        Intent intent = getIntent();

        eposta = intent.getStringExtra("eposta");

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Barinak").whereEqualTo("eposta", eposta).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> data = documentSnapshot.getData();
                                kurumisimi = (String) data.get("kurumisim");
                                getData();
                                String kurulusyili = (String) data.get("kurulusyili");
                                String acikadres = (String) data.get("acikadres");
                                String biyografi = (String) data.get("biyografi");

                                String telno = (String) data.get("telno");
                                String sehir = (String) data.get("sehir");
                                String ilce = (String) data.get("ilce");
                                String profil_foto = (String) data.get("profil_foto");


                                // Verileri kullanarak UI güncelleyin
                                Picasso.get().load(profil_foto).into(binding.profileHeaderImage);
                                binding.kurumisim.setText(kurumisimi);
                                binding.kurulus.setText(kurulusyili);
                                binding.acikadres.setText(acikadres);
                                binding.biyografi.setText(biyografi);
                                binding.epostatext.setText(eposta);
                                binding.telefon.setText(telno);
                                binding.konum.setText(sehir + " / " + ilce);


                            }

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BarinakKullaniciDetay.this, "Kullanıcı Sayfası Yüklenemedi Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                    }
                });




    }

    public void getData() {
        firebaseFirestore.collection("Ilanlar")
                .whereEqualTo("kullaniciadi", kurumisimi)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.out.println(error.getLocalizedMessage());
                            return; // Hata olduğunda işlemi durdur.
                        }
                        if (value != null) {
                            ilanArrayList.clear(); // Listenin her veri çekilişinde temizlenmesi önemli.
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Map<String, Object> data = snapshot.getData();

                                assert data != null;
                                String baslik = (String) data.get("ilanbaslik");
                                String dowloandurl = (String) data.get("dowloandurl");
                                String sehir = (String) data.get("sehir");
                                String ilanturu = (String) data.get("ilanturu");
                                String foto = (String) data.get("userpp");
                                username = (String) data.get("kullaniciadi");
                                String hesapturu = (String) data.get("hesapturu");
                                date = null;

                                Object dateObj = data.get("date");
                                if (dateObj instanceof Timestamp) {
                                    Timestamp timestamp = (Timestamp) dateObj;
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
                                    date = sdf.format(timestamp.toDate());
                                } else if (dateObj instanceof String) {
                                    // String olarak kaydedilmişse, bu blok çalışacak
                                    date = (String) dateObj;
                                }

                                Post ilan = new Post(baslik,dowloandurl,sehir,ilanturu,date,foto,username,hesapturu);
                                ilanArrayList.add(ilan);


                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void mesajgonder(View view){
        Intent intent = new Intent(BarinakKullaniciDetay.this, MesajEkle.class);
        // Verileri intent ile MesajEkle aktivitesine gönder
        intent.putExtra("gidenveri", username);
        startActivity(intent);
    }

}