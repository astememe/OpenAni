package com.astememe.openani.Adaptador_Evento;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.API_Manager.DataModel;
import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.TorrentsModel;
import com.astememe.openani.R;
import com.astememe.openani.Ventanas.TorrentView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TorrentAdapter extends RecyclerView.Adapter<TorrentAdapter.SostenDeVistas> {

    Context context;
    List<DataModel.Torrent> torrentList;

    public TorrentAdapter(Context context, List<DataModel.Torrent> torrentList){
        this.context = context;
        this.torrentList = torrentList;
    }

    @NonNull
    @Override
    public SostenDeVistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_card_torrent, parent, false);
        return new SostenDeVistas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SostenDeVistas holder, int position) {
        DataModel.Torrent torrent = torrentList.get(position);

        holder.titulo_torrent.setText(torrent.getTitulo());
        holder.magnet_boton_torrent.setText(torrent.getEnlace());
        holder.tamano_torrent.setText(torrent.getTamano());
        holder.ultima_fecha_torrent.setText(torrent.getFecha());
        holder.cantidad_seeders_torrent.setText(String.valueOf(torrent.getSeeders()));
        holder.cantidad_leechers_torrent.setText(String.valueOf(torrent.getLeechers()));
        holder.cantidad_likes_torrent.setText("0");
        holder.cantidad_dislikes_torrent.setText("0");

        holder.image_boton_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TorrentView.class);
                intent.putExtra("titulo", torrent.getTitulo());
                intent.putExtra("tamano", torrent.getTamano());
                intent.putExtra("fecha", torrent.getFecha());
                intent.putExtra("seeders", torrent.getSeeders());
                intent.putExtra("leechers", torrent.getLeechers());
                intent.putExtra("categoria", torrent.getCategoria());
                intent.putExtra("enlace", torrent.getEnlace());
                context.startActivity(intent);
            }
        });

        holder.titulo_torrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DjangoClient.getAPI_Interface().postTorrent(new TorrentsModel.TorrentBBDD(torrent.getTitulo(), torrent.getCategoria(), torrent.getEnlace(), torrent.getTamano(), torrent.getFecha(), String.valueOf(torrent.getSeeders()), String.valueOf(torrent.getLeechers()), "0", "0")).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Guardado en la nube", Toast.LENGTH_SHORT).show();
                        } else {
                            // Si Django devuelve error (ej: 400 Bad Request)
                            Log.e("POST_ERROR", "Error: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("POST_ERROR", "Fallo total: " + t.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return torrentList.size();
    }

    public static class SostenDeVistas extends RecyclerView.ViewHolder {
        TextView titulo_torrent, tamano_torrent, ultima_fecha_torrent, cantidad_seeders_torrent, cantidad_leechers_torrent, cantidad_likes_torrent, cantidad_dislikes_torrent, magnet_boton_torrent;
        ImageButton image_boton_download;
        public SostenDeVistas(@NonNull View itemView) {
            super(itemView);
            titulo_torrent = itemView.findViewById(R.id.titulo_torrent);
            tamano_torrent = itemView.findViewById(R.id.tamano_torrent);
            ultima_fecha_torrent = itemView.findViewById(R.id.fecha_actualizacion_torrent);
            cantidad_seeders_torrent = itemView.findViewById(R.id.cantidad_seeders);
            cantidad_leechers_torrent = itemView.findViewById(R.id.cantidad_leechers);
            cantidad_likes_torrent = itemView.findViewById(R.id.cantidad_likes);
            cantidad_dislikes_torrent = itemView.findViewById(R.id.cantidad_dislikes);
            magnet_boton_torrent = itemView.findViewById(R.id.magnet_boton);
            image_boton_download = itemView.findViewById(R.id.imagebutton_download);
        }
    }
}
