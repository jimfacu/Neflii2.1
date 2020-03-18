package com.example.neflii.DetailActivity.Utils;

import com.example.neflii.DetailActivity.Entities.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi_DetailActivity {
    @GET("movie/{movie_id}")
    Call<Movie> getMovieById(@Path("movie_id")Integer id, @Query("api_key")String api_key, @Query("language")String idioma);
}
