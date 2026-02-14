package com.astememe.openani.Adaptador_Evento;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.API_Manager.Data;
import com.astememe.openani.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TorrentAdapter extends RecyclerView.Adapter<TorrentAdapter.SostenDeVistas> {
    Context context;
    ConstraintLayout barra_lateral_icono;

    List<Data.Torrent> torrentList;

    public TorrentAdapter(Context context, List<Data.Torrent> torrentList){
        this.context = context;
        this.torrentList = torrentList;
    }

    @NonNull
    @Override
    public TorrentAdapter.SostenDeVistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.anime_card_torrent, parent, false);
        SostenDeVistas holder = new TorrentAdapter.SostenDeVistas(view);
        return new SostenDeVistas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TorrentAdapter.SostenDeVistas holder, int position) {
        Data.Torrent torrent =torrentList.get(position);

        holder.titulo_torrent.setText(torrent.titulo);
        holder.magnet_boton_torrent.setText(torrent.enlace);
        holder.tamano_torrent.setText(torrent.tamano);
        holder.ultima_fecha_torrent.setText(torrent.fecha);
        holder.cantidad_seeders_torrent.setText(torrent.seeders);
        holder.cantidad_leechers_torrent.setText(torrent.leechers);
    }

    @Override
    public int getItemCount() {
        return torrentList.size();
    }

    public static class SostenDeVistas extends RecyclerView.ViewHolder {
        TextView titulo_torrent, tamano_torrent, ultima_fecha_torrent, cantidad_seeders_torrent, cantidad_leechers_torrent, cantidad_likes_torrent, cantidad_dislikes_torrent, magnet_boton_torrent;
    //    ImageButton image_boton_download;
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
    //        image_boton_download = itemView.findViewById(R.id.imagebutton_download);
        }
//        public class SostenDeVistas extends RecyclerView.ViewHolder {
//            RecyclerView recyclerView =findViewById(R.id.torrentRecycle);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//            adapter = new TorrentAdapter(this, torrentsModel);
//        recyclerView.setAdapter(adapter);
//            TextView titulo_torrent, tamano_torrent, ultima_fecha_torrent, cantidad_seeders_torrent, cantidad_leechers_torrent, cantidad_likes_torrent, cantidad_dislikes_torrent, magnet_boton_torrent;
//
//            Call<Data> call = apiInterface.getRecent(null);
//        call.enqueue(new Callback<Data>() {
//                @Override
//                public void onResponse(Call<Data> call, Response<Data> response) {
//                    if (response.isSuccessful() && response.body().torrents != null) {
//                        torrentsModel.clear();
//                        if (response.body().torrents != null) {
//                            torrentsModel.addAll(response.body().torrents);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//                @Override
//                public void onFailure(Call<Data> call, Throwable t) {
//                    Log.d("AAAAAAAAAAAAAAAAa", "AAAAAAAAAAAAAAAAa");
//                }
//            });
//        }
    }
}
