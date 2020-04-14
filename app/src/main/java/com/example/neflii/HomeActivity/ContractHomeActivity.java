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

        //Mensajes
        void mostrarMensajeDeFallo(String s);
        void mostrarMensajeDePeliculaAgregada();
    }

    interface Presenter{
        //Lista Multi Search
        void recibirListaMultiSearch(ContainerFilms containerFilms);
        void pedirListaMultiSearch(String nameFilm);
        //Lista Films Populares
        void pedirListaDeFilmsPopulares();
        void recibirListaDeFilmsPopulares(ContainerFilms containerFilms);
        //Lista de Generos
        void pedirListaDeGeneros();
        void recibirListaDeGenero(ContainerGenres containerGenres);
        //Lista de Films Suscriptos
        void pedirListaDeFilmsSups();
        void recibirListaDeFilmsSups(List<SubsMovie> listSupsFilm);
        //Lista con Nueva Pelicula
        void recibirListaConNuevaPeliculaDeLaView(List<SubsMovie> listWithNewsFilms);
        void recibirOkDeListaConNuevaPelicula();
        //Fallo
        void recibirMensajeDeFallo(String s);
    }

    interface Interactor{
        void pedirListaMultiSearchAlServicio(String nameFilm);

        void pedirListaDePeliculasPopularesAlServicio();

        void pedirListaDeGenerosAlServicio();

        void pedirListaDeFilmsSupsAlServicio();

        void recibirListaConNuevaPleiculaParaGuardarEnFirebase(List<SubsMovie> listWithNewsFilms);


    }
}
