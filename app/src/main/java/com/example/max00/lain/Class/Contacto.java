package com.example.max00.lain.Class;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.CheckBox;

import java.io.Serializable;

public class Contacto implements Parcelable {

    private String nombre;
    private String apellido;
    private String email;
    private String phone;
    private String date;
    private int Imagen;
    private Uri uri;
    private Boolean check;


    public Contacto(String nombre, String apellido, String email, String phone, String date) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.date = date;
    }

    public Contacto(String nombre, String apellido, String email, String phone, String date, int imagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.date = date;
        Imagen = imagen;
    }

    public Contacto(String nombre, String apellido, String email, String phone, String date, Uri uri, Boolean check) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.date = date;
        this.uri = uri;
        this.check = check;
    }

    protected Contacto(Parcel in) {
        nombre = in.readString();
        apellido = in.readString();
        email = in.readString();
        phone = in.readString();
        date = in.readString();
        Imagen = in.readInt();
        uri = in.readParcelable(Uri.class.getClassLoader());
        byte tmpCheck = in.readByte();
        check = tmpCheck == 0 ? null : tmpCheck == 1;
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Contacto(String nombre, int imagen) {
        this.nombre = nombre;
        this.Imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(date);
        dest.writeInt(Imagen);
        dest.writeParcelable(uri, flags);
        dest.writeByte((byte) (check == null ? 0 : check ? 1 : 2));
    }
}
