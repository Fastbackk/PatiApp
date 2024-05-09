package com.example.patiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityMamaBagisiOlusturBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MamaBagisiOlustur extends AppCompatActivity {
    private ActivityMamaBagisiOlusturBinding binding;
    String kullaniciadi;
    String ID;
    Timestamp timestamp;
    String kullaniciEposta;
    String secilenHayvan;
    private Uri selectedImageUri = null;  // Seçilen resmin URI'sini saklamak için

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMamaBagisiOlusturBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        kullaniciEposta=mAuth.getCurrentUser().getEmail();

        firebaseFirestore.collection("users").whereEqualTo("eposta", kullaniciEposta)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();

                                kullaniciadi= (String) data.get("kullaniciadi");


                            }
                            else {
                                firebaseFirestore.collection("Barinak").whereEqualTo("eposta",kullaniciEposta)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){
                                                    if (documentSnapshot.exists()){
                                                        Map<String, Object> data=documentSnapshot.getData();
                                                        kullaniciadi= (String) data.get("kullaniciadi");
                                                    }else{
                                                        Toast.makeText(MamaBagisiOlustur.this, "Belirtilen kriterlere uygun kullanıcı bulunamadı.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(MamaBagisiOlustur.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MamaBagisiOlustur.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
       /* //kategori
        String[] hayvankategori = getResources().getStringArray(R.array.kategoriListele2);
        ArrayAdapter<String> adapterItems2 = new ArrayAdapter<>(MamaBagisiOlustur.this, R.layout.dropdown_item,hayvankategori);
        binding.kategoritext.setAdapter(adapterItems2);
        binding.kategoritext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                secilenHayvan=item;


            }
        });*/



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            binding.imageView17.setImageURI(selectedImageUri);  // Seçilen resmi ImageView'da göster
        }
    }
}