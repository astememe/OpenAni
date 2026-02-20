package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

public class TokenModel {
    @SerializedName("access")
    public String token;

    @SerializedName("refresh")
    public String refresh;

    public TokenModel(String token, String refresh, boolean success) {
        this.token = token;
        this.refresh = refresh;
    }

    public String getToken() {
        return token;
    }

    public String getRefresh() {
        return refresh;
    }
}
