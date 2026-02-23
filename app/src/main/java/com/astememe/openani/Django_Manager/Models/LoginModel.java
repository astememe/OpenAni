package com.astememe.openani.Django_Manager.Models;

public class LoginModel {
    public String username;
    public String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}