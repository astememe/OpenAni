package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

public class ConfirmPasswordResetModel {
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;

    @SerializedName("reset_code")
    public int reset_code;

    public ConfirmPasswordResetModel(String email, String new_password, int code) {
        this.email = email;
        this.password = new_password;
        this.reset_code = code;
    }

    public String getEmail() {
        return email;
    }

    public int getReset_code() {
        return reset_code;
    }

    public String getPassword() {
        return password;
    }
}
