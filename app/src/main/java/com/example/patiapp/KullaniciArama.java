package com.example.patiapp;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentIlanlarBinding;
import com.example.patiapp.databinding.FragmentKullaniciAramaBinding;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class KullaniciArama extends Fragment implements SearchView.OnQueryTextListener {
    private FragmentKullaniciAramaBinding binding;
    ArrayList<Post3> ilanArrayList;
    Adapter3 adapter;
    private FirebaseFirestore firebaseFirestore;
    private String kullaniciadi; // Kullanıcı adını tutacak değişken

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Fragment'in bir Options Menu'su olduğunu belirt

        ilanArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentKullaniciAramaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter3(ilanArrayList);
        binding.recyclerView.setAdapter(adapter);

        // Ara butonuna tıklanıldığında verileri getir


        // SearchView'deki metni değiştirdiğinde kullanıcı adını güncelle
        binding.SearchViewArama.setOnQueryTextListener(this);
        getData2();
    }

    // Verileri getir
    public void getData() {
        // Kullanıcı adına göre sorgu yap
        firebaseFirestore.collection("users").whereEqualTo("kullaniciadi", kullaniciadi)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            ilanArrayList.clear(); // Listenin her veri çekilişinde temizlenmesi önemli.
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Map<String, Object> data = snapshot.getData();

                                assert data != null;
                                String username = (String) data.get("kullaniciadi");
                                String ad = (String) data.get("ad");
                                String soyad = (String) data.get("soyad");

                                Post3 ilan = new Post3(username, ad, soyad);
                                ilanArrayList.add(ilan);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void getData2() {
        // Kullanıcı adına göre sorgu yap
        firebaseFirestore.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            ilanArrayList.clear(); // Listenin her veri çekilişinde temizlenmesi önemli.
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Map<String, Object> data = snapshot.getData();

                                assert data != null;
                                String username = (String) data.get("kullaniciadi");
                                String ad = (String) data.get("ad");
                                String soyad = (String) data.get("soyad");

                                Post3 ilan = new Post3(username, ad, soyad);
                                ilanArrayList.add(ilan);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu); // Menüyü oluştur
        MenuItem searchItem = menu.findItem(R.id.action_search); // SearchView'i al
        SearchView searchView = (SearchView) searchItem.getActionView(); // SearchView'i bul

        // SearchView'in metnini değiştirdiğinde bu sınıftaki onQueryTextChange metodu çalışacak
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // SearchView'deki metni kullanıcı adına ata
        kullaniciadi = newText;
        if (newText.isEmpty()) {
            getData2();
        } else {
            getData();
        }
        return false;
    }

}