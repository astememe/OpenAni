package com.astememe.openani.Ventanas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.Adaptador_Evento.ComentarioAdapter;
import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.ComentarioModel;
import com.astememe.openani.R;

import java.util.ArrayList;
import java.util.List;

import io.woong.shapedimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    RecyclerView recyclerComentarios;
    String texto;
    ComentarioAdapter comentarioAdapter;
    List<ComentarioModel.ComentarioTorrent> listaComentarios = new ArrayList<>();

    CircleImageView anadir_comentario;

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
        titulo_torrent = findViewById(R.id.titulo_torrent_especificaciones);
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

        anadir_comentario = findViewById(R.id.anadir_comentario);

        titulo_torrent.setText(titulo);
        categoria_torrent.setText("Category: " + categoria);
        tamano_torrent.setText("Size:" + tamano);
        ultima_fecha_torrent.setText("Date: " + fecha);

        anadir_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMensajeComent();
            }
        });

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

        recyclerComentarios = findViewById(R.id.ComentariosRecycler);
        recyclerComentarios.setLayoutManager(new LinearLayoutManager(this));

        titulo = getIntent().getStringExtra("titulo");
        obtenerComentarios(titulo);

    }
    private void obtenerComentarios(String nombre){
        DjangoClient.getComentario_Interface().getComentario(nombre).enqueue(new Callback<ComentarioModel>() {
            @Override
            public void onResponse(Call<ComentarioModel> call, Response<ComentarioModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ComentarioModel.ComentarioTorrent> lista = response.body().comentarioModellist;
                    comentarioAdapter = new ComentarioAdapter(TorrentView.this, lista);
                    recyclerComentarios.setAdapter(comentarioAdapter);
                }
            }

            @Override
            public void onFailure(Call<ComentarioModel> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }
    private void mostrarMensajeComent(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escribe tu comentario");

        final EditText input = new EditText(this);
        input.setHint("Escribe aquí");

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                texto = input.getText().toString();

            }
        });
        builder.setNegativeButton("Cancelar",null);
        builder.show();
        Log.d("texto",texto);
    }
}