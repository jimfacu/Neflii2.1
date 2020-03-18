package com.example.neflii.DetailActivity.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRetrofit_DetailActivity {
    private static Retrofit retrofit;
    private static String baseURL = "https://api.themoviedb.org/3/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpclient = new OkHttpClient.Builder();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.client(httpclient.build()).build();
        }
        return retrofit;
    }
}
