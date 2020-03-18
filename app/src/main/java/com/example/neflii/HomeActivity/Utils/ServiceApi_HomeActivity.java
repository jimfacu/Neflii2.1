package com.example.neflii.HomeActivity.Utils;

import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi_HomeActivity {

    @GET("trending/{media_type}/{time_window}")
    Call<ContainerFilms> getTrendingFilms(@Path("media_type")String media_type, @Path("time_window")
            String time_window, @Query("api_key")String api_key);

    @GET("genre/movie/list")
    Call<ContainerGenres> getGenresList(@Query("api_key")String api_key);
}
