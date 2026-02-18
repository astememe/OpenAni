package com.astememe.openani.Ventanas;

import static android.view.LayoutInflater.from;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
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
    CircleImageView botonCambiarFoto;
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
        botonCambiarFoto = (CircleImageView) findViewById(R.id.cambiarfotoperfil);
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountView.this, MainAnime.class);
                startActivity(intent);
            }
        });
        infladorDeCambiarFoto = LayoutInflater
                .from(this);
        botonCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuCambiarFoto = infladorDeCambiarFoto.inflate(R.layout.change_photo_of_profile,layoutInflateAcountViewReference,true);
                Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                menuCambiarFoto.setAnimation(slide_in);
                cerrarMenu = menuCambiarFoto.findViewById(R.id.cerrar_menu_lateral);
            }
        });

        List<LinearLayout> fotosDePerfil = new ArrayList<>(Arrays.asList(
                primeraFoto = menuCambiarFoto.findViewById(R.id.contenedorKaneki),
                segundaFoto = menuCambiarFoto.findViewById(R.id.contenedorJotaro),
                terceraFoto = menuCambiarFoto.findViewById(R.id.contenedorJinWoo),
                cuartaFoto = menuCambiarFoto.findViewById(R.id.contenedorGoku),
                quintaFoto = menuCambiarFoto.findViewById(R.id.contenedorGohan),
                sextaFoto = menuCambiarFoto.findViewById(R.id.contenedorNicoRobin),
                septimaFoto = menuCambiarFoto.findViewById(R.id.contenedorToga),
                octavaFoto = menuCambiarFoto.findViewById(R.id.contenedorKakashi),
                novenaFoto = menuCambiarFoto.findViewById(R.id.contenedorNaruto),
                decimaFoto = menuCambiarFoto.findViewById(R.id.contenedorPersonajeTR),
                decimoPrimeraFoto = menuCambiarFoto.findViewById(R.id.contenedorSasuke),
                decimoSegundaFoto = menuCambiarFoto.findViewById(R.id.contenedorGojo),
                decimoTerceraFoto= menuCambiarFoto.findViewById(R.id.contenedorMelodias),
                decimoCuartaFoto= menuCambiarFoto.findViewById(R.id.contenedorMikasa),
                decimoQuintaFoto= menuCambiarFoto.findViewById(R.id.contenedorLuffy)
        ));

        for (LinearLayout foto: fotosDePerfil) {
            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPreferences.edit().putString("foto-perfil",String.valueOf(foto));
                }
            });

        }

    }
}