package com.astememe.openani.Django_Manager.Interfaces;
import com.astememe.openani.Django_Manager.Models.FavoriteModel;
import com.astememe.openani.Django_Manager.Models.TorrentsModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TorrentsInterface {
    @GET("torrents/")
    Call<TorrentsModel> getTorrents();

    @POST("torrents/")
    Call<Void> postTorrent(@Body TorrentsModel.TorrentBBDD torrent);

    @GET("favorito/")
    Call<TorrentsModel> getFavorites(@Query("nombre_usuario") String nombre);

    @POST("favorito/")
    Call<Void> postFavorite(@Header("Authorization") String token, @Body FavoriteModel.FavoriteTorrentModel favorite);
}
