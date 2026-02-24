package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ComentarioModel {
    @SerializedName("comentarios")
    public List<ComentarioTorrent> comentarioModellist = new ArrayList<>();

    public static class ComentarioTorrent {
        @SerializedName("nombre_torrent")
        public String nombre_torrent;
        @SerializedName("nombre_usuario")
        public String nombre_usuario;
        @SerializedName("imagen_usuario")
        public String imagen_usuario;
        @SerializedName("texto_usuario")
        public String texto_usuario;
        @SerializedName("fecha")
        public String fecha;

        public String getNombre_torrent() {
            return nombre_torrent;
        }

        public void setNombre_torrent(String nombre_torrent) {
            this.nombre_torrent = nombre_torrent;
        }

        public String getNombre_usuario() {
            return nombre_usuario;
        }

        public void setNombre_usuario(String nombre_usuario) {
            this.nombre_usuario = nombre_usuario;
        }

        public String getImagen_usuario() {
            return imagen_usuario;
        }

        public void setImagen_usuario(String imagen_usuario) {
            this.imagen_usuario = imagen_usuario;
        }

        public String getTexto_usuario() {
            return texto_usuario;
        }

        public void setTexto_usuario(String texto_usuario) {
            this.texto_usuario = texto_usuario;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public ComentarioTorrent(String nombre_torrent, String nombre_usuario, String imagen_usuario, String texto_usuario, String fecha){
            this.nombre_torrent = nombre_torrent;
            this.nombre_usuario = nombre_usuario;
            this.imagen_usuario = imagen_usuario;
            this.texto_usuario = texto_usuario;
            this.fecha = fecha;
        }

    }
}
