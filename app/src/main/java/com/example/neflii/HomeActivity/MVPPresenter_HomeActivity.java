package com.example.neflii.HomeActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.HomeActivity.Utils.Utils;
import com.example.neflii.R;

import java.util.List;

public class MVPPresenter_HomeActivity implements ContractHomeActivity.Presenter {

    private ContractHomeActivity.View view;
    private ContractHomeActivity.Interactor interactor;
    private Context context;

    public MVPPresenter_HomeActivity(ContractHomeActivity.View view,Context context) {
        this.view = view;
        this.context = context;
        interactor = new MVPInteractor_HomeActivity(this,context);
    }

    @Override
    public void pedirListaMultiSearch(String nameFilm) {
        if(!nameFilm.equals("")){
            interactor.pedirListaMultiSearchAlServicio(nameFilm);
        }
    }
    @Override
    public void recibirListaMultiSearch(ContainerFilms containerFilms) {
        if(view != null) {
            view.mostrarListaMultiSearch(containerFilms);
        }
    }
    @Override
    public void pedirListaDeFilmsPopulares() {
        if(Utils.internetAvalible(context)) {
            interactor.pedirListaDePeliculasPopularesAlServicio();
        }else{
            if(view != null){
                view.mostrarMensajeDeFallo(context.getResources().getString(R.string.Error_de_conexion_a_Internet));
            }
        }
    }
    @Override
    public void pedirListaDeGeneros() {
        interactor.pedirListaDeGenerosAlServicio();
    }
    @Override
    public void pedirListaDeFilmsSups() {
        interactor.pedirListaDeFilmsSupsAlServicio();
    }


    @Override
    public void recibirListaDeFilmsPopulares(ContainerFilms containerFilms) {
        if(view != null){
            view.mostrarListaDeFilms(containerFilms);
        }
    }
    @Override
    public void recibirListaDeGenero(ContainerGenres containerGenres) {
        if(view != null) {
            view.darListaGenerosRecycler(containerGenres);
        }
    }
    @Override
    public void recibirListaDeFilmsSups(List<SubsMovie> listSupsFilm) {
        if (view != null) {
            view.mostrarListaDeFilmsSups(listSupsFilm);
        }
    }

    //Recibir lista con nueva pelicula desde la view
    @Override
    public void recibirListaConNuevaPeliculaDeLaView(List<SubsMovie> listWithNewsFilms) {
        interactor.recibirListaConNuevaPleiculaParaGuardarEnFirebase(listWithNewsFilms);
    }


    @Override
    public void recibirOkDeListaConNuevaPelicula() {
        if(view != null){
            view.mostrarMensajeDePeliculaAgregada();
        }
    }

    @Override
    public void recibirMensajeDeFallo(String s) {
        if(view != null){
            view.mostrarMensajeDeFallo(s);
        }
    }
}

