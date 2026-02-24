package com.astememe.openani.Adaptador_Evento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.API_Manager.DataModel;
import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.FavoriteModel;
import com.astememe.openani.Django_Manager.Models.TorrentsModel;
import com.astememe.openani.R;
import com.astememe.openani.Ventanas.FavoriteView;
import com.astememe.openani.Ventanas.TorrentView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class TorrentAdapter extends RecyclerView.Adapter<TorrentAdapter.SostenDeVistas> {

    Context context;
    List<DataModel.Torrent> torrentList;
    SharedPreferences preferences;
    String token;
    private List<String> listaFavoritos = new ArrayList<>();

    public TorrentAdapter(Context context, List<DataModel.Torrent> torrentList){
        this.context = context;
        this.torrentList = torrentList;
    }

    public void setFavoritos(List<String> magnets) {
        this.listaFavoritos = magnets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SostenDeVistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_card_torrent, parent, false);
        return new SostenDeVistas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SostenDeVistas holder, int position) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        DataModel.Torrent torrent = torrentList.get(position);

        holder.titulo_torrent.setText(torrent.getTitulo());
        holder.magnet_boton_torrent.setText(torrent.getEnlace());
        holder.tamano_torrent.setText(torrent.getTamano());
        holder.ultima_fecha_torrent.setText(torrent.getFecha());
        holder.cantidad_seeders_torrent.setText(String.valueOf(torrent.getSeeders()));
        holder.cantidad_leechers_torrent.setText(String.valueOf(torrent.getLeechers()));

        if (listaFavoritos.contains(torrent.getEnlace())) {
            holder.estrella_favorita_icono.setImageResource(R.drawable.estrella_favorito_rellena);
        } else {
            holder.estrella_favorita_icono.setImageResource(R.drawable.estrella_favorito_icono);
        }

        holder.image_boton_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TorrentView.class);
                intent.putExtra("titulo", torrent.getTitulo());
                intent.putExtra("tamano", torrent.getTamano());
                intent.putExtra("fecha", torrent.getFecha());
                intent.putExtra("seeders", torrent.getSeeders());
                intent.putExtra("leechers", torrent.getLeechers());
                intent.putExtra("enlace", torrent.getEnlace());
                context.startActivity(intent);
            }
        });

        holder.estrella_favorita_icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getBoolean("invitado", false)) {
                    FavoriteModel.FavoriteTorrentModel favoriteTorrentModel = new FavoriteModel.FavoriteTorrentModel(preferences.getString("nombre", "Invitado"), torrent.getTitulo(), torrent.getEnlace(), torrent.getTamano(), torrent.getFecha(), Integer.toString(torrent.getSeeders()), Integer.toString(torrent.getLeechers()));
                    token = "Bearer " + preferences.getString("token", "");
                    DjangoClient.getTorrentsAPI_Interface().postFavorite(token, favoriteTorrentModel).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("Respuesta", "Torrent agregado a favoritos");
                                holder.estrella_favorita_icono.setImageResource(R.drawable.estrella_favorito_rellena);
                                System.out.println(holder.estrella_favorita_icono.getDrawable());

                                listaFavoritos.add(torrent.getEnlace());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("Error", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(context, "Debes iniciar sesión para agregar un torrent a favoritos", Toast.LENGTH_SHORT).show();
                }
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
        LinearLayout contenedor_estrella_favorita;
        ImageView estrella_favorita_icono;

        public SostenDeVistas(@NonNull View itemView) {
            super(itemView);
            titulo_torrent = itemView.findViewById(R.id.titulo_torrent);
            tamano_torrent = itemView.findViewById(R.id.tamano_torrent);
            ultima_fecha_torrent = itemView.findViewById(R.id.fecha_actualizacion_torrent);
            cantidad_seeders_torrent = itemView.findViewById(R.id.cantidad_seeders);
            cantidad_leechers_torrent = itemView.findViewById(R.id.cantidad_leechers);

            magnet_boton_torrent = itemView.findViewById(R.id.magnet_boton);
            image_boton_download = itemView.findViewById(R.id.imagebutton_download);

            contenedor_estrella_favorita = itemView.findViewById(R.id.contenedor_estrella_favorita);
            estrella_favorita_icono = itemView.findViewById(R.id.estrella_favorita_icono);
        }
    }
}