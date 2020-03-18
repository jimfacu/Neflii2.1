package com.example.neflii.HomeActivity;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;

import java.util.List;

public interface ContractHomeActivity {

    interface View{
        void mostrarListaDeFilms(ContainerFilms containerFilms);
        void darListaGenerosRecycler(ContainerGenres containerGenres);
        void mostrarMensajeFalloListaFilms();
        void mostrarMensajeFalloRetrofit();
        void mostrarListaDeFilmsSups(List<SubsMovie> listSupsFilm);
    }

    interface Presenter{
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
    }

    interface Interactor{
        void pedirListaDePeliculasPopularesAlServicio();
        void pedirListaDeGenerosAlServicio();
        void pedirListaDeFilmsSupsAlServicio();


    }
}
