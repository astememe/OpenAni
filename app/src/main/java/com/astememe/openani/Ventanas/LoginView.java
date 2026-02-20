package com.astememe.openani.Ventanas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.R;

public class LoginView extends AppCompatActivity {

    EditText login_usuario;
    EditText login_contrasenia;
    ConstraintLayout button_login;
    TextView button_register;
    TextView button_invitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login_usuario = findViewById(R.id.nombreusuario_login);
        login_contrasenia = findViewById(R.id.contrasena_login);
        button_login = findViewById(R.id.boton_login);
        button_invitado = findViewById(R.id.invitado);

        button_login.setOnClickListener(v -> login());
        button_register = findViewById(R.id.boton_registrarse);
        button_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginView.this, RegisterView.class);
            startActivity(intent);
        });

        button_invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_invitado();
            }
        });
    }

    private void login_invitado() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("invitado", true).apply();
        startActivity(new Intent(LoginView.this, MainAnime.class));
    }

    private void login(){
        String inputUsuario = login_usuario.getText().toString();
        String inputContrasenia = login_contrasenia.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        preferences.edit().putBoolean("invitado", false).apply();
        String usuarioGuardado = preferences.getString("nombre", null);
        String contrasenaGuardada  =  preferences.getString("contraseña",  null);

        if (inputUsuario.equals(usuarioGuardado) && inputContrasenia.equals(contrasenaGuardada)){
            Toast.makeText(this,"Sesión iniciada correctamente.",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginView.this, MainAnime.class));
            finish();
        } else if (inputUsuario.isEmpty() || inputContrasenia.isEmpty()) {
            mostrarErrorVacio();
        }
        else {
            mostrarError();
        }
    }
    private void mostrarError(){
        new AlertDialog.Builder(this)
                .setTitle("Error de login")
                .setMessage("No se ha encontrado el nombre de usuario "+login_usuario.getText().toString()+". Prueba introducir otro nombre de usuario o también puedes registrarte si no tienes una cuenta.")
                .setIcon(R.drawable.alert)
                .setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoginView.this, RegisterView.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Volver a intentarlo", null)
                .show();
    }
    private void mostrarErrorVacio(){
        new AlertDialog.Builder(this)
                .setTitle("Ingresa tus datos")
                .setMessage("Faltan datos por rellenar, por favor ingrésalos.")
                .setIcon(R.drawable.alert)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}