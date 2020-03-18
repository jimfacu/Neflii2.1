package com.example.neflii.HomeActivity;

import android.util.Log;

import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.HomeActivity.Utils.ServiceApi_HomeActivity;
import com.example.neflii.HomeActivity.Utils.ServiceRetrofit_HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MVPInteractor_HomeActivity implements ContractHomeActivity.Interactor {

    private ContractHomeActivity.Presenter presenter;


    public MVPInteractor_HomeActivity(ContractHomeActivity.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void pedirListaDePeliculasPopularesAlServicio() {
        ServiceApi_HomeActivity serviceApi = ServiceRetrofit_HomeActivity.getInstance().create(ServiceApi_HomeActivity.class);
        Call<ContainerFilms> call = serviceApi.getTrendingFilms("all","week","64312ffd81ef5d7e20afaa0866b9bec6");
        call.enqueue(new Callback<ContainerFilms>() {
            @Override
            public void onResponse(Call<ContainerFilms> call, Response<ContainerFilms> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaDeFilms(response.body());

                    Log.d("ERROR","EL CODIGO DE ERROR ES PERO ENTRO: "+response.code());
                }else{
                    presenter.falloAlRecibirListaDeFilms();

                    Log.d("ERROR","EL CODIGO DE ERROR ES: "+response.code());
                    Log.d("ERROR0","EL ERROR ES"+response.message())   ;
                    Log.d("ERROR","EL CODIGO DE ERROR ES: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ContainerFilms> call, Throwable t) {
                presenter.falloConRetrofit();
                Log.d("ERROR CON RETROFIT","EL MENSAJE DE ERROR ES: "+t.getMessage());
            }
        });
    }

    @Override
    public void pedirListaDeGenerosAlServicio() {
        ServiceApi_HomeActivity serviceApi = ServiceRetrofit_HomeActivity.getInstance().create(ServiceApi_HomeActivity.class);
        Call<ContainerGenres> call = serviceApi.getGenresList("64312ffd81ef5d7e20afaa0866b9bec6");

        call.enqueue(new Callback<ContainerGenres>() {
            @Override
            public void onResponse(Call<ContainerGenres> call, Response<ContainerGenres> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaDeGenero(response.body());
                    Log.d("ERROR","EL CODIGO DE ERROR ES PERO ENTRO: "+response.code());
                }else{
                    presenter.falloAlRecibirListaDeGeneros();

                    Log.d("ERROR","EL CODIGO DE ERROR ES: "+response.code());
                    Log.d("ERROR0","EL ERROR ES"+response.message())   ;
                    Log.d("ERROR","EL CODIGO DE ERROR ES: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ContainerGenres> call, Throwable t) {
                presenter.falloConRetrofit();
                Log.d("ERROR CON RETROFIT","EL MENSAJE DE ERROR ES: "+t.getMessage());
            }
        });

    }


}
