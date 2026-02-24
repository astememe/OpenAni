package com.astememe.openani.Adaptador_Evento;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.Django_Manager.Models.ComentarioModel;
import com.astememe.openani.R;
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
        ComentarioModel.ComentarioTorrent comentarioModel = comentarios.get(position);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/foto_de_perfil_" + preferences.getString("imagen", ""));


        holder.nombre_usuario_comentario.setText(comentarioModel.getNombre_usuario());
        holder.img_perfil.setImageURI(uri);
        holder.comentario_texto.setText(comentarioModel.getTexto_usuario());
        holder.comentario_fecha_publicacion.setText(comentarioModel.getFecha());
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

            nombre_usuario_comentario = itemView.findViewById(R.id.nombre_usuario);
            img_perfil = itemView.findViewById(R.id.img_perfil);
            comentario_texto = itemView.findViewById(R.id.comentario_texto);
            comentario_fecha_publicacion = itemView.findViewById(R.id.comentario_fecha_publicacion);
        }
    }

}