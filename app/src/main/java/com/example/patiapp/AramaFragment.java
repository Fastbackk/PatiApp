package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentAramaBinding;
import com.example.patiapp.databinding.FragmentIlanlarBinding;

public class AramaFragment extends Fragment {

    private FragmentAramaBinding binding;
    String secilenHayvan;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAramaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ilan türleri
        String[] ilanturu = getResources().getStringArray(R.array.ilanTurListele);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(getContext(), R.layout.dropdown_item,ilanturu);
        binding.autoCompleteTextView2.setAdapter(adapterItems);
        binding.autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                System.out.println(item);
            }
        });

        //kategori
        String[] hayvankategori = getResources().getStringArray(R.array.kategoriListele);
        ArrayAdapter<String> adapterItems2 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item,hayvankategori);
        binding.autoCompleteTextView.setAdapter(adapterItems2);
        binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                secilenHayvan=item;
            }
        });
        //cinsler
        if (secilenHayvan.equals("Köpek")){
            System.out.println(secilenHayvan);
        }
        else if(secilenHayvan.equals("Kedi")){
            System.out.println(secilenHayvan);

        }

        String[] hayvancinsi= getResources().getStringArray(R.array.KedicinsListele);
        ArrayAdapter<String> adapterItems3 = new ArrayAdapter<>(getContext(), R.layout.dropdown_item,hayvancinsi);
        binding.autoCompleteTextView3.setAdapter(adapterItems3);
        binding.autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
                System.out.println(item);
            }
        });








    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



}