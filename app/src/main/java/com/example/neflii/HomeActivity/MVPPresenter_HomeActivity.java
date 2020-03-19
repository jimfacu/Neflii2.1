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

    @Override
    public void recibirListaMultiSearch(ContainerFilms containerFilms) {
        view.mostrarListaMultiSearch(containerFilms);
    }

    @Override
    public void pedirListaMultiSearch(String nameFilm) {
        interactor.pedirListaMultiSearchAlServicio(nameFilm);
    }

    @Override
    public void pedirListaDeFilmsPopulares() {
        interactor.pedirListaDePeliculasPopularesAlServicio();
    }


    @Override
    public void recibirListaDeFilms(ContainerFilms containerFilms) {
        view.mostrarListaDeFilms(containerFilms);
    }

    @Override
    public void falloAlRecibirListaDeFilms() {
        view.mostrarMensajeFalloListaFilms();

    }

    @Override
    public void falloConRetrofit() {
        view.mostrarMensajeFalloRetrofit();

    }

    @Override
    public void pedirListaDeGeneros() {
        interactor.pedirListaDeGenerosAlServicio();

    }

    @Override
    public void recibirListaDeGenero(ContainerGenres containerGenres) {
        view.darListaGenerosRecycler(containerGenres);

    }

    @Override
    public void falloAlRecibirListaDeGeneros() {
        view.darListaGenerosRecycler(null);
    }

    @Override
    public void pedirListaDeFilmsSups() {
        interactor.pedirListaDeFilmsSupsAlServicio();
    }

    @Override
    public void recibirListaDeFilmsSups(List<SubsMovie> listSupsFilm) {
        view.mostrarListaDeFilmsSups(listSupsFilm);
    }

    @Override
    public void falloAlRecibirListaSups() {

    }
}

