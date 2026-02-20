package com.astememe.openani.API_Manager;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    @SerializedName("count")
    int count;

    @SerializedName("data")
    public List<Torrent> torrents = new ArrayList<>();

    public class Torrent {
        @SerializedName("category")
        public String categoria;

        @SerializedName("title")
        public String titulo;

        @SerializedName("magnet")
        public String enlace;

        @SerializedName("size")
        public String tamano;

        @SerializedName("time")
        public String fecha;

        @SerializedName("seeders")
        public int seeders;

        @SerializedName("leechers")
        public int leechers;

        public Torrent(String categoria, String titulo, String enlace, String tamano, String fecha, int seeders, int leechers) {
            this.categoria = categoria;
            this.titulo = titulo;
            this.enlace = enlace;
            this.tamano = tamano;
            this.fecha = fecha;
            this.seeders = seeders;
            this.leechers = leechers;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getTitulo() {
            return titulo;
        }


        public String getEnlace() {
            return enlace;
        }


        public String getTamano() {
            return tamano;
        }

        public String getFecha() {
            return fecha.split(" ")[0];
        }


        public int getLeechers() {
            return leechers;
        }

        public int getSeeders() {
            return seeders;
        }

    }

}
