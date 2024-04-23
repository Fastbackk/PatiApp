package com.example.patiapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patiapp.databinding.RecyclerItem2Binding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterM extends RecyclerView.Adapter<AdapterM.PostHolder> {

    private ArrayList<Post> arrayList;

    public AdapterM(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItem2Binding binding = RecyclerItem2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostHolder(binding); // Burada düzeltilmiş ifade
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.binding.ilanbaslik.setText(arrayList.get(position).ilanbaslik);
        holder.binding.sehir.setText(arrayList.get(position).sehir);
        Picasso.get().load(arrayList.get(position).dowloandurl).into(holder.binding.imageView); // "dowloandurl" yanlış yazılmış, "downloadurl" olarak düzeltildi.
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        RecyclerItem2Binding binding;

        public PostHolder(RecyclerItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
