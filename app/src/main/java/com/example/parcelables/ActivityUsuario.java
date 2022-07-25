package com.example.parcelables;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.parcelables.databinding.ActivityUsuarioBinding;

import java.sql.SQLOutput;

public class ActivityUsuario extends AppCompatActivity {
    public static final String USUARIO_KEY ="usuario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUsuarioBinding binding = ActivityUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        Usuario usuario = extras.getParcelable(USUARIO_KEY);
        System.out.println(usuario.getNombre()+" contra: "+usuario.getContraseña()+" email: "+usuario.getEmail()+ " rol: "+usuario.getRol());
        binding.setUsuario(usuario);
        String usua =usuario.getNombre();
        MainActivity main = new MainActivity();
        String contra =usuario.getContraseña();
        String rol = usuario.getRol();
        String email= usuario.getEmail();
        binding.imgUsuario.setImageBitmap(usuario.getBitmap());
        binding.Rol.setText("Rol: "+rol);
        binding.txtEmail.setText(email);
        binding.txtNombre.setText(usua);

        int contador = 0;
        char arreglo[] = contra.toCharArray();
        for (int i = 0; i < contra.length(); i++) {
            if (arreglo[i]=='?' || arreglo[i]=='*' || arreglo[i]=='?' || arreglo[i]=='¡' || arreglo[i]=='!' || arreglo[i]=='#' || arreglo[i]=='$' || arreglo[i]=='%' || arreglo[i]=='&') {
                contador= contador+1;
            }
        }
        int val=usuario.NivelSeguridad(contra, contador);

        if(val==5 ){
        binding.valcontra.setRating(5);
        binding.txtvalcontra.setText("ALTA");
        }else if (val==4){
            binding.valcontra.setRating(4);
            binding.txtvalcontra.setText("MEDIA ALTA");
        }else if(val==3){
            binding.valcontra.setRating(3);
            binding.txtvalcontra.setText("MEDIA   ");

        }else if(val==2){
            binding.valcontra.setRating(2);
            binding.txtvalcontra.setText("BAJA");
        }
        else if(val==1){
            binding.valcontra.setRating(1);
            binding.txtvalcontra.setText("INSEGURA");
        }
    }
}