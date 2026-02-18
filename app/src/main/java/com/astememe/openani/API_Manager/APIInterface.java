package com.astememe.openani.API_Manager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {
    @GET("/nyaa")
    Call<DataModel> getRecent(@Query("nada") String nada);
    //Al hacer la peticion, no pasarle nada.

    @GET("/nyaa")
    Call<DataModel> getByCategory(@Query("category") String categoria);

    @GET("/nyaa")
    Call<DataModel> getBySubcategory(@QueryMap Map<String, String> filters);
    //Calling with foo.friends(ImmutableMap.of("group", "coworker", "age", "42")) yields /friends?group=coworker&age=42.
    //En nuestro caso sería getBySubCategory(ImmutableMap.of("category", "anime/manga", "sub_category", "loquesea")
    //'https://nyaaapi.onrender.com/nyaa?category=anime&sub_category=raw'

    @GET("/nyaa")
    Call<DataModel> getByName(@Query("q") String nombre);

    @GET("/nyaa")
    Call<DataModel> getByNameandCategory(@QueryMap Map<String, String> filters);

    @GET("/nyaa")
    Call<DataModel> getByNameandCategoryandSubCategory(@QueryMap Map<String, String> filters);


}
