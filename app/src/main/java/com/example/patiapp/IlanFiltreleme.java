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
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentIlanlarBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class IlanFiltreleme extends Fragment {
    private FragmentIlanlarBinding binding;
    ArrayList<Post>ilanArrayList;
    Adapter adapter;
    String neilani;



    private FirebaseFirestore firebaseFirestore;
    private String ilanturu;
    private String hayvankategori;
    private String sehir;
    private String ilce;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String ilanturu = getArguments().getString("ilanturu");
            String hayvankategori = getArguments().getString("hayvankategori");
            String sehir = getArguments().getString("sehir");
            String ilce = getArguments().getString("ilce");}





        ilanArrayList=new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        getData();
        ilanArrayList=new ArrayList<>();
        firebaseFirestore= FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View oluşturuluyor ve binding ile bağlanıyor.
        binding = FragmentIlanlarBinding.inflate(inflater, container, false);

        // Activity'den Intent alınıyor ve kullanıcı adı çekiliyor.
        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            neilani = intent.getStringExtra("ilan");

        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(ilanArrayList);
        binding.recyclerView.setAdapter(adapter);
    }
    public void getData() {
        // Verileri almadan önce ilanArrayList temizlenmeli
        ilanArrayList.clear();

        // Firestore sorgusu oluşturulur
        Query query = firebaseFirestore.collection("Ilanlar");

        // Filtreleme işlemi, kullanıcıdan gelen verilere göre yapılır
        if (ilanturu != null) {
            query = query.whereEqualTo("ilanturu", ilanturu);
        }
        if (hayvankategori != null) {
            query = query.whereEqualTo("hayvankategori", hayvankategori);
        }
        if (sehir != null) {
            query = query.whereEqualTo("sehir", sehir);
        }
        if (ilce != null) {
            query = query.whereEqualTo("ilce", ilce);
        }

        // Firestore sorgusu dinleyiciye eklenir
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firestore'dan dönen belgeler okunur ve ilanArrayList'e eklenir
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    Map<String, Object> data = snapshot.getData();

                    if (data != null) {
                        String baslik = (String) data.get("ilanbaslik");
                        String dowloandurl = (String) data.get("dowloandurl");
                        String sehir = (String) data.get("sehir");
                        String ilanturu = (String) data.get("ilanturu");
                        String hayvancinsi = (String) data.get("hayvancinsi");

                        Post ilan = new Post(baslik, dowloandurl, sehir, ilanturu, hayvancinsi);
                        ilanArrayList.add(ilan);
                    }
                }

                // Adapter'a değişikliklerin uygulandığına dair bilgi verilir
                adapter.notifyDataSetChanged();
            }
        });


        /*/
        firebaseFirestore.collection("Ilanlar").whereEqualTo("ilanturu",ilanturu).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if(value!=null){

                    for (DocumentSnapshot snapshot:value.getDocuments()){
                        Map<String,Object> data=snapshot.getData();

                        assert data != null;
                        String baslik=(String) data.get("ilanbaslik");
                        String dowloandurl=(String) data.get("dowloandurl");
                        String sehir=(String) data.get("sehir");
                        String ilanturu=(String) data.get("ilanturu");
                        String hayvancinsi=(String) data.get("hayvancinsi");

                        Post ilan=new Post(baslik,dowloandurl,sehir,ilanturu,hayvancinsi);
                        ilanArrayList.add(ilan);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

         */

    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
