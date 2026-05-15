package com.astememe.openani.Adaptador_Evento;
import static androidx.core.content.ContextCompat.startActivity;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.Django_Manager.Models.ComentarioModel;
import com.astememe.openani.R;
import com.astememe.openani.Ventanas.AccountView;
import com.astememe.openani.Ventanas.OtherProfile;
import com.astememe.openani.Ventanas.TorrentView;

import java.time.LocalDate;
import java.util.List;

import io.woong.shapedimageview.CircleImageView;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.SostenDeVistas_C>{
    List<ComentarioModel.ComentarioTorrent> comentarios;
    SharedPreferences preferences;

    Context context;

    public ComentarioAdapter(Context context, List<ComentarioModel.ComentarioTorrent> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }

    @NonNull
    @Override
    public SostenDeVistas_C onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comentario, parent, false);
        return new SostenDeVistas_C(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SostenDeVistas_C holder, int position) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ComentarioModel.ComentarioTorrent comentarioModel = comentarios.get(position);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/foto_de_perfil_" + comentarios.get(position).imagen_usuario);


        holder.nombre_usuario_comentario.setText(comentarioModel.getNombre_usuario());
        holder.img_perfil.setImageURI(uri);
        holder.comentario_texto.setText(comentarioModel.getTexto_usuario());
        holder.comentario_fecha_publicacion.setText(comentarioModel.getFecha());

        holder.nombre_usuario_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comentarioModel.getNombre_usuario().equals(preferences.getString("nombre", ""))) {
                    Intent intent = new Intent(context, AccountView.class);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, OtherProfile.class);
                    intent.putExtra("other_username", comentarioModel.getNombre_usuario());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public static class SostenDeVistas_C extends RecyclerView.ViewHolder{
        CircleImageView img_perfil;
        TextView nombre_torrent, nombre_usuario_comentario, comentario_texto, comentario_fecha_publicacion;

        public SostenDeVistas_C(@NonNull View itemView){
            super(itemView);

            nombre_usuario_comentario = itemView.findViewById(R.id.nombre_usuario_comentario);
            img_perfil = itemView.findViewById(R.id.img_perfil);
            comentario_texto = itemView.findViewById(R.id.comentario_texto);
            comentario_fecha_publicacion = itemView.findViewById(R.id.comentario_fecha_publicacion);
        }
    }

}