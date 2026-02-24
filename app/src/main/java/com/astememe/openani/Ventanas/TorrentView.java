package com.astememe.openani.Ventanas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.R;

public class TorrentView extends AppCompatActivity {


    Context context = this;
    Bundle extras;
    String titulo;
    String tamano;
    String fecha;
    String seeders;
    String leechers;
    String categoria;
    String enlace;

    TextView titulo_torrent;
    TextView tamano_torrent;
    TextView ultima_fecha_torrent;
    TextView categoria_torrent;

    ConstraintLayout boton_descargar;
    ConstraintLayout flechaAtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_torrent_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        extras = getIntent().getExtras();

        titulo = extras.getString("titulo");
        tamano = extras.getString("tamano");
        fecha = extras.getString("fecha");
        seeders = extras.getString("seeders");
        leechers = extras.getString("leechers");
        categoria = extras.getString("categoria");
        enlace = extras.getString("enlace");

        boton_descargar = findViewById(R.id.boton_descarga);

        titulo_torrent = findViewById(R.id.titulo_torrent_especificaciones);
        tamano_torrent = findViewById(R.id.tamano_torrent_especificaciones);
        ultima_fecha_torrent = findViewById(R.id.fecha_actualizacion_torrent_especificaciones);
        categoria_torrent = findViewById(R.id.categoria_especificaciones);

        titulo_torrent.setText(titulo);
        categoria_torrent.setText("Category: " + categoria);
        tamano_torrent.setText("Size:" + tamano);
        ultima_fecha_torrent.setText("Date: " + fecha);

        boton_descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(enlace.toString()));
                startActivity(intent);
            }
        });

        flechaAtras = findViewById(R.id.flecha_volver_title);
        flechaAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TorrentView.this, MainAnime.class);
                startActivity(intent);
            }
        });

//        DjangoClient.getComentario_Interface().getComentario()

    }
}