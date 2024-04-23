package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentAramaBinding;
import com.example.patiapp.databinding.FragmentIlanlarBinding;

public class AramaFragment extends Fragment {

    private FragmentAramaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAramaBinding.inflate(inflater, container, false);
        return binding.getRoot();



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Ilanlar.class);
                String ilanturu=binding.editTextText5.getText().toString();
                String hayvankategori=binding.editTextText5.getText().toString();
                String sehir=binding.editTextText5.getText().toString();
                String ilce=binding.editTextText5.getText().toString();

                intent.putExtra("ilanturu", ilanturu);
                intent.putExtra("hayvankategori", hayvankategori);
                intent.putExtra("sehir", sehir);
                intent.putExtra("ilce", ilce);


                startActivity(intent);







            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}