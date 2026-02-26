package com.astememe.openani.Django_Manager.Interfaces;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DjangoClient {
    public static String BASE_URL = "http://10.0.2.2:8000/api/";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static TorrentsInterface getAPI_Interface() {
        return getRetrofitInstance().create(TorrentsInterface.class);
    }

    public static RegisterInterface getRegisterAPI_Interface() {
        return getRetrofitInstance().create(RegisterInterface.class);
    }
    public static UserInterface getUserAPI_Interface() {
        return getRetrofitInstance().create(UserInterface.class);
    }

    public static LoginInterface getLoginAPI_Interface() {
        return getRetrofitInstance().create(LoginInterface.class);
    }

    public static PasswordResetInterface getResetPasswordAPI_Interface() {
        return getRetrofitInstance().create(PasswordResetInterface.class);
    }

    public static ComentarioInterface getComentario_Interface() {
        return getRetrofitInstance().create(ComentarioInterface.class);
    }

    public static TorrentsInterface getTorrentsAPI_Interface() {
        return getRetrofitInstance().create(TorrentsInterface.class);
    }
}
