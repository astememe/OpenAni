package com.astememe.openani.Ventanas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        flechaAtras = findViewById(R.id.flecha_volver_title);
        flechaAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TorrentView.this, MainAnime.class);
                startActivity(intent);
            }
        });
    }
}