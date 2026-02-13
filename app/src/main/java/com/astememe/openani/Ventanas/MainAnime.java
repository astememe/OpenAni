package com.astememe.openani.Ventanas;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.R;

public class MainAnime extends AppCompatActivity {

    ConstraintLayout barra_lateral_icono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_anime);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    public static class SostenDeVistas extends RecyclerView.ViewHolder {

        TextView titulo_torrent, tamano_torrent, ultima_fecha_torrent, cantidad_seeders_torrent, cantidad_leechers_torrent, cantidad_likes_torrent, cantidad_dislikes_torrent, magner_boton_torrent;

        public SostenDeVistas(@NonNull View itemView) {
            super(itemView);
            titulo_torrent = itemView.findViewById(R.id.titulo_torrent);
            tamano_torrent = itemView.findViewById(R.id.tamano_torrent);
            ultima_fecha_torrent = itemView.findViewById(R.id.fecha_actualizacion_torrent);
            cantidad_seeders_torrent = itemView.findViewById(R.id.cantidad_seeders);
            cantidad_leechers_torrent = itemView.findViewById(R.id.cantidad_leechers);
            cantidad_likes_torrent = itemView.findViewById(R.id.cantidad_likes);
            cantidad_dislikes_torrent = itemView.findViewById(R.id.cantidad_dislikes);
            magner_boton_torrent = itemView.findViewById(R.id.magnet_boton);
        }
    }
}