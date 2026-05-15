package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RoomModel {

    @SerializedName("rooms")
    private List<RoomDetail> salas;

    public List<RoomDetail> getSalas() {
        return salas;
    }

    public void setSalas(List<RoomDetail> salas) {
        this.salas = salas;
    }

    public static class RoomDetail {
        @SerializedName("id")
        private int id;

        @SerializedName("other_user")
        private String nombreOtroUsuario;

        @SerializedName("last_message")
        private String ultimoMensaje;

        @SerializedName("last_message_timestamp")
        private String fechaUltimoMensaje;

        // Constructor
        public RoomDetail(int id, String nombreOtroUsuario, String ultimoMensaje, String fechaUltimoMensaje) {
            this.id = id;
            this.nombreOtroUsuario = nombreOtroUsuario;
            this.ultimoMensaje = ultimoMensaje;
            this.fechaUltimoMensaje = fechaUltimoMensaje;
        }

        // Getters
        public int getId() { return id; }
        public String getNombreOtroUsuario() { return nombreOtroUsuario; }
        public String getUltimoMensaje() { return ultimoMensaje; }
        public String getFechaHora() { return fechaUltimoMensaje; }
    }
}