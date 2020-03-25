package com.example.neflii.HomeActivity;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;

import java.util.List;

public interface ContractHomeActivity {

    interface View{
        void mostrarListaMultiSearch(ContainerFilms containerFilms);
        void mostrarListaDeFilms(ContainerFilms containerFilms);
        void mostrarListaDeFilmsSups(List<SubsMovie> listSupsFilm);
        void darListaGenerosRecycler(ContainerGenres containerGenres);

        //Mensajes de errores
        void mostrarMensajeFalloListaMultiSearch();
        void mostrarMensajeFalloRetrofitMultiSearch();
        void mostrarMensajeFalloFilmsPopulares();
        void mostrarMensajeFalloRetrofitFilmsPopulares();
        void mostrarMensajeFalloListaDeGeneros();
        void mostrarMensajeFalloRetrofitGeneros();
        void mostrarMensajeFalloListaFilmsSuscriptos();
        void mostrarMensajeFalloRetrofitFilmsSuscriptos();
        void mostrarMensajeExitoAñadirPeliculaNueva();
    }

    interface Presenter{
        //Lista Multi Search
        void recibirListaMultiSearch(ContainerFilms containerFilms);
        void pedirListaMultiSearch(String nameFilm);
        void falloAlRecibirListaMultiSearch();
        void falloConRetrofitDeMultiSearch();
        //Lista Films Populares
        void pedirListaDeFilmsPopulares();
        void recibirListaDeFilmsPopulares(ContainerFilms containerFilms);
        void falloAlRecibirListaDeFilmsPopulares();
        void falloConRetrofitDeFilmsPopulares();
        //Lista de Generos
        void pedirListaDeGeneros();
        void recibirListaDeGenero(ContainerGenres containerGenres);
        void falloAlRecibirListaDeGeneros();
        void falloConRetrofitDeListaDeGeneros();
        //Lista de Films Suscriptos
        void pedirListaDeFilmsSups();
        void recibirListaDeFilmsSups(List<SubsMovie> listSupsFilm);
        void falloAlRecibirListaSups();
        //Lista con Nueva Pelicula
        void recibirListaConNuevaPeliculaDeLaView(List<SubsMovie> listWithNewsFilms);
        void recibirOkDeListaConNuevaPelicula();
    }

    interface Interactor{
        void pedirListaMultiSearchAlServicio(String nameFilm);

        void pedirListaDePeliculasPopularesAlServicio();

        void pedirListaDeGenerosAlServicio();

        void pedirListaDeFilmsSupsAlServicio();

        void recibirListaConNuevaPleiculaParaGuardarEnFirebase(List<SubsMovie> listWithNewsFilms);


    }
}
