package com.astememe.openani.Django_Manager.Interfaces;

import com.astememe.openani.Django_Manager.Models.ConfirmPasswordResetModel;
import com.astememe.openani.Django_Manager.Models.RequestPasswordResetModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PasswordResetInterface {
    @POST("request-reset/")
    Call<ResponseBody> requestReset(@Body RequestPasswordResetModel requestPasswordResetModel);

    @POST("confirm-reset/")
    Call<ResponseBody> confirmReset(@Body ConfirmPasswordResetModel confirmPasswordResetModel);
}
