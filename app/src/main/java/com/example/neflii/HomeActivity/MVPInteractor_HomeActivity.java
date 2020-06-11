package com.example.neflii.HomeActivity;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.HomeActivity.Utils.Constants;
import com.example.neflii.HomeActivity.Utils.ServiceApi_HomeActivity;
import com.example.neflii.HomeActivity.Utils.ServiceRetrofit_HomeActivity;
import com.example.neflii.R;
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
    private Context context;
    private  ServiceApi_HomeActivity serviceApi;



    public MVPInteractor_HomeActivity(ContractHomeActivity.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
        serviceApi = ServiceRetrofit_HomeActivity.getInstance().create(ServiceApi_HomeActivity.class);
     }

    @Override
    public void pedirListaMultiSearchAlServicio(String nameFilm) {
        Call<ContainerFilms>  call = serviceApi.getMultiSearchFilms(context.getString(R.string.Api_Key),nameFilm);
        call.enqueue(new Callback<ContainerFilms>() {
            @Override
            public void onResponse(Call<ContainerFilms> call, Response<ContainerFilms> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaMultiSearch(response.body());
                }else{
                    presenter.recibirMensajeDeFallo(context.getResources().getString(R.string.Fallo_al_recibir_lista_multi_search));
                }
            }
            @Override
            public void onFailure(Call<ContainerFilms> call, Throwable t) {
                    presenter.recibirMensajeDeFallo(context.getResources().getString(R.string.Fallo_con_Retrofit_lista_multi_search));
        }
    });
    }

    @Override
    public void pedirListaDePeliculasPopularesAlServicio() {
        Call<ContainerFilms> call = serviceApi.getTrendingFilms(Constants.KEY_ALL,Constants.KEY_WEEK,context.getString(R.string.Api_Key));
        call.enqueue(new Callback<ContainerFilms>() {
            @Override
            public void onResponse(Call<ContainerFilms> call, Response<ContainerFilms> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaDeFilmsPopulares(response.body());
                }else{
                    presenter.recibirMensajeDeFallo(context.getResources().getString(R.string.Fallo_con_retrofit_lista_films_populares));
                }
            }
            @Override
            public void onFailure(Call<ContainerFilms> call, Throwable t) {
                presenter.recibirMensajeDeFallo(context.getResources().getString(R.string.Fallo_con_retrofit_lista_films_populares));
            }
        });
    }

    @Override
    public void pedirListaDeGenerosAlServicio() {
        Call<ContainerGenres> call = serviceApi.getGenresList(context.getString(R.string.Api_Key));
        call.enqueue(new Callback<ContainerGenres>() {
            @Override
            public void onResponse(Call<ContainerGenres> call, Response<ContainerGenres> response) {
                if(response.isSuccessful()){
                    presenter.recibirListaDeGenero(response.body());
                }else{
                    presenter.recibirMensajeDeFallo(context.getResources().getString(R.string.Fallo_al_recibir_lista_de_generos));
                }
            }
            @Override
            public void onFailure(Call<ContainerGenres> call, Throwable t) {
                presenter.recibirMensajeDeFallo(context.getResources().getString(R.string.Fallo_con_Retrofit_lista_de_generos));
            }
        });

    }

    @Override
    public void pedirListaDeFilmsSupsAlServicio() {
        List<SubsMovie> subsMovieslist = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("films");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subsMovieslist.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    SubsMovie subsMovie = ds.getValue(SubsMovie.class);
                    subsMovieslist.add(subsMovie);
                }
                presenter.recibirListaDeFilmsSups(subsMovieslist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.recibirMensajeDeFallo(context.getResources().getString(R.string.Fallo_al_recibir_lista_de_peliculas_suscriptas));
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


