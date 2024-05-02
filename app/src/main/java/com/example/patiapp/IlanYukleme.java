package com.example.patiapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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

import com.example.patiapp.databinding.ActivityIlanYuklemeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class IlanYukleme extends AppCompatActivity {
    Uri ImageData;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> izin;
    private FirebaseStorage firebaseStorage;
    private ActivityIlanYuklemeBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    private String secilenİl;
    private String secilenTur;
    private String secilenKategori;

    private ArrayAdapter<CharSequence>Adapterİl;
    private ArrayAdapter<CharSequence>AdapterTur;
    private ArrayAdapter<CharSequence>AdapterKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIlanYuklemeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        registerLauncher();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();

         Adapterİl = ArrayAdapter.createFromResource(this,R.array.ilListeleme, android.R.layout.simple_spinner_item);
         Adapterİl.setDropDownViewResource(android.R.layout.simple_spinner_item);
         binding.spinner1.setAdapter(Adapterİl);

        AdapterKategori = ArrayAdapter.createFromResource(this,R.array.kategoriListele, android.R.layout.simple_spinner_item);
        AdapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.spinner3.setAdapter(AdapterKategori);

         AdapterTur = ArrayAdapter.createFromResource(this,R.array.ilanTurListele, android.R.layout.simple_spinner_item);
         AdapterTur.setDropDownViewResource(android.R.layout.simple_spinner_item);
         binding.spinner2.setAdapter(AdapterTur);


        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 secilenİl= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                 secilenİl= parent.getItemAtPosition(0).toString();
            }
        });

        binding.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secilenTur= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                secilenTur= parent.getItemAtPosition(0).toString();
            }
        });
        binding.spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secilenKategori= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                secilenKategori= parent.getItemAtPosition(0).toString();
            }
        });
    }

    public void uploadButton(View view) {
        //universal uniq id

        UUID uuid=UUID.randomUUID();
        String ImageName="images/"+uuid+".jpg";
        if(ImageData!=null){
            storageReference.child(ImageName).putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference newReferance=firebaseStorage.getReference(ImageName);
                    newReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String dowloandurl=uri.toString();
                            String ilanbaslik=binding.editTextText7.getText().toString();
                            //String sehir=binding.editTextText8.getText().toString();
                            String ilce=binding.editTextText9.getText().toString();
                            String aciklama=binding.editTextText10.getText().toString();
                            String ekipman=binding.editTextText11.getText().toString();
                            //String hayvankategori=binding.editTextText12.getText().toString();
                            String hayvancinsi=binding.editTextText13.getText().toString();
                            //String ilanturu=binding.editTextText14.getText().toString();
                            String telno=binding.editTextText17.getText().toString();
                            String date;
                            FirebaseUser user=firebaseAuth.getCurrentUser();

                            String email=user.getEmail();

                            HashMap<String,Object>ilanData=new HashMap<>();
                            ilanData.put("dowloandurl",dowloandurl);
                            ilanData.put("ilanbaslik",ilanbaslik);
                            //ilanData.put("sehir",sehir);
                            ilanData.put("sehir",secilenİl);
                            ilanData.put("ilce",ilce);
                            ilanData.put("aciklama",aciklama);
                            ilanData.put("ekipman",ekipman);
                           // ilanData.put("hayvankategori",hayvankategori);
                            ilanData.put("hayvankategori",secilenKategori);
                            ilanData.put("hayvancinsi",hayvancinsi);
                            //ilanData.put("ilanturu",ilanturu);
                            ilanData.put("ilanturu",secilenTur);
                            ilanData.put("date",FieldValue.serverTimestamp());
                            ilanData.put("email",email);
                            ilanData.put("telno",telno);




                            firebaseFirestore.collection("Ilanlar").add(ilanData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent=new Intent(IlanYukleme.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("ilan","ilanhayvan");
                                    startActivity(intent);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(IlanYukleme.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(IlanYukleme.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });






                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(IlanYukleme.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Galeriye erişmek için izin gerekiyor!", Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        izin.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                }).show();
            } else {
                // İzin isteği başlatılıyor
                izin.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
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
                        binding.imageView2.setImageURI(ImageData);


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
                    Toast.makeText(IlanYukleme.this, "Galeriye Erişim İçin İzin Gerekiyor", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}