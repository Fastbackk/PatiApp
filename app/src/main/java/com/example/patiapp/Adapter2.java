package com.example.patiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patiapp.databinding.RecyclerMessageBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.PostHolder> {


    private ArrayList<Post2> arrayList;

    public Adapter2(ArrayList<Post2> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerMessageBinding binding=RecyclerMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.binding.ilanbaslik.setText(arrayList.get(position).mesaj);
        holder.binding.textView18.setText(arrayList.get(position).mesajbaslik);
        //holder.binding.sehir.setText(arrayList.get(position).mesajfromsend);
        //holder.binding.sehir.setText(arrayList.get(position).mesajtosend);
        holder.binding.textView17.setText(arrayList.get(position).username);
        //holder.binding.tarih.setText(arrayList.get(position).date);
        //Picasso.get().load(arrayList.get(position).dowloandurl).into(holder.binding.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), Mesajdetay.class);
                intent.putExtra("mesajbaslik",arrayList.get(position).mesajbaslik);
                intent.putExtra("username",arrayList.get(position).username);
                intent.putExtra("mesaj",arrayList.get(position).mesaj);
                intent.putExtra("gonderenemail",arrayList.get(position).gonderenemail);
                intent.putExtra("alici",arrayList.get(position).alici);
                //intent.putExtra("sehir",arrayList.get(position).sehir);
                //intent.putExtra("date",arrayList.get(position).date);
                //intent.putExtra("dowloandurl",arrayList.get(position).dowloandurl);





                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
         return arrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        RecyclerMessageBinding binding;
        public PostHolder(RecyclerMessageBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}