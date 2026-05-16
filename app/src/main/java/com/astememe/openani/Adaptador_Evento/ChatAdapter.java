package com.astememe.openani.Adaptador_Evento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.Django_Manager.Models.MessageModel.MessageDetail;
import com.astememe.openani.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int ENVIADO = 1;
    int RECIBIDO = 0;

    List<MessageDetail> listaMensajes;
    String miUsuario;
    Context context;

    public ChatAdapter(Context context, List<MessageDetail> listaMensajes, String miUsuario) {
        this.listaMensajes = listaMensajes;
        this.miUsuario = miUsuario;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        MessageDetail mensaje = listaMensajes.get(position);
        if (mensaje.getEmisor().equals(miUsuario)) {
            return ENVIADO;
        } else {
            return RECIBIDO;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false); // Asegúrate de que este sea el nombre real de tu XML enviado
            return new EnviadoViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received, parent, false); // Asegúrate de que este sea el nombre real de tu XML recibido
            return new RecibidoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageDetail mensaje = listaMensajes.get(position);

        if (holder instanceof EnviadoViewHolder) {
            ((EnviadoViewHolder) holder).txtMensajeEnviado.setText(mensaje.getContenido());
        } else if (holder instanceof RecibidoViewHolder) {
            ((RecibidoViewHolder) holder).txtMensajeRecibido.setText(mensaje.getContenido());
        }
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    static class EnviadoViewHolder extends RecyclerView.ViewHolder {
        TextView txtMensajeEnviado;
        public EnviadoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMensajeEnviado = itemView.findViewById(R.id.message_sent_text);
        }
    }

    static class RecibidoViewHolder extends RecyclerView.ViewHolder {
        TextView txtMensajeRecibido;
        public RecibidoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMensajeRecibido = itemView.findViewById(R.id.message_recieved_text);
        }
    }
}