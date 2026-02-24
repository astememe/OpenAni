    package com.astememe.openani.Ventanas;

    import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.os.Handler;
    import android.util.Log;
    import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.UserDataModel;
import com.astememe.openani.Django_Manager.Models.UserUpdateModel;
import com.astememe.openani.R;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

import io.woong.shapedimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;


public class AccountView extends AppCompatActivity {


    //https://www.geeksforgeeks.org/android/how-to-select-an-image-from-gallery-in-android/
    //https://www.reddit.com/r/learnprogramming/comments/q5xelq/should_i_convert_an_image_uri_to_a_blobbytearray/

    ConstraintLayout botonAtras;
    EditText descripcion, nombreUsuario, correo, contrasena;
    CircleImageView fotoPerfil;

    View menuCambiarFoto;

    ImageView cerrarMenu;
    LinearLayout primeraFoto, segundaFoto, terceraFoto, cuartaFoto, quintaFoto, sextaFoto, septimaFoto, octavaFoto, novenaFoto, decimaFoto, decimoPrimeraFoto, decimoSegundaFoto, decimoTerceraFoto, decimoCuartaFoto, decimoQuintaFoto;
    View botonCambiarFoto;
    LayoutInflater infladorDeCambiarFoto;
    LinearLayout layoutInflateAcountViewReference;
    ConstraintLayout botonGuardar;

    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fotoPerfil = findViewById(R.id.fotoperfil);
        descripcion = findViewById(R.id.descripcion);

        nombreUsuario = findViewById(R.id.nombreusuario_account);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        botonGuardar = findViewById(R.id.guardarcambios_account);

        nombreUsuario.setText(preferences.getString("nombre", ""));
        correo.setText(preferences.getString("email", ""));

        layoutInflateAcountViewReference = findViewById(R.id.contenedorMenuFotoPerfil);
        botonAtras = findViewById(R.id.flechaAtrasAcountView);
        descripcion.setText(preferences.getString("descripcion", "Escriba su descripción aquí"));
        botonCambiarFoto = findViewById(R.id.cambiarfotoperfil);
        infladorDeCambiarFoto = LayoutInflater.from(this);

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountView.this, MainAnime.class);
                startActivity(intent);
            }
        });

        Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/drawable/foto_de_perfil_" + preferences.getString("imagen", ""));
        fotoPerfil.setImageURI(uri);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nombreUsuario.getText().toString();
                String email = correo.getText().toString();
                String password = contrasena.getText().toString();
                String descripcion = AccountView.this.descripcion.getText().toString();
                String imagen = preferences.getString("imagen", "");

                if (password.isEmpty()){
                    password = null;
                }

                UserUpdateModel userUpdateModel = new UserUpdateModel(username, email, password, imagen, descripcion);
                String token = "Bearer " + preferences.getString("token", "");

                guardarCambios();

            }
        });

        botonCambiarFoto.setOnClickListener(v -> {
            layoutInflateAcountViewReference.removeAllViews();
            botonCambiarFoto = infladorDeCambiarFoto.inflate(R.layout.change_photo_of_profile, layoutInflateAcountViewReference, true);
            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            botonCambiarFoto.setAnimation(slide_in);
            List<LinearLayout> fotosDePerfil = new ArrayList<>(Arrays.asList(
                    primeraFoto = botonCambiarFoto.findViewById(R.id.contenedorKaneki),
                    segundaFoto = botonCambiarFoto.findViewById(R.id.contenedorJotaro),
                    terceraFoto = botonCambiarFoto.findViewById(R.id.contenedorJinWoo),
                    cuartaFoto = botonCambiarFoto.findViewById(R.id.contenedorGoku),
                    quintaFoto = botonCambiarFoto.findViewById(R.id.contenedorGohan),
                    sextaFoto = botonCambiarFoto.findViewById(R.id.contenedorNicoRobin),
                    septimaFoto = botonCambiarFoto.findViewById(R.id.contenedorToga),
                    octavaFoto = botonCambiarFoto.findViewById(R.id.contenedorKakashi),
                    novenaFoto = botonCambiarFoto.findViewById(R.id.contenedorNaruto),
                    decimaFoto = botonCambiarFoto.findViewById(R.id.contenedorPersonajeTR),
                    decimoPrimeraFoto = botonCambiarFoto.findViewById(R.id.contenedorSasuke),
                    decimoSegundaFoto = botonCambiarFoto.findViewById(R.id.contenedorGojo),
                    decimoTerceraFoto = botonCambiarFoto.findViewById(R.id.contenedorMelodias),
                    decimoCuartaFoto = botonCambiarFoto.findViewById(R.id.contenedorMikasa),
                    decimoQuintaFoto = botonCambiarFoto.findViewById(R.id.contenedorLuffy)
            ));
            for (LinearLayout foto: fotosDePerfil) {
                foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView nombreFoto = v.findViewById(R.id.idImagen);
                        String valor = nombreFoto.getText().toString();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putString("imagen", valor).apply();
                        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/foto_de_perfil_" + valor);
                        fotoPerfil.setImageDrawable(null);
                        fotoPerfil.setImageURI(uri);
                        desplazarMenu(slide_out);
                    }
                });
            }
        });
    }

    public void desplazarMenu(Animation slide_out) {
        botonCambiarFoto.setAnimation(slide_out);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutInflateAcountViewReference.removeAllViews();
            }
        }, slide_out.getDuration());
    }

    public void guardarCambios(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String token = "Bearer " + prefs.getString("token", "");


        String nuevoNombre = nombreUsuario.getText().toString().trim();
        String nuevoCorreo = correo.getText().toString().trim();
        String nuevaContrasena = contrasena.getText().toString().trim();
        String nuevaDescripcion = descripcion.getText().toString().trim();
        String nuevaImagen = prefs.getString("imagen", "");

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", nuevoNombre);
        editor.putString("email", nuevoCorreo);
        editor.putString("descripcion", nuevaDescripcion);
        editor.putString("imagen", nuevaImagen);

        UserUpdateModel userUpdateModel = new UserUpdateModel(nuevoNombre, nuevoCorreo, nuevaContrasena, nuevaImagen, nuevaDescripcion);

        DjangoClient.getUserAPI_Interface().updateProfile(token, userUpdateModel).enqueue(new retrofit2.Callback<UserDataModel>() {

            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                editor.apply();
                if (response.isSuccessful()) {
                    Toast.makeText(AccountView.this, "¡Cambios guardados con éxito!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject errores = new JSONObject(response.errorBody().string());
                        if (errores.has("password")) {
                            contrasena.setError(errores.getJSONArray("password").getString(0));
                        }
                        if (errores.has("email")) {
                            correo.setError(errores.getJSONArray("email").getString(0));
                        }
                        if (errores.has("username")) {
                            nombreUsuario.setError(errores.getJSONArray("username").getString(0));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                Toast.makeText(AccountView.this, "Error: El usuario ya existe", Toast.LENGTH_LONG).show();
            }
        });

    }

}