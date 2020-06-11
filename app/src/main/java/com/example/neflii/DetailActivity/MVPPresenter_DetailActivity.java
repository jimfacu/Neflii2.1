package com.example.neflii.DetailActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Utils.Utils;
import com.example.neflii.R;

import java.util.List;

public class MVPPresenter_DetailActivity implements Contract_DetailActivity.Presenter{

    private Contract_DetailActivity.View view;
    private Contract_DetailActivity.Interactor interactor;
    private Context context;

    public MVPPresenter_DetailActivity(Contract_DetailActivity.View view, Context context) {
        this.view = view;
        this.context = context;
        this.interactor = new MVPInteractor_DetailActivity(this,context);

    }

    //Peticion de peliculas mediante ID
    @Override
    public void pedirPeliculaMendianteIDAlServicio(int id) {
        if(Utils.internetAvalible(context)) {
            interactor.pedirPleiculaAlServicioID(id);
        }else{
            if(view != null){
                view.mostrarMensajeDeError(context.getResources().getString(R.string.Error_de_conexion_a_Internet));
            }
        }
    }
    @Override
    public void recibirPeliculaMedianteID(Movie movie) {
        if (view != null) {
            view.mostrarDetallePelicula(movie);
        }
    }

    //Peticion de peliculas suscriptas
    @Override
    public void pedirListaDePeliculasSuscriptasAlServicio() {
        interactor.pedirListaAFirebase();
    }
    @Override
    public void recibirListaDePeliculasSuscriptas(List<SubsMovie> subsMovieList) {
        if (view != null) {
            view.setearListaDeFilms(subsMovieList);
        }
    }

    @Override
    public void recibirListaConNuevaPelicula(List<SubsMovie> subsMovieLists) {
        interactor.guardarListaConNuevaPelicula(subsMovieLists);
    }

    @Override
    public void recibirMensajeErrorInteractor(String s) {
        if(view != null){
            view.mostrarMensajeDeError(s);
        }
    }

    @Override
    public void recibirOk(String s) {
        if (view != null) {
            view.recibirOkDelPresenter(s);
        }
    }
}
