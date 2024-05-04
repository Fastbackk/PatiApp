package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.patiapp.databinding.ActivityKullaniciDetayBinding;
import com.example.patiapp.databinding.ActivityMesajdetayBinding;
import com.example.patiapp.databinding.FragmentIlanlarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class KullaniciDetay extends AppCompatActivity {
    ActivityKullaniciDetayBinding binding;
    private Intent intent;
    ArrayList<Post3> ilanArrayList;
    ArrayList<Post> ilanArrayList2;
    Adapter adapter;
    private FirebaseFirestore firebaseFirestore;
    String ID;

    public String username, adsoyad, ad, soyad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKullaniciDetayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ilanArrayList2 = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(KullaniciDetay.this));
        adapter = new Adapter(ilanArrayList2);
        binding.recyclerView.setAdapter(adapter);

        firebaseFirestore = FirebaseFirestore.getInstance();

        //verileri alma
        intent = getIntent();

            username = intent.getStringExtra("username");

        // Kullanıcı bilgilerini gösterme
        firebaseFirestore.collection("users").whereEqualTo("kullaniciadi", username)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                ID = snapshot.getId();
                                String ad = (String) data.get("ad");
                                String soyad = (String) data.get("soyad");

                                // Verileri kullanarak UI güncelleme
                                adsoyad = ad + " " + soyad;
                                binding.textView2.setText(adsoyad);
                            } else {
                                Toast.makeText(KullaniciDetay.this, "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(KullaniciDetay.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.textView6.setText(username);
    }

    public void getData() {
        intent = getIntent();

        username = intent.getStringExtra("username");
        firebaseFirestore.collection("Ilanlar").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("kullaniciadi", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            ilanArrayList2.clear(); // Listenin her veri çekilişinde temizlenmesi önemli.
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Map<String, Object> data = snapshot.getData();

                                assert data != null;
                                String baslik = (String) data.get("ilanbaslik");
                                String dowloandurl = (String) data.get("dowloandurl");
                                String sehir = (String) data.get("sehir");
                                String ilanturu = (String) data.get("ilanturu");
                                String date = null;

                                Object dateObj = data.get("date");
                                if (dateObj instanceof Timestamp) {
                                    Timestamp timestamp = (Timestamp) dateObj;
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                    date = sdf.format(timestamp.toDate());
                                } else if (dateObj instanceof String) {
                                    // String olarak kaydedilmişse, bu blok çalışacak
                                    date = (String) dateObj;
                                }

                                Post ilan = new Post(baslik, dowloandurl, sehir, ilanturu, date);
                                ilanArrayList2.add(ilan);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
