package com.astememe.openani.Ventanas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.LoginModel;
import com.astememe.openani.Django_Manager.Models.TokenModel;
import com.astememe.openani.Django_Manager.Models.UserDataModel;
import com.astememe.openani.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginView extends AppCompatActivity {

    EditText login_usuario;
    EditText login_contrasenia;
    ConstraintLayout button_login;
    TextView button_register;
    TextView button_invitado;

    boolean token_recieved;


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

    private void login() {
        token_recieved = false;
        String inputUsuario = login_usuario.getText().toString();
        String inputContrasenia = login_contrasenia.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("invitado", false).apply();

        if (inputUsuario.isEmpty() || inputContrasenia.isEmpty()) {
            mostrarErrorVacio();
        } else {
            LoginModel login = new LoginModel(inputUsuario, inputContrasenia);

            DjangoClient.getLoginAPI_Interface().loginUser(login).enqueue(new Callback<TokenModel>() {

                @Override
                public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                    if (response.isSuccessful()) {
                        TokenModel tokenModel = response.body();
                        String token = tokenModel.getToken();
                        String refresh = tokenModel.getRefresh();
                        preferences.edit().putString("token", token).apply();
                        preferences.edit().putString("refresh", refresh).apply();
                        token_recieved = true;
                        Log.d("Token", token);

                        obtenerDatosPerfil(token);
                    }
                }

                @Override
                public void onFailure(Call<TokenModel> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    token_recieved = false;
                }
            });
        }
    }
    private void mostrarErrorVacio(){
        new AlertDialog.Builder(this)
                .setTitle("Ingresa tus datos")
                .setMessage("Faltan datos por rellenar, por favor ingrésalos.")
                .setIcon(R.drawable.alert)
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void obtenerDatosPerfil(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        DjangoClient.getUserAPI_Interface().getProfile("Bearer " + token).enqueue(new Callback<UserDataModel.UserData>() {
            @Override
            public void onResponse(Call<UserDataModel.UserData> call, Response<UserDataModel.UserData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDataModel.UserData userData = response.body();

                    preferences.edit()
                            .putString("nombre", userData.getUsername())
                            .putString("email", userData.getEmail())
                            .putString("imagen", userData.getImagen())
                            .putString("descripcion", userData.getDescripcion())
                            .apply();

                    Log.d("Perfil", userData.getUsername());
                    Log.d("Correo", userData.getEmail());
                    Log.d("Imagen", userData.getImagen());
                    Log.d("Descripcion", userData.getDescripcion());

                    startActivity(new Intent(LoginView.this, MainAnime.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserDataModel.UserData> call, Throwable t) {
                Log.e("Error Perfil", t.getMessage());
            }
        });
    }
}