package com.example.max00.lain.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.R;

public class ViewContactActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private Contacto contacto;
    private ImageView photo;
    private TextView name;
    private TextView lastname;
    private TextView email;
    private TextView phone;
    private TextView birthday;
    private ImageButton calls;
    private ImageButton share;
    private ImageButton edit;
    private String missing = "missing";
    private Uri uri;

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
        edit = findViewById(R.id.editContact);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        contacto = (Contacto) bundle.getParcelable("ContactInformation");

        name.setText(contacto.getNombre());

        if (contacto.getApellido() == null && contacto.getApellido().equals("")) {
            lastname.setText(missing);
        } else {
            lastname.setText(contacto.getApellido());
        }

        if (contacto.getEmail() == null && contacto.getEmail().equals("")) {
            email.setText(missing);
        } else {
            email.setText(contacto.getEmail());
        }

        if (contacto.getPhone() == null && contacto.getPhone().equals("")) {
            phone.setText(missing);
        } else {
            phone.setText(contacto.getPhone());
        }

        if (contacto.getDate() == null && contacto.getDate().equals("")) {
            birthday.setText(missing);
        } else {
            birthday.setText(contacto.getDate());
        }

        if (contacto.getUri() == null) {
            photo.setImageResource(R.drawable.judge);
        } else {
            photo.setImageURI(contacto.getUri());
            uri = contacto.getUri();
        }

        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makephonecall();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

    }

    public void makephonecall(){
        String number = phone.getText().toString();
        if(ContextCompat.checkSelfPermission(ViewContactActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ViewContactActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makephonecall();
            }else {
                Toast.makeText(this,"Permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void share(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.putExtra(Intent.EXTRA_TEXT,"Name: "+name.getText().toString() + "\nLast Name: "+lastname.getText().toString() +
        "\nEmail: "+email.getText().toString() + "\nPhone: "+phone.getText().toString() + "\nBirthday: "+birthday.getText().toString());
        Intent chooser = Intent.createChooser(intent,"Share with:");
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }
    }

}
