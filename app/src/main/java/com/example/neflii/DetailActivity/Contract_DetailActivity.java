package com.example.neflii.DetailActivity;

import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;

import java.util.List;

public interface Contract_DetailActivity {

    interface View{
        void mostrarDetallePelicula(Movie movie);
        void setearListaDeFilms(List<SubsMovie> subsMovieList);
        void falloAlDescargarListaDeFilms();
        void recibirOkDelPresenter();
    }

    interface Presenter{
        void pedirPeliculaMendianteID(int id);
        void recibirPelicula(Movie movie);
        void falloAlRecibirListaDeFilms(Movie movie);
        void falloConRetrofit();
        void pedirListaDeSubsAlServicio();
        void falloAlDescargarListadeFirebase();
        void recibirListaDeFilmsDelServicio(List<SubsMovie> subsMovieList);
        void recibirListaConNuevaPelicula(List<SubsMovie> collectionFilms);
        void recibirOk();
    }

    interface Interactor{
        void pedirPleiculaAlServicioID(int id);
        void pedirListaAFirebase();
        void guardarListaConNuevaPelicula(List<SubsMovie> collectionFilms);
    }
}
