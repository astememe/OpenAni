package com.astememe.openani.Adaptador_Evento;

public class TorrentModel {


    public String titulo_torrent, tamano_torrent, ultima_fecha_torrent, magnet_boton_torrent;

    public Integer cantidad_dislikes_torrent, cantidad_likes_torrent, cantidad_seeders_torrent, cantidad_leechers_torrent;

    public boolean dado_like, dado_dislike, is_favorito;

    public TorrentModel(String titulo_torrent, String tamano_torrent, String ultima_fecha_torrent,
                        int cantidad_seeders_torrent, int cantidad_leechers_torrent, String magnet_boton_torrent){
        this.titulo_torrent = titulo_torrent;
        this.tamano_torrent = tamano_torrent;
        this.ultima_fecha_torrent = ultima_fecha_torrent;
        this.cantidad_seeders_torrent = cantidad_seeders_torrent;
        this.cantidad_leechers_torrent = cantidad_leechers_torrent;
        this.magnet_boton_torrent = magnet_boton_torrent;
    }

    public String getTitulo_torrent() {
        return titulo_torrent;
    }

    public String getTamano_torrent() {
        return tamano_torrent;
    }

    public String getUltima_fecha_torrent() {
        return ultima_fecha_torrent;
    }

    public int getCantidad_seeders_torrent() {
        return cantidad_seeders_torrent;
    }

    public int getCantidad_leechers_torrent() {
        return cantidad_leechers_torrent;
    }

    public String getMagnet_boton_torrent() {
        return magnet_boton_torrent;
    }

    public int getCantidad_dislikes_torrent() {
        return cantidad_dislikes_torrent;
    }

    public int getCantidad_likes_torrent() {
        return cantidad_likes_torrent;
    }

    public boolean isDado_like() {
        return dado_like;
    }

    public boolean isDado_dislike() {
        return dado_dislike;
    }

    public boolean is_favorito() {
        return is_favorito;
    }
}
