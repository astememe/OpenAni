package com.astememe.openani;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Pattern;

public class activity_register_view extends AppCompatActivity {

    EditText usuarioRegister;
    EditText emailRegister;
    EditText passwordRegister;
    EditText ConfirmPasswordRegister;
    ConstraintLayout botonRegistrarse;



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

        usuarioRegister = findViewById(R.id.nombreusuario_register);
        emailRegister = findViewById(R.id.correo_register);
        passwordRegister  = findViewById(R.id.contrasena_register);
        ConfirmPasswordRegister = findViewById(R.id.confirmar_contrasena_register);
        botonRegistrarse = findViewById(R.id.buttonRegistrarse);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        botonRegistrarse.setOnClickListener(v -> {
            boolean comprobobar_nombre = emptyVerify(usuarioRegister);
            boolean comprobar_email =
        });
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
    public boolean emailVerify(EditText EditText){
        String email = EditText.getText().toString();
        String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if(email.isEmpty()){
            Toast.makeText(this,"Falta rellenar el email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return Pattern.matches(EMAIL_REGEX,email);
    }

}