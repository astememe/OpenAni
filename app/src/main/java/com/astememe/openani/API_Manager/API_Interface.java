package com.astememe.openani.API_Manager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface API_Interface {
    @GET("/nyaa")
    Call<Data> getRecent(@Query("nada") String nada);
    //Al hacer la peticion, no pasarle nada.

    @GET("/nyaa")
    Call<Data> getByCategory(@Query("category") String categoria);

    @GET("/nyaa")
    Call<Data> getBySubcategory(@QueryMap Map<String, String> filters);
    //Calling with foo.friends(ImmutableMap.of("group", "coworker", "age", "42")) yields /friends?group=coworker&age=42.
    //En nuestro caso sería getBySubCategory(ImmutableMap.of("category", "anime/manga", "sub_category", "loquesea")
    //'https://nyaaapi.onrender.com/nyaa?category=anime&sub_category=raw'

    @GET("/nyaa")
    Call<Data> getByName(@Query("q") String nombre);

    @GET("/nyaa")
    Call<Data> getByNameandCategory(@QueryMap Map<String, String> filters);

    @GET("/nyaa")
    Call<Data> getByNameandCategoryandSubCategory(@QueryMap Map<String, String> filters);


}
