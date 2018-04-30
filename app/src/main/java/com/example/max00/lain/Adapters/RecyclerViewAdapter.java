package com.example.max00.lain.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ContactsViewHolder> {

    private ArrayList<Contacto> contactos;

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView name;
        ImageView img;

        public ContactsViewHolder(View itemview){
            super(itemview);
            cardView = itemview.findViewById(R.id.card_view);
            name = itemview.findViewById(R.id.TV_name);
            img = itemview.findViewById(R.id.imagen);
        }
    }

    public RecyclerViewAdapter(ArrayList<Contacto> lolis) {
        this.contactos = lolis;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return (new ContactsViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {

        final Contacto contacto = contactos.get(position);

        holder.name.setText(contacto.getNombre());
        //holder.img.setImageResource(contacto.getImagen());
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

}
