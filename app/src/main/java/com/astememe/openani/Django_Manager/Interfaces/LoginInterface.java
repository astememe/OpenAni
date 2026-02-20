package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.LoginModel;
import com.astememe.openani.Django_Manager.Models.TokenModel;
import com.astememe.openani.Django_Manager.Models.UserDataModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginInterface {
    @POST("login/")
    Call<TokenModel> loginUser(@Body LoginModel login);
}
