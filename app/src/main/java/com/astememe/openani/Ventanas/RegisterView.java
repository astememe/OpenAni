package com.astememe.openani.Ventanas;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.UserDataModel;
import com.astememe.openani.Django_Manager.Models.RegisterModel;
import com.astememe.openani.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import io.woong.shapedimageview.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterView extends AppCompatActivity {

    EditText usuarioRegister;
    EditText emailRegister;
    EditText passwordRegister;
    EditText confirmPasswordRegister;
    TextView botonRegistrarse;
    TextView tengoCuenta;
    View cambiarFoto;
    LinearLayout primeraFoto, segundaFoto, terceraFoto, cuartaFoto, quintaFoto, sextaFoto, septimaFoto, octavaFoto, novenaFoto, decimaFoto, decimoPrimeraFoto, decimoSegundaFoto, decimoTerceraFoto, decimoCuartaFoto, decimoQuintaFoto;
    CircleImageView botonCambiarFoto;
    LayoutInflater infladorDeCambiarFoto;
    LinearLayout layoutInflateRegisterViewReference;

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
        layoutInflateRegisterViewReference = findViewById(R.id.contenedorMenuFotoPerfilRegister);
        usuarioRegister = findViewById(R.id.nombreusuario_registro);
        emailRegister = findViewById(R.id.correo_registro);
        passwordRegister  = findViewById(R.id.contrasena_registro);
        confirmPasswordRegister = findViewById(R.id.confirmar_contrasena_registro);
        botonRegistrarse = findViewById(R.id.guardarcambios_register_TV);
        tengoCuenta = findViewById(R.id.iniciarsesion_TV);
        botonCambiarFoto = findViewById(R.id.cambiarfotoperfil_registro);

        botonRegistrarse.setOnClickListener(v -> {
            boolean comprobar_nombre = emptyVerify(usuarioRegister);

            boolean comprobar_email = emailVerify(emailRegister);
            boolean comprobar_contras = comprobarContrasenias(passwordRegister,confirmPasswordRegister);

            boolean continuar = comprobar_nombre && comprobar_email && comprobar_contras;

            if (continuar){
                registerUser();
                Intent intent = new Intent(RegisterView.this, LoginView.class);
                startActivity(intent);
            }
        });
        tengoCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterView.this,MainAnime.class);
            startActivity(intent);
        });
        infladorDeCambiarFoto = LayoutInflater.from(this);
        botonCambiarFoto.setOnClickListener(v ->{
            layoutInflateRegisterViewReference.removeAllViews();
            cambiarFoto = infladorDeCambiarFoto.inflate(R.layout.change_photo_of_profile,layoutInflateRegisterViewReference, true);
            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            cambiarFoto.setAnimation(slide_in);


            List<LinearLayout> fotosDePerfil = new ArrayList<>(Arrays.asList(
                    primeraFoto = cambiarFoto.findViewById(R.id.contenedorKaneki),
                    segundaFoto = cambiarFoto.findViewById(R.id.contenedorJotaro),
                    terceraFoto = cambiarFoto.findViewById(R.id.contenedorJinWoo),
                    cuartaFoto = cambiarFoto.findViewById(R.id.contenedorGoku),
                    quintaFoto = cambiarFoto.findViewById(R.id.contenedorGohan),
                    sextaFoto = cambiarFoto.findViewById(R.id.contenedorNicoRobin),
                    septimaFoto = cambiarFoto.findViewById(R.id.contenedorToga),
                    octavaFoto = cambiarFoto.findViewById(R.id.contenedorKakashi),
                    novenaFoto = cambiarFoto.findViewById(R.id.contenedorNaruto),
                    decimaFoto = cambiarFoto.findViewById(R.id.contenedorPersonajeTR),
                    decimoPrimeraFoto = cambiarFoto.findViewById(R.id.contenedorSasuke),
                    decimoSegundaFoto = cambiarFoto.findViewById(R.id.contenedorGojo),
                    decimoTerceraFoto= cambiarFoto.findViewById(R.id.contenedorMelodias),
                    decimoCuartaFoto= cambiarFoto.findViewById(R.id.contenedorMikasa),
                    decimoQuintaFoto= cambiarFoto.findViewById(R.id.contenedorLuffy)
            ));
            for (LinearLayout foto: fotosDePerfil) {
                foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView nombreFoto = v.findViewById(R.id.idImagen);
                        String valor = nombreFoto.getText().toString();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putString("foto_perfil", valor).apply();
                        Log.d("id", valor);
                        desplazarMenu(slide_out);
                    }
                });

            };
        });

        }
    public void desplazarMenu(Animation slide_out) {
        cambiarFoto.setAnimation(slide_out);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutInflateRegisterViewReference.removeAllViews();
            }
        }, slide_out.getDuration());
    }

    private String getValue(EditText et){
        return et.getText().toString();
    }
    public boolean emptyVerify(EditText EditText){
        String text  = EditText.getText().toString();
        if(text.isEmpty()){
            Toast.makeText(this,"Error, falta(n) rellenar campos ", Toast.LENGTH_SHORT).show();
            EditText.setError("Por favor, introduzca su usuario");
            return false;
        } else {
            return true;
        }
    }

    public boolean emailVerify(EditText emailEditText){
        String email = emailEditText.getText().toString();
        String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if(email.isEmpty()){
            emailEditText.setError("Por favor, introduzca su email");
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

        if (firstContra.isEmpty() && secondContra.isEmpty()) {
            Toast.makeText(this,"Este campo no debe estar vacío", Toast.LENGTH_SHORT).show();
            contra1.setError("Por favor, introduzca su contraseña");
            contra2.setError("Por favor, introduzca su contraseña nuevamente");

            return false;
        }
        if (!firstContra.matches("^(?=.*[0-9])(?=.*[a-z]).{6,}$")) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres y un número", Toast.LENGTH_LONG).show();
            contra1.setError("Las contraseñas deben tener, Mín 1 número y 6 caracteres");
            contra2.setError("Las contraseñas deben tener, Mín 1 número y 6 caracteres");
            return false;
        }
        if (!firstContra.equals(secondContra)) {
            Toast.makeText(this,"Las contraseñas no coinciden. Por favor, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
            contra1.setError("Las contraseñas deben coincidir");
            contra2.setError("Las contraseñas deben coincidir");
            return false;
        }
        return true;

    }

    public void registerUser() {
        String inputUsuario = usuarioRegister.getText().toString();
        String inputEmail = emailRegister.getText().toString();
        String inputContrasenia = passwordRegister.getText().toString();
        String inputConfirmarContrasenia = confirmPasswordRegister.getText().toString();

        RegisterModel.UserRegister userRegister = new RegisterModel.UserRegister(inputUsuario, inputEmail, "naruto", inputContrasenia, inputConfirmarContrasenia);

        DjangoClient.getRegisterAPI_Interface().register(userRegister).enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if (response.isSuccessful()) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterView.this);

                    UserDataModel userDataModel = response.body();
                    UserDataModel.UserData userData = userDataModel.getUser();
                    String username = userData.getUsername();
                    String email = userData.getEmail();
                    String imagen = userData.getImagen();

                    preferences.edit().putString("nombre", username).apply();
                    preferences.edit().putString("email", email).apply();
                    preferences.edit().putString("imagen", imagen).apply();
                    preferences.edit().putString("access", userDataModel.getAccess()).apply();
                    preferences.edit().putString("refresh", userDataModel.getRefresh()).apply();
                    preferences.edit().putBoolean("invitado", false).apply();

                    Toast.makeText(RegisterView.this, "Registro exitoso", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

}