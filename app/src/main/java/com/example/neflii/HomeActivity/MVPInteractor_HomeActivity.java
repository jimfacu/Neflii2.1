package com.example.neflii.HomeActivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.HomeActivity.Utils.ServiceApi_HomeActivity;
import com.example.neflii.HomeActivity.Utils.ServiceRetrofit_HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public void pedirListaMultiSearchAlServicio(String nameFilm) {
        ServiceApi_HomeActivity serviceApi = ServiceRetrofit_HomeActivity.getInstance().create(ServiceApi_HomeActivity.class);
        Call<ContainerFilms>  call = serviceApi.getMultiSearchFilms("64312ffd81ef5d7e20afaa0866b9bec6",nameFilm);
        call.enqueue(new Callback<ContainerFilms>() {
            @Override
            public void onResponse(Call<ContainerFilms> call, Response<ContainerFilms> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaMultiSearch(response.body());
                }else{
                    presenter.falloAlRecibirListaMultiSearch();
                }
            }
            @Override
            public void onFailure(Call<ContainerFilms> call, Throwable t) {
                presenter.falloConRetrofitDeMultiSearch();
            }
        });
    }

    @Override
    public void pedirListaDePeliculasPopularesAlServicio() {
        ServiceApi_HomeActivity serviceApi = ServiceRetrofit_HomeActivity.getInstance().create(ServiceApi_HomeActivity.class);
        Call<ContainerFilms> call = serviceApi.getTrendingFilms("all","week","64312ffd81ef5d7e20afaa0866b9bec6");
        call.enqueue(new Callback<ContainerFilms>() {
            @Override
            public void onResponse(Call<ContainerFilms> call, Response<ContainerFilms> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaDeFilmsPopulares(response.body());
                }else{
                    presenter.falloAlRecibirListaDeFilmsPopulares();
                }
            }
            @Override
            public void onFailure(Call<ContainerFilms> call, Throwable t) {
                presenter.falloConRetrofitDeFilmsPopulares();
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
                }else{
                    presenter.falloAlRecibirListaDeGeneros();
                }
            }
            @Override
            public void onFailure(Call<ContainerGenres> call, Throwable t) {
                presenter.falloConRetrofitDeListaDeGeneros();
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
                subsMovieslist.clear();
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

    @Override
    public void recibirListaConNuevaPleiculaParaGuardarEnFirebase(List<SubsMovie> listWithNewsFilms) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("films");
        reference.setValue(listWithNewsFilms)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            presenter.recibirOkDeListaConNuevaPelicula();
                        }
                    }
                });
    }
}


