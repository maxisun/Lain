package com.example.max00.lain.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.max00.lain.Activities.ViewContactActivity;
import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ContactsViewHolder> {

    private List<Contacto> contactos;
    private Context context;
    private LayoutInflater layoutInflater;

    public class ContactsViewHolder extends RecyclerView.ViewHolder{

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

    public RecyclerViewAdapter(Context context,List<Contacto> lolis) {
        this.contactos = lolis;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.card_view,parent,false);
        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(v);
        return contactsViewHolder;

        /*View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return (new ContactsViewHolder(v));*/
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {

        final Contacto contacto = contactos.get(position);

        holder.name.setText(contacto.getNombre());
        holder.img.setImageResource(contacto.getImagen());
        holder.img.setImageURI(contacto.getUri());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("ContactInformation",contacto);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("ContactInformation",contacto);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

}
