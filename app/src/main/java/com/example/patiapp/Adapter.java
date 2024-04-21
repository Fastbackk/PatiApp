package com.example.patiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patiapp.databinding.RecyclerItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.PostHolder> {


    private ArrayList<Post> arrayList;

    public Adapter(ArrayList<Post> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerItemBinding binding=RecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.binding.ilanbaslik.setText(arrayList.get(position).ilanbaslik);
        holder.binding.ilanturu.setText(arrayList.get(position).ilanturu);
        holder.binding.hayvancinsi.setText(arrayList.get(position).hayvancinsi);
        holder.binding.sehir.setText(arrayList.get(position).sehir);
        Picasso.get().load(arrayList.get(position).dowloandurl).into(holder.binding.imageView);


    }

    @Override
    public int getItemCount() {
         return arrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        RecyclerItemBinding binding;
        public PostHolder(RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
