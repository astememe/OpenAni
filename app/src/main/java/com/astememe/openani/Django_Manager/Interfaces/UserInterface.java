package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.RegisterModel;
import com.astememe.openani.Django_Manager.Models.UserDataModel;
import com.astememe.openani.Django_Manager.Models.UserUpdateModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface UserInterface {
    @GET("userRegisters/")
    Call<RegisterModel> getUsers();

    @GET("profile/")
    Call<UserDataModel.UserData> getProfile(@Header("Authorization") String token);

    @PATCH("users/")
    Call<UserDataModel> updateProfile(@Header("Authorization") String token, @Body UserUpdateModel userUpdateModel);
}
