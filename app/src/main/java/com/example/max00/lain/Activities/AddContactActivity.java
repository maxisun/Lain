package com.example.max00.lain.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.Fragments.ContactsFragment;
import com.example.max00.lain.R;

import java.util.Calendar;

public class AddContactActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Button addContact;
    private Button buttonImagen;
    private DatePickerDialog.OnDateSetListener mdateSetListener;
    private Uri uri;

    private String name;
    private String Lastname;
    private String Email;
    private String phone;
    private String birthday;
    private int image;

    private ImageView imageView;
    private EditText NameR;
    private EditText LastNameR;
    private EditText emailR;
    private EditText PhoneR;
    private TextView textViewBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.add_img);
        NameR = findViewById(R.id.editText9);
        LastNameR = findViewById(R.id.editText10);
        emailR = findViewById(R.id.editText13);
        PhoneR = findViewById(R.id.editText14);
        textViewBirthday = findViewById(R.id.botonBirthday);
        buttonImagen = findViewById(R.id.botonaddimagen);
        addContact = findViewById(R.id.botonAddContacto);
        buttonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        textViewBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(
                    AddContactActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mdateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month + "/" + year;
                textViewBirthday.setText(date);
            }
        };

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacto contacto = new Contacto(NameR.getText().toString(),LastNameR.getText().toString(),emailR.getText().toString(),PhoneR.getText().toString(),textViewBirthday.getText().toString(),uri,false);
                Intent intent = new Intent(getApplicationContext(), ContactsFragment.class);
                intent.putExtra("New_Contact",contacto);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });


    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }



}
