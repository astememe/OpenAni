package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

public class UserUpdateModel {
    @SerializedName("username")
    public String username;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("imagen")
    public String imagen;

    @SerializedName("descripcion")
    public String descripcion;

    public UserUpdateModel(String username, String email, String password, String imagen, String descripcion) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }
}
