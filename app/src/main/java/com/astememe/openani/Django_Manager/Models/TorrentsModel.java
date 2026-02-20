package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TorrentsModel {

    @SerializedName("torrents")
    public List<TorrentBBDD> torrentsbbdd = new ArrayList<>();

    public static class TorrentBBDD {
        @SerializedName("id")
        public int id_bbdd;
        @SerializedName("nombre")
        public String nombre_bbdd;

        @SerializedName("categoria")
        public String categoria_bbdd;

        @SerializedName("enlace")
        public String enlace_bbdd;

        @SerializedName("tamano")
        public String tamano_bbdd;

        @SerializedName("fecha")
        public String fecha_bbdd;

        @SerializedName("seeders")
        public String seeders_bbdd;

        @SerializedName("leechers")
        public String leechers_bbdd;

        @SerializedName("likes")
        public String likes_bbdd;

        @SerializedName("dislikes")
        public String dislikes_bbdd;

        public TorrentBBDD(String nombre, String categoria, String enlace, String tamano, String fecha, String seeders, String leechers, String likes, String dislikes) {
            this.nombre_bbdd = nombre;
            this.categoria_bbdd = categoria;
            this.enlace_bbdd = enlace;
            this.tamano_bbdd = tamano;
            this.fecha_bbdd = fecha;
            this.seeders_bbdd = seeders;
            this.leechers_bbdd = leechers;
            this.likes_bbdd = likes;
            this.dislikes_bbdd = dislikes;
        }

        public int getId_bbdd() {
            return id_bbdd;
        }

        public String getNombre_bbdd() {
            return nombre_bbdd;
        }

        public String getCategoria_bbdd() {
            return categoria_bbdd;
        }

        public String getEnlace_bbdd() {
            return enlace_bbdd;
        }

        public String getTamano_bbdd() {
            return tamano_bbdd;
        }

        public String getFecha_bbdd() {
            return fecha_bbdd;
        }

        public String getSeeders_bbdd() {
            return seeders_bbdd;
        }

        public String getLeechers_bbdd() {
            return leechers_bbdd;
        }

        public String getLikes_bbdd() {
            return likes_bbdd;
        }

        public String getDislikes_bbdd() {
            return dislikes_bbdd;
        }
    }

}
