package com.astememe.openani.Ventanas;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.API_Manager.API_Client;
import com.astememe.openani.API_Manager.API_Interface;
import com.astememe.openani.API_Manager.Data;
import com.astememe.openani.R;
import com.astememe.openani.Ventanas.Adaptador_Evento.TorrentAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAnime extends AppCompatActivity {

    TorrentAdapter adapter;

    ArrayList<Data.Torrent> torrentsModel = new ArrayList<Data.Torrent>();

    API_Interface apiInterface = API_Client.getClient().create(API_Interface.class);

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
        RecyclerView recyclerView =findViewById(R.id.torrentRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TorrentAdapter(this, torrentsModel);
        recyclerView.setAdapter(adapter);

        Call<Data> call = apiInterface.getRecent(null);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful() && response.body().torrents != null) {
                    torrentsModel.clear();
                    if (response.body().torrents != null) {
                        torrentsModel.addAll(response.body().torrents);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("AAAAAAAAAAAAAAAAa", "AAAAAAAAAAAAAAAAa");
            }
        });
    }

}