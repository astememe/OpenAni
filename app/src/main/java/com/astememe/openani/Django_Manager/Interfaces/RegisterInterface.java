package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.RegisterModel;
import com.astememe.openani.Django_Manager.Models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterInterface {
    @POST("register/")
    Call<RegisterModel> register(@Body UserModel.User user);
}
