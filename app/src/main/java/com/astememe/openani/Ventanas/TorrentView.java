package com.astememe.openani.Ventanas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import io.woong.shapedimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TorrentView extends AppCompatActivity {

    SharedPreferences preferences;
    Context context = this;
    Bundle extras;
    String titulo;
    String tamano;
    String fecha;
    String seeders;
    String leechers;
    String categoria;
    String enlace;
    String fecha_string;
    TextView titulo_torrent;
    TextView tamano_torrent;
    TextView ultima_fecha_torrent;

    ConstraintLayout boton_descargar;
    ConstraintLayout flechaAtras;
    RecyclerView recyclerComentarios;
    String texto;
    String nombre;
    String imagen;
    ComentarioAdapter comentarioAdapter;
    List<ComentarioModel.ComentarioTorrent> listaComentarios = new ArrayList<>();

    CircleImageView anadir_comentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        nombre = preferences.getString("nombre", "");
        imagen = preferences.getString("imagen", "");
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

        recyclerComentarios = findViewById(R.id.ComentariosRecycler);
        comentarioAdapter = new ComentarioAdapter(this, listaComentarios);

        recyclerComentarios.setAdapter(comentarioAdapter);

        boton_descargar = findViewById(R.id.boton_descarga);

        titulo_torrent = findViewById(R.id.titulo_torrent_especificaciones);
        tamano_torrent = findViewById(R.id.tamano_torrent_especificaciones);
        ultima_fecha_torrent = findViewById(R.id.fecha_actualizacion_torrent_especificaciones);

        anadir_comentario = findViewById(R.id.anadir_comentario);

        titulo_torrent.setText(titulo);
        tamano_torrent.setText("Size:" + tamano);
        ultima_fecha_torrent.setText("Date: " + fecha);
        obtenerComentarios(titulo);

        anadir_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getBoolean("invitado", true)) {
                    Toast.makeText(getApplicationContext(), "You must Log In to comment", Toast.LENGTH_LONG).show();
                    return;
                }
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

    }
    private void obtenerComentarios(String nombre){
        DjangoClient.getComentario_Interface().getComentario(nombre).enqueue(new Callback<ComentarioModel>() {
            @Override
            public void onResponse(Call<ComentarioModel> call, Response<ComentarioModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaComentarios.clear();
                    listaComentarios.addAll(response.body().comentarioModellist);
                    comentarioAdapter.notifyDataSetChanged();
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
                SimpleDateFormat fecha_formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                fecha_string = fecha_formato.format(new Date());
                texto = input.getText().toString();
                ComentarioModel.ComentarioTorrent comentarioTorrent = new ComentarioModel.ComentarioTorrent(titulo, nombre, imagen, texto, fecha_string);
                DjangoClient.getComentario_Interface().postComentario(comentarioTorrent).enqueue(new Callback<ComentarioModel>() {
                    @Override
                    public void onResponse(Call<ComentarioModel> call, Response<ComentarioModel> response) {
                        if (response.isSuccessful()){
                            listaComentarios.add(comentarioTorrent);
                        } else {
                            Log.d("comentarioError", "Error al comentar");
                        }
                        comentarioAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ComentarioModel> call, Throwable t) {
                        Log.d("ERROR", "error al crear comentario");
                    }
                });
            }
        });
        builder.setNegativeButton("Cancelar",null);
        builder.show();

    }
}