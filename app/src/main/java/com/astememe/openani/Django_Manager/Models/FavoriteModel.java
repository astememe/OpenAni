package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FavoriteModel {
    @SerializedName("torrents")
    List<FavoriteTorrentModel> favoritos;

    public static class FavoriteTorrentModel {
        @SerializedName("nombre_usuario")
        private String nombre_usuario;

        @SerializedName("title")
        private String nombre_torrent;

        @SerializedName("magnet")
        private String enlace;

        @SerializedName("size")
        private String tamano;

        @SerializedName("time")
        private String fecha;

        @SerializedName("seeders")
        private String seeders;

        @SerializedName("leechers")
        private String leechers;

        public String getNombre_usuario() {
            return nombre_usuario;
        }

        public String getNombre_torrent() {
            return nombre_torrent;
        }

        public String getEnlace() {
            return enlace;
        }

        public String getTamano() {
            return tamano;
        }

        public String getFecha() {
            return fecha;
        }

        public String getSeeders() {
            return seeders;
        }

        public String getLeechers() {
            return leechers;
        }

        public FavoriteTorrentModel(String nombre_usuario, String nombre_torrent, String enlace, String tamano, String fecha, String seeders, String leechers) {
            this.nombre_usuario = nombre_usuario;
            this.nombre_torrent = nombre_torrent;
            this.enlace = enlace;
            this.tamano = tamano;
            this.fecha = fecha;
            this.seeders = seeders;
            this.leechers = leechers;
        }

    }

}
