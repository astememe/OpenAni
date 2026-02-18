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
}
