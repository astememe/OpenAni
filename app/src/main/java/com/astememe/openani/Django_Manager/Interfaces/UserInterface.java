package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserInterface {
    @GET("users/")
    Call<UserModel> getUsers();
}
