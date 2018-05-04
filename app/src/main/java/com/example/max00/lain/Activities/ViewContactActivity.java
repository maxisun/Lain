package com.example.max00.lain.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.R;

public class ViewContactActivity extends AppCompatActivity {

    private Contacto contacto;
    private ImageView photo;
    private TextView name;
    private TextView lastname;
    private TextView email;
    private TextView phone;
    private TextView birthday;
    private ImageButton calls;
    private ImageButton share;
    private String missing = "missing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        photo = findViewById(R.id.foto_IV);
        name = findViewById(R.id.name_TV);
        lastname = findViewById(R.id.LastName_TV);
        email = findViewById(R.id.email_TV);
        phone = findViewById(R.id.phone_TV);
        birthday = findViewById(R.id.birthday_TV);
        calls = findViewById(R.id.call_IB);
        share = findViewById(R.id.share_IB);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        contacto = (Contacto) bundle.getParcelable("ContactInformation");

        name.setText(contacto.getNombre());

        if(contacto.getApellido()==null && contacto.getApellido().equals("")){
            lastname.setText(missing);
        }else {
            lastname.setText(contacto.getApellido());
        }

        if(contacto.getEmail()==null && contacto.getEmail().equals("")){
            email.setText(missing);
        }else {
            email.setText(contacto.getEmail());
        }

        if(contacto.getPhone()==null && contacto.getPhone().equals("")){
            phone.setText(missing);
        }else {
            phone.setText(contacto.getPhone());
        }

        if(contacto.getDate()==null && contacto.getDate().equals("")){
            birthday.setText(missing);
        }else {
            birthday.setText(contacto.getDate());
        }

        if(contacto.getUri()==null){
            photo.setImageResource(R.drawable.judge);
        }else {
            photo.setImageURI(contacto.getUri());
        }
    }
}
