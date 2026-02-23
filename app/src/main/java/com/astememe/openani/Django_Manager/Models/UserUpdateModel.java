package com.astememe.openani.Django_Manager.Models;

public class UserUpdateModel {
    public String username;
    public String email;
    public String password;
    public String imagen;
    public String descripcion;

    public UserUpdateModel(String username, String email, String password, String imagen) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }
}
