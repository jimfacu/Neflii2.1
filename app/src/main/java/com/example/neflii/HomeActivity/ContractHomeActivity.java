package com.example.neflii.HomeActivity;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;

import java.util.List;

public interface ContractHomeActivity {

    interface View{
        void mostrarListaMultiSearch(ContainerFilms containerFilms);
        void mostrarListaDeFilms(ContainerFilms containerFilms);
        void darListaGenerosRecycler(ContainerGenres containerGenres);
        void mostrarMensajeFalloListaFilms();
        void mostrarMensajeFalloRetrofit();
        void mostrarListaDeFilmsSups(List<SubsMovie> listSupsFilm);
    }

    interface Presenter{
        void recibirListaMultiSearch(ContainerFilms containerFilms);
        void pedirListaMultiSearch(String nameFilm);
        void pedirListaDeFilmsPopulares();
        void recibirListaDeFilms(ContainerFilms containerFilms);
        void falloAlRecibirListaDeFilms();
        void falloConRetrofit();
        void pedirListaDeGeneros();
        void recibirListaDeGenero(ContainerGenres containerGenres);
        void falloAlRecibirListaDeGeneros();
        void pedirListaDeFilmsSups();
        void recibirListaDeFilmsSups(List<SubsMovie> listSupsFilm);
        void falloAlRecibirListaSups();
        void recibirListaConNuevaPeliculaDeLaView(List<SubsMovie> listWithNewsFilms);
        void recibirOk();
    }

    interface Interactor{
        void pedirListaMultiSearchAlServicio(String nameFilm);
        void pedirListaDePeliculasPopularesAlServicio();
        void pedirListaDeGenerosAlServicio();
        void pedirListaDeFilmsSupsAlServicio();
        void recibirListaConNuevaPleiculaParaGuardarEnFirebase(List<SubsMovie> listWithNewsFilms);


    }
}
