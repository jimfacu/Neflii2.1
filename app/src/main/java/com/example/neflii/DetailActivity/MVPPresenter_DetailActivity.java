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

    @Override
    public void pedirPeliculaMendianteID(int id) {
        interactor.pedirPleiculaAlServicioID(id);
    }



    @Override
    public void recibirPelicula(Movie movie) {
        view.mostrarDetallePelicula(movie);
    }

    @Override
    public void falloAlRecibirListaDeFilms(Movie movie) {

    }

    @Override
    public void falloConRetrofit() {

    }

    @Override
    public void pedirListaDeSubsAlServicio() {
        interactor.pedirListaAFirebase();
    }

    @Override
    public void falloAlDescargarListadeFirebase() {
        view.falloAlDescargarListaDeFilms();
    }



    @Override
    public void recibirListaDeFilmsDelServicio(List<SubsMovie> subsMovieList) {
        view.setearListaDeFilms(subsMovieList);
    }

    @Override
    public void recibirListaConNuevaPelicula(List<SubsMovie> subsMovieLists) {
        interactor.guardarListaConNuevaPelicula(subsMovieLists);
    }

    @Override
    public void recibirOk() {
        view.recibirOkDelPresenter();

    }
}
