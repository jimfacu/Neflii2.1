package com.example.neflii.HomeActivity;

import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;

public class MVPPresenter_HomeActivity implements ContractHomeActivity.Presenter {

    private ContractHomeActivity.View view;
    private ContractHomeActivity.Interactor interactor;



    public MVPPresenter_HomeActivity(ContractHomeActivity.View view) {
        this.view = view;
        interactor = new MVPInteractor_HomeActivity(this);
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
}

