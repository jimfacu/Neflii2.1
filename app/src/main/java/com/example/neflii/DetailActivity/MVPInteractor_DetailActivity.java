package com.example.neflii.DetailActivity;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.DetailActivity.Utils.ServiceApi_DetailActivity;
import com.example.neflii.HomeActivity.Utils.Constants;
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

public class MVPInteractor_DetailActivity implements Contract_DetailActivity.Interactor {

    private Contract_DetailActivity.Presenter presenter;
    private Context context;

    public MVPInteractor_DetailActivity(Contract_DetailActivity.Presenter presenter,Context context) {
        this.presenter = presenter;
        this.context = context;
    }


    @Override
    public void pedirPleiculaAlServicioID(int id) {
        ServiceApi_DetailActivity serviceApi = ServiceRetrofit_HomeActivity.getInstance().create(ServiceApi_DetailActivity.class);
        Call<Movie> call = serviceApi.getMovieById(id, context.getString(R.string.Api_Key),Constants.KEY_EN_US);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    presenter.recibirPeliculaMedianteID(response.body());
                } else {
                    presenter.recibirMensajeErrorInteractor(context.getResources().getString(R.string.Fallo_descarga_de_pelicula_mediante_id));
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                presenter.recibirMensajeErrorInteractor(context.getResources().getString(R.string.Fallo_con_retrofit_pelicula_mediante_ID));
            }
        });

    }
    @Override
    public void pedirListaAFirebase() {

        List<SubsMovie> subsMovieslist = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("films");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    SubsMovie subsMovie = ds.getValue(SubsMovie.class);
                    subsMovieslist.add(subsMovie);
                }
                presenter.recibirListaDePeliculasSuscriptas(subsMovieslist);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.recibirMensajeErrorInteractor(context.getResources().getString(R.string.Fallo_al_recibir_lista_de_peliculas_suscriptas));
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
                            presenter.recibirOk(context.getResources().getString(R.string.Exito_al_añadir_la_nueva_Pelicula));
                        }
                    }
                });
    }
}
