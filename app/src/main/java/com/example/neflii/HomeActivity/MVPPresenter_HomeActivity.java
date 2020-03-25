package com.example.neflii.HomeActivity;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;

import java.util.List;

public class MVPPresenter_HomeActivity implements ContractHomeActivity.Presenter {

    private ContractHomeActivity.View view;
    private ContractHomeActivity.Interactor interactor;

    public MVPPresenter_HomeActivity(ContractHomeActivity.View view) {
        this.view = view;
        interactor = new MVPInteractor_HomeActivity(this);
    }

    //Peticiones de Listas

    @Override
    public void pedirListaMultiSearch(String nameFilm) {
        if(!nameFilm.equals("")){
            interactor.pedirListaMultiSearchAlServicio(nameFilm);
        }
    }
    @Override
    public void recibirListaMultiSearch(ContainerFilms containerFilms) {
        view.mostrarListaMultiSearch(containerFilms);
    }
    @Override
    public void pedirListaDeFilmsPopulares() {
            interactor.pedirListaDePeliculasPopularesAlServicio();

    }
    @Override
    public void pedirListaDeGeneros() {
        interactor.pedirListaDeGenerosAlServicio();
    }
    @Override
    public void pedirListaDeFilmsSups() {
        interactor.pedirListaDeFilmsSupsAlServicio();
    }


    //Recibir listas pedidas
    @Override
    public void recibirListaDeFilmsPopulares(ContainerFilms containerFilms) {
        view.mostrarListaDeFilms(containerFilms);
    }
    @Override
    public void recibirListaDeGenero(ContainerGenres containerGenres) {
        view.darListaGenerosRecycler(containerGenres);
    }
    @Override
    public void recibirListaDeFilmsSups(List<SubsMovie> listSupsFilm) {
        view.mostrarListaDeFilmsSups(listSupsFilm);
    }

    //Recibir lista con nueva pelicula desde la view
    @Override
    public void recibirListaConNuevaPeliculaDeLaView(List<SubsMovie> listWithNewsFilms) {
        interactor.recibirListaConNuevaPleiculaParaGuardarEnFirebase(listWithNewsFilms);
    }

    //Recibir el ok de la lista actualizada
    @Override
    public void recibirOkDeListaConNuevaPelicula() {
        view.mostrarMensajeExitoAñadirPeliculaNueva();
    }

    //Fallos
    @Override
    public void falloAlRecibirListaMultiSearch() {
        view.mostrarMensajeFalloListaMultiSearch();
    }
    @Override
    public void falloConRetrofitDeMultiSearch() {
        view.mostrarMensajeFalloRetrofitMultiSearch();
    }
    @Override
    public void falloAlRecibirListaDeFilmsPopulares() {
        view.mostrarMensajeFalloFilmsPopulares();
    }
    @Override
    public void falloConRetrofitDeFilmsPopulares() {
       view.mostrarMensajeFalloRetrofitFilmsPopulares();
    }
    @Override
    public void falloAlRecibirListaDeGeneros() {
        view.mostrarMensajeFalloListaDeGeneros();
    }

    @Override
    public void falloConRetrofitDeListaDeGeneros() {
        view.mostrarMensajeFalloRetrofitGeneros();
    }
    @Override
    public void falloAlRecibirListaSups() {
        view.mostrarMensajeFalloListaFilmsSuscriptos();
    }


}

