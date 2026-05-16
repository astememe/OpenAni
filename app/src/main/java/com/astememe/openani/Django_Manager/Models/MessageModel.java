package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MessageModel {
    @SerializedName("messages")
    private List<MessageDetail> mensajes;

    public List<MessageDetail> getMensajes() {
        return mensajes;
    }

    public static class MessageDetail {

        @SerializedName("sender_username")
        private String emisor;

        @SerializedName("text")
        private String contenido;

        @SerializedName("timestamp")
        private String fechaHora;

        public MessageDetail(String emisor, String contenido, String fechaHora) {
            this.emisor = emisor;
            this.contenido = contenido;
            this.fechaHora = fechaHora;
        }

        public String getEmisor() { return emisor; }
        public String getContenido() { return contenido; }
        public String getFechaHora() { return fechaHora; }
    }
}