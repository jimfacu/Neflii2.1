package com.example.neflii.HomeActivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.HomeActivity.Utils.ServiceApi_HomeActivity;
import com.example.neflii.HomeActivity.Utils.ServiceRetrofit_HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MVPInteractor_HomeActivity implements ContractHomeActivity.Interactor {

    private ContractHomeActivity.Presenter presenter;
    private SubsMovie subsMovie;
    private List<SubsMovie> subsMovieslist;


    public MVPInteractor_HomeActivity(ContractHomeActivity.Presenter presenter) {
        this.presenter = presenter;
        this.subsMovieslist = new ArrayList<>();
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

    @Override
    public void pedirListaDeFilmsSupsAlServicio() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("films");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    subsMovie = ds.getValue(SubsMovie.class);
                    subsMovieslist.add(subsMovie);
                }
                presenter.recibirListaDeFilmsSups(subsMovieslist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.falloAlRecibirListaSups();
            }
        });
    }


}
