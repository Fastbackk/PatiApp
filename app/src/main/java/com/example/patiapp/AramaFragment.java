package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.patiapp.databinding.FragmentAramaBinding;
import com.example.patiapp.databinding.FragmentIlanlarBinding;

public class AramaFragment extends Fragment {

    private FragmentAramaBinding binding;
    private String filtreİl;
    private String filtreKategori;
    private String filtreTur;

    private ArrayAdapter<CharSequence>Adapterİl;
    private ArrayAdapter<CharSequence>AdapterTur;
    private ArrayAdapter<CharSequence>AdapterKategori;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAramaBinding.inflate(inflater, container, false);

        Adapterİl = ArrayAdapter.createFromResource(getContext(), R.array.ilListeleme, android.R.layout.simple_spinner_item);
        Adapterİl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner3.setAdapter(Adapterİl);

        AdapterKategori = ArrayAdapter.createFromResource(getContext(), R.array.kategoriListele, android.R.layout.simple_spinner_item);
        AdapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner2.setAdapter(AdapterKategori);

        AdapterTur = ArrayAdapter.createFromResource(getContext(), R.array.ilanTurListele, android.R.layout.simple_spinner_item);
        AdapterTur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner1.setAdapter(AdapterTur);


        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtreTur= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filtreTur= parent.getItemAtPosition(0).toString();
            }
        });

        binding.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtreKategori= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filtreKategori= parent.getItemAtPosition(0).toString();
            }
        });
        binding.spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtreTur= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filtreTur= parent.getItemAtPosition(0).toString();
            }
        });

        return binding.getRoot();


    }






/*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    /*    binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IlanFiltreleme ilanFiltrelemeFragment = new IlanFiltreleme();

                // Verileri Fragment'a aktarma
                Bundle args = new Bundle();
                args.putString("ilanturu", binding.editTextText5.getText().toString());
                args.putString("hayvankategori", binding.editTextText5.getText().toString());
                args.putString("sehir", binding.editTextText5.getText().toString());
                args.putString("ilce", binding.editTextText5.getText().toString());
                ilanFiltrelemeFragment.setArguments(args);

                // FragmentManager ile Fragment'ı yerleştirme
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, ilanFiltrelemeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



}