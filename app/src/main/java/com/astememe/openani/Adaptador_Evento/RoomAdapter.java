package com.astememe.openani.Adaptador_Evento;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.Django_Manager.Models.RoomModel;
import com.astememe.openani.R;
import com.astememe.openani.Ventanas.Chat;

import java.util.List;

import io.woong.shapedimageview.CircleImageView;

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
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/foto_de_perfil_" + salaActual.getImagenOtroUsuario());
        holder.imagen_otro_usuario.setImageURI(uri);
        holder.ultimo_mensaje.setText(salaActual.getUltimoMensaje());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("ROOM_ID", Integer.toString(salaActual.getId()));
                intent.putExtra("other_username", salaActual.getNombreOtroUsuario());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salas.size();
    }

    public static class SostenDeVistas_Sala extends RecyclerView.ViewHolder {
        TextView nombre_otro_usuario, ultimo_mensaje_texto, fecha_ultima_conexion, ultimo_mensaje;
        CircleImageView imagen_otro_usuario;


        public SostenDeVistas_Sala(@NonNull View itemView) {
            super(itemView);
            nombre_otro_usuario = itemView.findViewById(R.id.nombre_usuario_room);
            imagen_otro_usuario = itemView.findViewById(R.id.img_perfil);
            ultimo_mensaje = itemView.findViewById(R.id.ultimo_mensaje);
        }
    }
}