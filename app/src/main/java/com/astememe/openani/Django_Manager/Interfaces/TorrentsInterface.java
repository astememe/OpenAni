package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.TorrentsModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TorrentsInterface {
    @GET("torrents/")
    Call<TorrentsModel> getTorrents();

    @POST("torrents/")
    Call<Void> postTorrent(@Body TorrentsModel.TorrentBBDD torrent);

}
