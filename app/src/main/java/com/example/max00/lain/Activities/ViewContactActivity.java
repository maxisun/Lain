package com.example.max00.lain.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;

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
    private Spinner spinner;
    private ArrayList<String> telefonos;

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
        //spinner = findViewById(R.id.SP_phones);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        contacto = (Contacto) bundle.getParcelable("ContactInformation");

        //telefonos.add(contacto.getPhone());

        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,simple_spinner_item,telefonos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

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
            photo.setImageResource(R.drawable.perfil);
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
                share(v);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarContacto();
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

    //se crea el intent y se usa la imagen del bitmap para poder compartir
    public void share(View view) {
        Bitmap bitmap;
        bitmap = getBitmapFromView(photo);

        try {
            File file = new File(this.getExternalCacheDir(), "Contact_Photo.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.putExtra(Intent.EXTRA_TEXT,"Name: "+name.getText().toString() + "\nLast Name: "+lastname.getText().toString() + "\nEmail: "+email.getText().toString() + "\nPhone: "+phone.getText().toString() + "\nBirthday: "+birthday.getText().toString());
            intent.setType("*/*");
            startActivity(Intent.createChooser(intent, "Share with:"));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Error: "+e,Toast.LENGTH_LONG).show();
        }
    }

    //Crea una imagen y se pone en un canvas para que pueda ser soportada para ser compuesta a travez de un bitmap
    public Bitmap getBitmapFromView(View view) {
        Bitmap Result = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas One = new Canvas(Result);
        Drawable Background = view.getBackground();

        if (Background != null) {
            Background.draw(One);
        }
        else {
            One.drawColor(Color.WHITE);
        }
        view.draw(One);
        return Result;
    }

    public void editarContacto(){
        Intent intent = new Intent(getApplicationContext(),EditContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("EditContact",contacto);
        intent.putExtras(bundle);
        //intent.putExtra(Intent.EXTRA_TEXT, pos);
        ViewContactActivity.this.startActivity(intent);
    }

}
