package com.astememe.openani.Django_Manager.Models;


import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("success")
    boolean success;

    @SerializedName("user")
    UserData user;

    @SerializedName("access")
    String access;

    @SerializedName("refresh")
    String refresh;

    public boolean isSuccess() {
        return success;
    }

    public UserData getUser() {
        return user;
    }

    public String getAccess() {
        return access;
    }

    public String getRefresh() {
        return refresh;
    }

    public RegisterModel(boolean success, UserData user, String access, String refresh) {
        this.success = success;
        this.user = user;
        this.access = access;
        this.refresh = refresh;
    }

    public static class UserData {
        @SerializedName("username")
        String username;

        @SerializedName("email")
        String email;

        @SerializedName("imagen")
        String imagen;

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getImagen() {
            return imagen;
        }
    }
}