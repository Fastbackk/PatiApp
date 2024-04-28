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


public class Ilanlar extends Fragment {
    private FragmentIlanlarBinding binding;
    ArrayList<Post>ilanArrayList;
    Adapter adapter;





    private FirebaseFirestore firebaseFirestore;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ilanArrayList=new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        getData();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View oluşturuluyor ve binding ile bağlanıyor.
        binding = FragmentIlanlarBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(ilanArrayList);
        binding.recyclerView.setAdapter(adapter);
    }
    public void getData(){

          //Query
          //-----
          // tuna@gmail'in tüm ilanlarını getirir
          // whereEqualTo("email", tuna@gmail.com")

          firebaseFirestore.collection("Ilanlar").orderBy("date", Query.Direction.DESCENDING)
                  .addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                                  Post ilan=new Post(baslik,dowloandurl,sehir,ilanturu);
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

}