package com.example.neflii.DetailActivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.DetailActivity.Utils.ServiceApi_DetailActivity;
import com.example.neflii.DetailActivity.Utils.ServiceRetrofit_DetailActivity;
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

public class MVPInteractor_DetailActivity implements Contract_DetailActivity.Interactor {

    private Contract_DetailActivity.Presenter presenter;
    private List<SubsMovie> subsMovieslist;
    private SubsMovie subsMovie;

    public MVPInteractor_DetailActivity(Contract_DetailActivity.Presenter presenter) {
        this.presenter = presenter;
        this.subsMovie = new SubsMovie();
        this.subsMovieslist = new ArrayList<>();
    }


    @Override
    public void pedirPleiculaAlServicioID(int id) {
        ServiceApi_DetailActivity serviceApi = ServiceRetrofit_DetailActivity.getInstance().create(ServiceApi_DetailActivity.class);
        Call<Movie> call = serviceApi.getMovieById(id, "64312ffd81ef5d7e20afaa0866b9bec6", "en-US");
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    presenter.recibirPelicula(response.body());
                    Log.d("ERROR", "EL CODIGO DE ERROR ES PERO ENTRO: " + response.code());
                } else {
                    presenter.falloAlRecibirListaDeFilms(null);
                    Log.d("ERROR", "EL CODIGO DE ERROR ES: " + response.code());
                    Log.d("ERROR0", "EL ERROR ES" + response.message());
                    Log.d("ERROR", "EL CODIGO DE ERROR ES: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                presenter.falloConRetrofit();

            }
        });

    }

    @Override
    public void pedirListaAFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("films");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    subsMovie = ds.getValue(SubsMovie.class);
                    subsMovieslist.add(subsMovie);
                }
                presenter.recibirListaDeFilmsDelServicio(subsMovieslist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.falloAlDescargarListadeFirebase();
            }
        });
    }

    @Override
    public void guardarListaConNuevaPelicula(List<SubsMovie> subsMovieLists) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("films");
        reference.setValue(subsMovieLists)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            presenter.recibirOk();
                        }
                    }
                });

    }
}
