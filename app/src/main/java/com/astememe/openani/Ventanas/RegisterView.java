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

import java.util.regex.Pattern;

public class RegisterView extends AppCompatActivity {

    EditText usuarioRegister;
    EditText emailRegister;
    EditText passwordRegister;
    EditText confirmPasswordRegister;
    TextView botonRegistrarse;
    TextView tengoCuenta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuarioRegister = findViewById(R.id.nombreusuario_registro);
        emailRegister = findViewById(R.id.correo_registro);
        passwordRegister  = findViewById(R.id.contrasena_registro);
        confirmPasswordRegister = findViewById(R.id.confirmar_contrasena_registro);
        botonRegistrarse = findViewById(R.id.guardarcambios_register_TV);
        tengoCuenta = findViewById(R.id.iniciarsesion_TV);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        botonRegistrarse.setOnClickListener(v -> {
            boolean comprobar_nombre = emptyVerify(usuarioRegister);
            boolean comprobar_email = emailVerify(emailRegister);
            boolean comprobar_contras = comprobarContrasenias(passwordRegister,confirmPasswordRegister);

            boolean continuar = comprobar_nombre && comprobar_email && comprobar_contras;

            if (continuar){
                editor.putString("nombre", getValue(usuarioRegister));
                editor.putString("email", getValue(emailRegister));
                editor.putString("contraseña", getValue(passwordRegister));
                editor.apply();

                Intent intent = new Intent(RegisterView.this, LoginView.class);
                startActivity(intent);
            }
        });
        tengoCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterView.this,LoginView.class);
            startActivity(intent);
        });
    }

    private String getValue(EditText et){
        return et.getText().toString();
    }
    public boolean emptyVerify(EditText EditText){
        String text  = EditText.getText().toString();
        if(text.isEmpty()){
            Toast.makeText(this,"Error, falta(n) rellenar campos ", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    public boolean emailVerify(EditText emailEditText){
        String email = emailEditText.getText().toString();
        String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if(email.isEmpty()){
            emailEditText.setError("Falta rellenar el email");
            return false;
        }
        if(!Pattern.matches(EMAIL_REGEX, email)) {
            emailEditText.setError("Email no válido");
            return false;
        }
        return true;
    }
    public boolean comprobarContrasenias(EditText contra1, EditText contra2){
        String firstContra = contra1.getText().toString();
        String secondContra =  contra2.getText().toString();

        if (firstContra.isEmpty()) {
            Toast.makeText(this,"Este campo no debe estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!firstContra.equals(secondContra)) {
            Toast.makeText(this,"Las contraseñas no coinciden. Por favor, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}