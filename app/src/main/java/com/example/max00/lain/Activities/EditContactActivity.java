package com.example.max00.lain.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.Fragments.ContactsFragment;
import com.example.max00.lain.MainActivity;
import com.example.max00.lain.R;

import java.util.Calendar;

public class EditContactActivity extends AppCompatActivity{

    private static final int PICK_IMAGE = 100;
    private DatePickerDialog.OnDateSetListener mdateSetListener;
    private ImageView foto;
    private Button addImage;
    private EditText name;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private TextView date;
    private Button modify;
    private Uri uri;
    public Contacto contacto;
    public String position;

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
        Bundle bundle = intent.getExtras();
        contacto = (Contacto) bundle.getParcelable("EditContact");
        position = intent.getStringExtra(Intent.EXTRA_TEXT);

        /*foto.setImageURI(contacto.getUri());
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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(EditContactActivity.this, android.R.style.Theme_Holo_Light_Dialog,mdateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mdateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String dates = dayOfMonth + "/" + month + "/" + year;
                date.setText(dates);
            }
        };

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
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

    public void update(){
        if(!name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !phone.getText().toString().isEmpty() && date.getText().toString()!= "DD/MM/YYYY" && uri !=null){
            Contacto contacto = new Contacto(name.getText().toString(), lastname.getText().toString(), email.getText().toString(), phone.getText().toString(), date.getText().toString(), uri, false);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("EditContact",contacto);
            intent.putExtras(bundle);
            intent.putExtra(Intent.EXTRA_TEXT,position);
            EditContactActivity.this.startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"Termine de ingresar datos e imagen. Para ingresar fecha toque la fecha por defecto",Toast.LENGTH_LONG).show();
        }
    }

}
