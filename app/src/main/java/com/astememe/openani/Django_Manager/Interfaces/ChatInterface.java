package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.ComentarioModel;
import com.astememe.openani.Django_Manager.Models.MessageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatInterface {

    @GET("messages/")
    Call<MessageModel> getMessage(@Query("group_id") String group_id);

}
