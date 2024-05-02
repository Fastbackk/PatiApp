package com.example.patiapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentIlanlarBinding;
<<<<<<< HEAD
=======
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
>>>>>>> master
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.HashMap;
import java.util.Locale;
>>>>>>> master
import java.util.Map;

public class Ilanlar extends Fragment {
    private FragmentIlanlarBinding binding;
    ArrayList<Post> ilanArrayList;
    Adapter adapter;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        ilanArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

=======
        ilanArrayList=new ArrayList<>();
        firebaseFirestore= FirebaseFirestore.getInstance();
>>>>>>> master
        getData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIlanlarBinding.inflate(inflater, container, false);
        Bundle args = getArguments();
        if (args != null) {
            String ilanturu = args.getString("ilanturu");
            String hayvankategori = args.getString("hayvankategori");
            String sehir = args.getString("sehir");
            String ilce = args.getString("ilce");
            // Burada bu verileri kullanabilirsiniz
        }
        return binding.getRoot();
<<<<<<< HEAD
=======

>>>>>>> master
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(ilanArrayList);
        binding.recyclerView.setAdapter(adapter);

    }
<<<<<<< HEAD

    public void getData() {
        ilanArrayList = new ArrayList<>();
        firebaseFirestore.collection("Ilanlar")
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (value != null) {
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Map<String, Object> data = snapshot.getData();
=======
    public void getData(){
        firebaseFirestore.collection("Ilanlar").orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            return; // Hata olduğunda işlemi durdur.
                        }
                        if(value!=null){
                            ilanArrayList.clear(); // Listenin her veri çekilişinde temizlenmesi önemli.
                            for (DocumentSnapshot snapshot : value.getDocuments()){
                                Map<String, Object> data = snapshot.getData();

>>>>>>> master
                                assert data != null;
                                String baslik = (String) data.get("ilanbaslik");
                                String dowloandurl = (String) data.get("dowloandurl");
                                String sehir = (String) data.get("sehir");
                                String ilanturu = (String) data.get("ilanturu");
<<<<<<< HEAD
                                String hayvancinsi = (String) data.get("hayvancinsi");
                                Post ilan = new Post(baslik, dowloandurl, sehir, ilanturu, hayvancinsi);
=======
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
>>>>>>> master
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
