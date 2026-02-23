package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

public class RequestPasswordResetModel {
    @SerializedName("email")
    public String email;

    public RequestPasswordResetModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}