package com.astememe.openani.Ventanas;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.R;

public class RecuperarContrasenaActivity extends AppCompatActivity {

    EditText correo_recuperar, codigo_recuperar, password_recuperar;
    CardView aceptar_recuperar, generar_codigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_contrasena);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correo_recuperar = findViewById(R.id.correo_recuperar);
        codigo_recuperar = findViewById(R.id.codigo_recuperar);
        password_recuperar = findViewById(R.id.password_recuperar);
        aceptar_recuperar = findViewById(R.id.aceptar_recuperar);
        generar_codigo = findViewById(R.id.generar_code);

        aceptar_recuperar.setVisibility(INVISIBLE);

        generar_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptar_recuperar.setVisibility(VISIBLE);

//                DjangoClient.getResetPasswordAPI_Interface().requestReset()
            }
        });




    }
}