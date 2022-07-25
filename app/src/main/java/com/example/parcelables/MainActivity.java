package com.example.parcelables;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcelables.databinding.ActivityMainBinding;
import com.example.parcelables.databinding.ActivityUsuarioBinding;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
 public String rol="";
    Bitmap bitmap;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
        binding.sololetras.setVisibility(View.GONE);
        binding.max.setVisibility(View.GONE);
        binding.imgCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirCamara();
            }
        });
        binding.txtUsuario.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String usuario= binding.txtUsuario.getText().toString();
                if(!valid(usuario.trim())){
                    binding.sololetras.setVisibility(View.VISIBLE);
                    binding.txtUsuario.setText("");
                    return false;
                }else{
                    binding.sololetras.setVisibility(View.GONE);
                    return true;
                }
            }
        });
        binding.txtContra.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String clave =  binding.txtContra.getText().toString();
                if (clave.length()<5){
                    binding.max.setVisibility(View.VISIBLE);
                    return false;
                }else{
                    binding.max.setVisibility(View.GONE);
                }

                return false;
            }
        });
        binding.txtVerContra.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String clave =  binding.txtVerContra.getText().toString();
                if (clave.length()<5){
                    binding.max.setVisibility(View.VISIBLE);
                    return false;
                }else{
                    binding.max.setVisibility(View.GONE);
                }

                return false;
            }
        });
        binding.bntIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre =  binding.txtUsuario.getText().toString();
                String clave =  binding.txtContra.getText().toString();
                String repclave =  binding.txtVerContra.getText().toString();
                String email =  binding.txtEmail.getText().toString();
                String ver_Email=  binding.txtVerEmail.getText().toString();
                Usuario usu = new Usuario(nombre, clave, email, rol, bitmap);
                boolean f= usu.validarClave(clave, repclave);
                boolean x= usu.validarEmail(email, ver_Email);
                if (!validarEmail(email)|| !validarEmail(ver_Email)){
                    Context context = MainActivity.this;
                    CharSequence text = "Correo invalido";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else {
                    if (f == true) {
                        if (x == true) {
                            if ( binding.rolAdmin.isChecked() == true &  binding.rolInvt.isChecked() == true) {
                                Context context = MainActivity.this;
                                CharSequence text = "Seleccione un solo rol";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            } else if ( binding.rolAdmin.isChecked() == false &  binding.rolInvt.isChecked() == false) {
                                Context context = MainActivity.this;
                                CharSequence text = "Seleccione un rol";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            } else {
                                if (clave.equals(repclave)) {
                                    if (nombre.equals("") || clave.equals("") || repclave.equals("")) {
                                        Context context = MainActivity.this;
                                        CharSequence text = "Llene todos los campos";
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    } else if(bitmap!=null){
                                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                        alert.setMessage("Registrado Correctamente");
                                        alert.setTitle("Registro");
                                        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText((MainActivity.this), "Bienvenido", Toast.LENGTH_LONG).show();
                                                if ( binding.rolAdmin.isChecked() == true) {
                                                    rol = " Administrador";
                                                } else {
                                                    rol = "Invitado";
                                                }
                                                    abrirActivityDetalle(nombre, clave, email, rol, bitmap);
                                            }
                                        });
                                        alert.show();
                                    }else{
                                        Context context = MainActivity.this;
                                        CharSequence text = "Porfavor tomar la foto para su usuario";
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    }
                                }
                            }
                        } else {
                            Context context = MainActivity.this;
                            CharSequence text = "Correos no coinciden";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    } else {
                        Context context = MainActivity.this;
                        CharSequence text = "Contraseñas no coinciden";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    }
                }

        });
    }
    private void AbrirCamara(){
        Intent camaraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(camaraIntent);
        startActivityForResult(camaraIntent,1000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && requestCode==1000){
            if(data!=null){
                bitmap= data.getExtras().getParcelable("data");
            }

        }
    }
    public static boolean valid(String v) {
        return v.matches("[a-z-A-Z- ]*");
    }
    private void abrirActivityDetalle(String nom, String contra, String email, String rol, Bitmap bitmap){
        Intent intent = new Intent(this, ActivityUsuario.class);
        Usuario usuario = new Usuario(nom,contra, email, rol, bitmap);
        intent.putExtra(ActivityUsuario.USUARIO_KEY, usuario);;
        startActivity(intent);
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}