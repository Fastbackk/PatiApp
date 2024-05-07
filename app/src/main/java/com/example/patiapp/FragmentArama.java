package com.example.patiapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.patiapp.databinding.FragmentBarinakAramaBinding;
import com.example.patiapp.databinding.FragmentKullaniciAramaBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class FragmentArama extends Fragment implements SearchView.OnQueryTextListener {
    private FragmentBarinakAramaBinding binding;
    ArrayList<Post4> ilanArrayList;
    Adapter5 adapter;
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
        binding = FragmentBarinakAramaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView10.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter5(ilanArrayList);
        binding.recyclerView10.setAdapter(adapter);

        // Ara butonuna tıklanıldığında verileri getir


        // SearchView'deki metni değiştirdiğinde kullanıcı adını güncelle
        binding.SearchViewArama2.setOnQueryTextListener(this);
        getData2();
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

                                String acikadres = (String) data.get("acikadres");
                                String biyografi = (String) data.get("biyografi");
                                String eposta = (String) data.get("eposta");
                                String ilce = (String) data.get("ilce");
                                String kurulusyili = (String) data.get("kurulusyili");
                                String kurumisim = (String) data.get("kurumisim");
                                String profil_foto = (String) data.get("profil_foto");
                                String sehir = (String) data.get("sehir");
                                String telno = (String) data.get("telno");


                                Post4 ilan = new Post4(acikadres,biyografi,eposta,ilce,kurulusyili,kurumisim,profil_foto,sehir,telno);
                                ilanArrayList.add(ilan);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    // Verileri getir
    public void getData() {
        // Kullanıcı adına göre sorgu yap
        firebaseFirestore.collection("Barinak").whereEqualTo("kurumisim", kullaniciadi)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            ilanArrayList.clear(); // Listenin her veri çekilişinde temizlenmesi önemli.
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Map<String, Object> data = snapshot.getData();

                                assert data != null;
                                String acikadres = (String) data.get("acikadres");
                                String biyografi = (String) data.get("biyografi");
                                String eposta = (String) data.get("eposta");
                                String ilce = (String) data.get("ilce");
                                String kurulusyili = (String) data.get("kurulusyili");
                                String kurumisim = (String) data.get("kurumisim");
                                String profil_foto = (String) data.get("profil_foto");
                                String sehir = (String) data.get("sehir");
                                String telno = (String) data.get("telno");


                                Post4 ilan = new Post4(acikadres,biyografi,eposta,ilce,kurulusyili,kurumisim,profil_foto,sehir,telno);
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