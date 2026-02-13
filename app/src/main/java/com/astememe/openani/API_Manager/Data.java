package com.astememe.openani.API_Manager;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("count")
    int count;

    @SerializedName("data")
    List<Torrent> torrents = new ArrayList<>();

    public class Torrent {
        @SerializedName("category")
        public String categoria;

        @SerializedName("title")
        public String titulo;

        @SerializedName("torrent")
        public String enlace;

        @SerializedName("size")
        public String tamano;

        @SerializedName("time")
        public String fecha;

        @SerializedName("seeders")
        public int seeders;

        @SerializedName("leechers")
        public int leechers;

    }

}
