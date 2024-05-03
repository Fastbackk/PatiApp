package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentMessagesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;


public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding binding;
    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    String ad;
    String soyad;
    String kullaniciadi;
    MenuItem item;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance(); // FirebaseAuth instance'ını başlatma
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

                                binding.textView2.setText(ad + " " + soyad);
                                binding.textView6.setText("@"+kullaniciadi);

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
                intent.putExtra("email", useremail);
                intent.putExtra("ad", ad);
                intent.putExtra("soyad", soyad);
                intent.putExtra("kullaniciadi", kullaniciadi);
                startActivity(intent);

            }
        });
        binding.button55.setOnClickListener(new View.OnClickListener() {
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
    public void sifreDegis(View v){
        IlanlarKendi ilanlarKendiFragment = new IlanlarKendi();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, ilanlarKendiFragment)
                .addToBackStack(null)
                .commit();
    }
}