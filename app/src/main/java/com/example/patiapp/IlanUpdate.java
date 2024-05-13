package com.example.patiapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.patiapp.databinding.ActivityIlanUpdateBinding;
import com.example.patiapp.databinding.ActivityIlanYuklemeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class IlanUpdate extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    Uri ImageData;
    String referansdeger;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> izin;
    Timestamp timestamp;
    private FirebaseStorage firebaseStorage;
    private ActivityIlanUpdateBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private String secilenİl, secilenTur, secilenKategori;
    private ArrayAdapter<CharSequence> Adapterİl, AdapterTur, AdapterKategori;
    public String kullaniciEposta2, NickName, ID;
    ArrayList<Post> ilanArrayList;
    Adapter adapter;
    public String username, ilanbaslik, ilanturu, date, kullaniciemail, aciklamatext, telno, sehir, ilce, dowloandURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIlanUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        firebaseAuth = FirebaseAuth.getInstance();

        //Kullanıcı adını aldım
        kullaniciEposta2 = firebaseAuth.getCurrentUser().getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").whereEqualTo("eposta", kullaniciEposta2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                //  ID= snapshot.getId();
                                String kullaniciadi = (String) data.get("kullaniciadi");
                                NickName = kullaniciadi;
                                System.out.println(NickName);
                            } else {
                                Toast.makeText(IlanUpdate.this, "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IlanUpdate.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        registerLauncher();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        ilanbaslik = intent.getStringExtra("ilanbaslik");
        ilanturu = intent.getStringExtra("ilanturu");
        date = intent.getStringExtra("date");
        kullaniciemail = intent.getStringExtra("kullaniciemail");
        aciklamatext = intent.getStringExtra("aciklamatext");
        telno = intent.getStringExtra("telno");
        sehir = intent.getStringExtra("sehir");
        ilce = intent.getStringExtra("ilce");
        dowloandURL = intent.getStringExtra("dowloandurl");


        binding.editTextText12.setText(ilanbaslik);
        binding.editTextTexttt.setText(ilce);
        binding.editTextTextt.setText(aciklamatext);
        binding.editTextNumber2.setText(telno);
        Picasso.get().load(dowloandURL).into(binding.imageView2);
        binding.imageView2.setEnabled(false);


    }

    public void getDataID() {
        firebaseFirestore.collection("Ilanlar").whereEqualTo("date", timestamp)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            if (snapshot.exists()) {
                                Map<String, Object> data = snapshot.getData();
                                //ID'yi aldık şimdi ise ID'yi ilanın içerisinde gömeceğiz

                                ID = snapshot.getId();
                                System.out.println(ID);


                                // Belirli bir dokümanı güncelleme
                                DocumentReference docRef = firebaseFirestore.collection("Ilanlar").document(ID);
                                docRef.update("ID", ID)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Güncelleme başarılı
                                                System.out.println("Doküman başarıyla güncellendi: " + ID);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Güncelleme sırasında hata
                                                System.err.println("Hata! Doküman güncellenemedi: " + e.getLocalizedMessage());
                                            }
                                        });
                            } else {
                                Toast.makeText(IlanUpdate.this, "Belirtilen kriterlere uygun ilan bulunamadı.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IlanUpdate.this, "Veri yükleme sırasında bir hata oluştu: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void uploadButton(View view) {
        //universal uniq id

        UUID uuid = UUID.randomUUID();
        String ImageName = "images/" + uuid + ".jpg";
        if (ImageData != null) {
            storageReference.child(ImageName).putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference newReferance = firebaseStorage.getReference(ImageName);
                    newReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String dowloandurl = uri.toString();
                            String kullaniciAdi;
                            String ilanbaslik = binding.editTextText12.getText().toString();
                            //String sehir=binding.editTextText8.getText().toString();
                            String ilce = binding.editTextTexttt.getText().toString();
                            String aciklama = binding.editTextTextt.getText().toString();
                            String saglik = binding.editTextText14.getText().toString();
                            //String hayvankategori=binding.editTextText12.getText().toString();
                            //String hayvancinsi=binding.editTextText13.getText().toString();
                            //String ilanturu=binding.editTextText14.getText().toString();
                            //String telno=binding.editTextText17.getText().toString();

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            String email = user.getEmail();

                            HashMap<String, Object> ilanData = new HashMap<>();
                            ilanData.put("dowloandurl", dowloandurl);
                            ilanData.put("ilanbaslik", ilanbaslik);
                            //ilanData.put("sehir",sehir);
                            ilanData.put("sehir", secilenİl);
                            ilanData.put("ilce", ilce);
                            ilanData.put("aciklama", aciklama);
                            ilanData.put("saglikdurumu", saglik);
                            // ilanData.put("hayvankategori",hayvankategori);
                            ilanData.put("hayvankategori", secilenKategori);
                            // ilanData.put("hayvancinsi",hayvancinsi);
                            //ilanData.put("ilanturu",ilanturu);
                            ilanData.put("ilanturu", secilenTur);

                            timestamp = new Timestamp(new Date());
                            ilanData.put("date", timestamp);

                           /* Timestamp Date=FieldValue.serverTimestamp();
                            ilanData.put("date",Date);*/

                            ilanData.put("email", email);
                            ilanData.put("kullaniciadi", NickName);
                            ilanData.put("telno", telno);
                            firebaseFirestore.collection("Ilanlar").add(ilanData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //ID'yi içeri yükleme
                                    getDataID();


                                    Intent intent = new Intent(IlanUpdate.this, IlanlarKendi.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("ilan", "ilanhayvan");
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(IlanUpdate.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(IlanUpdate.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(IlanUpdate.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void selectImage(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                // İzin verilmemişse izin iste
                requestPermissions(new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_AUDIO
                }, PERMISSION_REQUEST_CODE);
            } else {
                // İzin verilmişse galeriye git
                openGallery();
            }
        } else {
            // Android 13 öncesi sürümler için mevcut izin isteme yöntemini kullan
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // İzin verilmemişse izin iste
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                // İzin verilmişse galeriye git
                openGallery();
            }
        }
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzin verildiyse galeriye git
                openGallery();
            } else {
                Toast.makeText(this, "Galeriye erişim izni reddedildi.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void registerLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null) {
                        ImageData = intentFromResult.getData();
                        //   binding.imageView11.setImageURI(ImageData);


                    }
                }
            }
        });
        izin = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if (o) {
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                } else {
                    Toast.makeText(IlanUpdate.this, "Galeriye Erişim İçin İzin Gerekiyor", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }










}