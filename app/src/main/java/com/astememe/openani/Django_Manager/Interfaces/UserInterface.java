package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.RegisterModel;
import com.astememe.openani.Django_Manager.Models.UserDataModel;
import com.astememe.openani.Django_Manager.Models.UserUpdateModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Query;

public interface UserInterface {
    @GET("userRegisters/")
    Call<RegisterModel> getUsers();

    @GET("profile/")
    Call<UserDataModel.UserData> getProfile(@Header("Authorization") String token);

    @GET("users/")
    Call<List<UserDataModel.UserData>> getProfiles(@Header("Authorization") String token, @Query("username") String username);

    @PATCH("users/")
    Call<UserDataModel> updateProfile(@Header("Authorization") String token, @Body UserUpdateModel userUpdateModel);
}
