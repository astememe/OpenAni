package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.RegisterModel;
import com.astememe.openani.Django_Manager.Models.UserDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserInterface {
    @GET("userRegisters/")
    Call<RegisterModel> getUsers();

    @GET("profile/")
    Call<UserDataModel.UserData> getProfile(@Header("Authorization") String token);
}
