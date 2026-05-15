package com.astememe.openani.Adaptador_Evento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.Django_Manager.Models.RoomModel;
import com.astememe.openani.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.SostenDeVistas_Sala> {

    List<RoomModel.RoomDetail> salas;
    Context context;
    public RoomAdapter(Context context, List<RoomModel.RoomDetail> salas) {
        this.context = context;
        this.salas = salas;
    }
    @NonNull
    @Override
    public SostenDeVistas_Sala onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_chat_user, parent, false);
        return new SostenDeVistas_Sala(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SostenDeVistas_Sala holder, int position) {
        RoomModel.RoomDetail salaActual = salas.get(position);

        holder.nombre_otro_usuario.setText(salaActual.getNombreOtroUsuario());
        holder.ultimo_mensaje_texto.setText(salaActual.getUltimoMensaje());
        holder.fecha_ultima_conexion.setText(salaActual.getFechaHora());
    }

    @Override
    public int getItemCount() {
        return salas.size();
    }

    public static class SostenDeVistas_Sala extends RecyclerView.ViewHolder {
        TextView nombre_otro_usuario, ultimo_mensaje_texto, fecha_ultima_conexion;
        public SostenDeVistas_Sala(@NonNull View itemView) {
            super(itemView);
            nombre_otro_usuario = itemView.findViewById(R.id.nombre_usuario_comentario);
            ultimo_mensaje_texto = itemView.findViewById(R.id.comentario_texto);
            fecha_ultima_conexion = itemView.findViewById(R.id.comentario_fecha_publicacion);
        }
    }
}