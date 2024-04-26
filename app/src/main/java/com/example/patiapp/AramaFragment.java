package com.example.patiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
        });*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}