package com.example.parcelables;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String Nombre;
    private String Contraseña;
    private String Email;
    private String Rol;
    private Bitmap bitmap;

    public Usuario(String nombre, String contraseña, String email, String rol, Bitmap bitmap) {
        Nombre = nombre;
        Contraseña = contraseña;
        Email = email;
        Rol = rol;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    protected Usuario(Parcel in) {
        Nombre = in.readString();
        Contraseña = in.readString();
        Email = in.readString();
        Rol = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public boolean validarClave (String clave, String repclave){
        if (clave.equals(repclave) ){
            return  true;
        }else{
            return false;
        }
    }
    public  boolean validarEmail(String email, String repEmail){
        if (email.equals(repEmail) ){
            return  true;
        }else{
            return false;
        }
    }
    public int NivelSeguridad( String contra, int contador){
        if(contra.length()>11 && contador>3 ){
            return 5;
        }else if (contra.length()==10 || (contra.length()==11 && contador==2 ||  contador==3) ){
            return 4;
        }else if(contra.length()>7 && contra.length()<10  && contador==1 ){
            return 3;

        }else if(contra.length()>7 && contra.length()<10 && contador==0){
            return 2;
        }
        else {
            return 1;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Nombre);
        dest.writeString(Contraseña);
        dest.writeString(Email);
        dest.writeString(Rol);
        dest.writeParcelable(bitmap, flags);
    }
}
