package com.astememe.openani.Ventanas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllChatsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ComentarioAdapter adapter;
    List<ComentarioModel.ComentarioTorrent> listaComentarios = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_chats);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerViewChats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ComentarioAdapter(this, listaComentarios);
        recyclerView.setAdapter(adapter);
        cargarComentarios();
    }

    private void cargarComentarios() {
        DjangoClient.getAPI_Interface().getComentarios().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ComentarioModel> call, Response<ComentarioModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaComentarios.clear();
                    if (response.body().comentarioModellist.isEmpty()) {
                        Log.d("DEBUG", "La lista llegó vacía desde el servidor");
                    } else {
                        listaComentarios.addAll(response.body().comentarioModellist);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("DEBUG", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ComentarioModel> call, Throwable t) {
                Toast.makeText(AllChatsActivity.this, "Error al cargar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}