package com.example.patiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentFavBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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


public class FavFragment extends Fragment {
    private FragmentFavBinding binding;
    ArrayList<Post2> messageArrayList;
    Adapter2 adapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    public String kullaniciEposta, username;
    public String alici;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth nesnesini oluştur


        kullaniciEposta = firebaseAuth.getCurrentUser().getEmail(); // Kullanıcı e-postasını al

        firebaseFirestore.collection("users").whereEqualTo("eposta", kullaniciEposta)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                username = (String) data.get("kullaniciadi");
                                Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();
                                System.out.println(username);
                                getData(); // Kullanıcı adı alındıktan sonra verileri getir
                            } else {
                                Toast.makeText(getContext(), "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        firebaseFirestore.collection("Barinak").whereEqualTo("eposta", kullaniciEposta)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                username = (String) data.get("kurumisim");
                                Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();
                                System.out.println(username);
                                getData(); // Kullanıcı adı alındıktan sonra verileri getir
                            } else {
                                Toast.makeText(getContext(), "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });



    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView6.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter2(messageArrayList);
        binding.recyclerView6.setAdapter(adapter);
        binding.button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MesajEkle.class);
                startActivity(intent);
            }
        });
        binding.giden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GidenFragment GidenFragment = new GidenFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, GidenFragment);
                transaction.commit();
            }
        });

        binding.gelenKutusuTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();



                // İlgili belgeyi sorgula ve sil
                firebaseFirestore.collection("Messages").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("alici", alici)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                                    // Belgeyi silme
                                    snapshot.getReference().delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Silme başarılı olduğunda yapılacak işlemler
                                                    Toast.makeText(getContext(), "Gelen Mesajlar başarıyla silindi", Toast.LENGTH_SHORT).show();
                                                    getData();
                                                    // Silme işleminden sonra belki bir işlem yapmak istersiniz
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Hata durumunda kullanıcıya bilgi verme
                                                    Toast.makeText(getContext(), "Silme işlemi başarısız: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Veri yüklenemediği durumda kullanıcıya bilgi verme
                                Toast.makeText(getContext(), "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    public void getData() {
        firebaseFirestore.collection("Messages").whereEqualTo("alici", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            return; // Hata olduğunda işlemi durdur.
                        }
                        if (value != null) {
                            messageArrayList.clear(); // Listenin her veri çekilişinde temizlenmesi önemli.
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Map<String, Object> data = snapshot.getData();
                                assert data != null;
                                String mesajbaslik = (String) data.get("mesajbaslik");
                                String username = (String) data.get("username");
                                String mesaj = (String) data.get("mesaj");
                                String gonderenemail = (String) data.get("gonderenemail");
                                alici = (String) data.get("alici");
                                String profil_picture = (String) data.get("profil_picture");
                                String date = null;
                                Object dateObj = data.get("date");
                                if (dateObj instanceof Timestamp) {
                                    Timestamp timestamp = (Timestamp) dateObj;
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    date = sdf.format(timestamp.toDate());
                                } else if (dateObj instanceof String) {
                                    date = (String) dateObj;
                                }
                                Post2 ilan = new Post2(mesajbaslik, username, mesaj, gonderenemail, alici,profil_picture,date);
                                messageArrayList.add(ilan);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}