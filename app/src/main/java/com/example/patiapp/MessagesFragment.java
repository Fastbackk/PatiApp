package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.patiapp.databinding.AddPersonDialogBinding;
import com.example.patiapp.databinding.FragmentMessagesBinding;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding binding;
    private AddPersonDialogBinding binding2;
    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    String ad,soyad,kullaniciadi,foto,biyografi;
    MenuItem item;
    ArrayList<Post> ilanArrayList2;
    Adapter adapter;
    Adapter4 adapter4;
    String ID;

    public String kullaniciEposta, username;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance'ını başlatma
        firebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth nesnesini oluştur
        firebaseFirestore = FirebaseFirestore.getInstance();

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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();

        binding.buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intentt = new Intent(getContext(), HesapGiris.class);
                intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Bu flaglar ile geri yığınını temizle
                startActivity(intentt);
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        String useremail = user.getEmail();

        firebaseFirestore.collection("users").whereEqualTo("eposta", useremail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> data = documentSnapshot.getData();
                                ad = (String) data.get("ad");
                                soyad = (String) data.get("soyad");
                                kullaniciadi = (String) data.get("kullaniciadi");
                                foto= (String) data.get("profil_foto");
                                biyografi= (String) data.get("biyografi");
                                if (foto!=null){
                                    Picasso.get().load(foto).into(binding.imageView13);
                                }

                                binding.bio.setText(biyografi);
                                binding.textView2.setText(ad + " " + soyad);
                                binding.textView6.setText(kullaniciadi);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Veriler yüklenemedi!");
                    }
                });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HesapDetay.class);
                startActivity(intent);
            }
        });

        binding.buttonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), KaydedilenIlanlar.class);
                intent.putExtra("kullaniciAdi",kullaniciadi);
                startActivity(intent);
            }
        });
        binding.ilanlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IlanlarKendi ilanlarKendiFragment = new IlanlarKendi();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, ilanlarKendiFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void getData() {
        mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance'ını başlatma
        firebaseAuth = FirebaseAuth.getInstance(); // FirebaseAuth nesnesini oluştur
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String useremail = user.getEmail();

        firebaseFirestore.collection("users").whereEqualTo("eposta", useremail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> data = documentSnapshot.getData();
                                ad = (String) data.get("ad");
                                soyad = (String) data.get("soyad");
                                kullaniciadi = (String) data.get("kullaniciadi");
                                binding.textView2.setText(ad + " " + soyad);
                                binding.textView6.setText(kullaniciadi);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Veriler yüklenemedi!");
                    }
                });
        ilanArrayList2 = new ArrayList<>(); // ilanArrayList2'yi başlat
        adapter4 = new Adapter4(ilanArrayList2); // Adapter'ı başlat
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
                                String foto= (String) data.get("userpp");
                                String username= (String) data.get("kullaniciadi");
                                String hesapturu= (String) data.get("hesapturu");
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

                                Post ilan = new Post(baslik, dowloandurl, sehir, ilanturu, date,username,foto,hesapturu);
                                ilanArrayList2.add(ilan);
                            }
                            adapter4.notifyDataSetChanged(); // Adapter'ı güncelle
                        }
                    }
                });



    binding.imageView13.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.add_person_dialog, null);
            builder.setView(dialogView);



            Button goToBio = dialogView.findViewById(R.id.goToBio);
            Button goToProfile = dialogView.findViewById(R.id.goToProfile);

            final AlertDialog dialog = builder.create();
            dialog.show();

            goToProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ProfilFotoIsteme.class);

                    startActivity(intent);
                }
            });
            goToBio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), BiyografiEkle.class);
                    startActivity(intent);
                }
            });
        }
    });
    }
}
