package com.astememe.openani.Django_Manager.Interfaces;
import com.astememe.openani.Django_Manager.Models.ComentarioModel;
import com.astememe.openani.Django_Manager.Models.TorrentsModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ComentarioInterface {
    @GET("comentarios/")
    Call<ComentarioModel> getComentario();

    @POST("comentarios/")
    Call<ComentarioModel> postComentario(@Body ComentarioModel comentario);
}