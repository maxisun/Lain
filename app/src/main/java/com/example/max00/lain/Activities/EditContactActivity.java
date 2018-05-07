package com.example.max00.lain.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.R;

public class EditContactActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private ImageView foto;
    private Button addImage;
    private EditText name;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private TextView date;
    private Button modify;
    private Uri uri;
    private Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        foto = findViewById(R.id.IV_photo);
        addImage = findViewById(R.id.B_addimage);
        name = findViewById(R.id.ED_name);
        lastname = findViewById(R.id.ED_lastname);
        email = findViewById(R.id.ED_email);
        phone = findViewById(R.id.ED_phone);
        date = findViewById(R.id.ET_calendar);
        modify = findViewById(R.id.B_modificar);

        Intent intent = this.getIntent();
        /*Bundle bundle = intent.getExtras();
        contacto = (Contacto) bundle.getParcelable("EditContact");

        //foto.setImageURI(contacto.getUri());
        name.setText(contacto.getNombre());
        lastname.setText(contacto.getApellido());
        email.setText(contacto.getEmail());
        phone.setText(contacto.getPhone());
        date.setText(contacto.getDate());*/

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            uri = data.getData();
            foto.setImageURI(uri);
        }
    }
}
