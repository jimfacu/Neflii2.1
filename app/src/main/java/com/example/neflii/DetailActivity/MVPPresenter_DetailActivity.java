package com.example.neflii.DetailActivity;

import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;

import java.util.List;

public class MVPPresenter_DetailActivity implements Contract_DetailActivity.Presenter{

    private Contract_DetailActivity.View view;
    private Contract_DetailActivity.Interactor interactor;



    public MVPPresenter_DetailActivity(Contract_DetailActivity.View view) {

        this.view = view;
        this.interactor = new MVPInteractor_DetailActivity(this);

    }

    //Peticion de peliculas mediante ID
    @Override
    public void pedirPeliculaMendianteIDAlServicio(int id) {
        interactor.pedirPleiculaAlServicioID(id);
    }
    @Override
    public void recibirPeliculaMedianteID(Movie movie) {
        view.mostrarDetallePelicula(movie);
    }
    @Override
    public void falloAlRecibirPeliculaMedianteID() {
        view.falloAlRecibirPeliculaMedianteID();
    }
    @Override
    public void falloConRetrofitPeliculaMedianteID() {
        view.falloConRetrofitPeliculaMedianteID();
    }

    //Peticion de peliculas suscriptas
    @Override
    public void pedirListaDePeliculasSuscriptasAlServicio() {
        interactor.pedirListaAFirebase();
    }
    @Override
    public void recibirListaDePeliculasSuscriptas(List<SubsMovie> subsMovieList) {
        view.setearListaDeFilms(subsMovieList);
    }

    @Override
    public void falloAlDescargarListadePeliculasSuscriptas() {
        view.falloAlDescargarListaDePeliculasSuscriptas();
    }

    //Lista con nueva pelicula desde la view
    @Override
    public void recibirListaConNuevaPelicula(List<SubsMovie> subsMovieLists) {
        interactor.guardarListaConNuevaPelicula(subsMovieLists);
    }

    @Override
    public void recibirOk() {
        view.recibirOkDelPresenter();
    }
}
