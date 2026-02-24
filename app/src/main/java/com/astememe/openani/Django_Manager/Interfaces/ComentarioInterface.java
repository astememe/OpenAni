package com.astememe.openani.Django_Manager.Interfaces;
import com.astememe.openani.Django_Manager.Models.ComentarioModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ComentarioInterface {
    @GET("comentarios/")
    Call<ComentarioModel> getComentario(@Query("nombre_torrent") String nombre_torrent);

    @POST("comentarios/")
    Call<ComentarioModel> postComentario(@Body ComentarioModel.ComentarioTorrent comentario);
}