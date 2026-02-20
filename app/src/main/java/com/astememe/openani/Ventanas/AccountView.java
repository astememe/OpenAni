package com.astememe.openani.Ventanas;

import static android.view.LayoutInflater.from;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.woong.shapedimageview.CircleImageView;

public class AccountView extends AppCompatActivity {


    //https://www.geeksforgeeks.org/android/how-to-select-an-image-from-gallery-in-android/
    //https://www.reddit.com/r/learnprogramming/comments/q5xelq/should_i_convert_an_image_uri_to_a_blobbytearray/

    ConstraintLayout botonAtras;
    View menuCambiarFoto;

    ImageView cerrarMenu;
    LinearLayout primeraFoto, segundaFoto, terceraFoto, cuartaFoto, quintaFoto, sextaFoto, septimaFoto, octavaFoto, novenaFoto, decimaFoto, decimoPrimeraFoto, decimoSegundaFoto, decimoTerceraFoto, decimoCuartaFoto, decimoQuintaFoto;
    View botonCambiarFoto;
    LayoutInflater infladorDeCambiarFoto;
    LinearLayout layoutInflateAcountViewReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layoutInflateAcountViewReference = findViewById(R.id.contenedorMenuFotoPerfil);
        botonAtras = findViewById(R.id.flechaAtrasAcountView);
        botonCambiarFoto = findViewById(R.id.cambiarfotoperfil);

        infladorDeCambiarFoto = LayoutInflater.from(this);
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
                        sharedPreferences.edit().putString("foto_perfil", valor).apply();
                        desplazarMenu(slide_out);
                    }
                });
            };
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

    }