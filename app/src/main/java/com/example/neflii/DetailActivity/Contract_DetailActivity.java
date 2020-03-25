package com.example.neflii.DetailActivity;

import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;

import java.util.List;

public interface Contract_DetailActivity {

    interface View{
        void mostrarDetallePelicula(Movie movie);
        void setearListaDeFilms(List<SubsMovie> subsMovieList);
        void falloAlRecibirPeliculaMedianteID();
        void falloConRetrofitPeliculaMedianteID();
        void falloAlDescargarListaDePeliculasSuscriptas();
        void falloConRetrofitPeliculasSuscriptas();
        void recibirOkDelPresenter();
    }

    interface Presenter{
        //Pelicula mediante ID
        void pedirPeliculaMendianteIDAlServicio(int id);
        void recibirPeliculaMedianteID(Movie movie);
        void falloAlRecibirPeliculaMedianteID();
        void falloConRetrofitPeliculaMedianteID();

        //Lista de peliculas suscriptas
        void pedirListaDePeliculasSuscriptasAlServicio();
        void recibirListaDePeliculasSuscriptas(List<SubsMovie> subsMovieList);
        void falloAlDescargarListadePeliculasSuscriptas();

        //Guardar lista de peliculas suscriptas con nueva pelicula
        void recibirListaConNuevaPelicula(List<SubsMovie> collectionFilms);
        void recibirOk();
    }

    interface Interactor{
        void pedirPleiculaAlServicioID(int id);
        void pedirListaAFirebase();
        void guardarListaConNuevaPelicula(List<SubsMovie> collectionFilms);
    }
}
