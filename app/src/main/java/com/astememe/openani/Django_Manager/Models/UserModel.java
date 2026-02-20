package com.astememe.openani.Django_Manager.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserModel {

    @SerializedName("users")
    public List<User> users = new ArrayList<>();

    public static class User {
        @SerializedName("username")
        public String name;
        @SerializedName("email")
        public String email;
        @SerializedName("imagen")
        public String imagen_perfil;
        @SerializedName("password")
        public String password;
        @SerializedName("confirm_password")
        public String confirm_password;

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getImagen_perfil() {
            return imagen_perfil;
        }

        public String getPassword() {
            return password;
        }

        public String getConfirm_password() {
            return confirm_password;
        }

        public User(String name, String email, String imagen_perfil, String password, String confirm_password) {
            this.name = name;
            this.email = email;
            this.imagen_perfil = imagen_perfil;
            this.password = password;
            this.confirm_password = confirm_password;
        }
    }
}
