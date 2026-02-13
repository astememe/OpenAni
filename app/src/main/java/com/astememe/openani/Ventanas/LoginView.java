package com.astememe.openani.Ventanas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

        button_login.setOnClickListener(v -> login());
        button_register = findViewById(R.id.boton_registrarse);
        button_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginView.this, RegisterView.class);
            startActivity(intent);
        });
    }

    private void login(){
        String inputUsuario = login_usuario.getText().toString();
        String inputContrasenia = login_contrasenia.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usuarioGuardado = preferences.getString("nombre", null);
        String contrasenaGuardada  =  preferences.getString("contraseña",  null);

        if (usuarioGuardado == null){
            Toast.makeText(this,"No hay usuarios registrados, crea una cuenta",Toast.LENGTH_SHORT).show();
            return;
        }

        if (inputUsuario.equals(usuarioGuardado) && inputContrasenia.equals(contrasenaGuardada)){
            Toast.makeText(this,"Sesión iniciada correctamente.",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginView.this, MainAnime.class));
            finish();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}