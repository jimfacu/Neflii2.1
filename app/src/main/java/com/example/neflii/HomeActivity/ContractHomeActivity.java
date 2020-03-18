package com.example.neflii.HomeActivity;

import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;

public interface ContractHomeActivity {

    interface View{
        void mostrarListaDeFilms(ContainerFilms containerFilms);
        void darListaGenerosRecycler(ContainerGenres containerGenres);
        void mostrarMensajeFalloListaFilms();
        void mostrarMensajeFalloRetrofit();
    }

    interface Presenter{
        void pedirListaDeFilmsPopulares();
        void recibirListaDeFilms(ContainerFilms containerFilms);
        void falloAlRecibirListaDeFilms();
        void falloConRetrofit();
        void pedirListaDeGeneros();
        void recibirListaDeGenero(ContainerGenres containerGenres);
        void falloAlRecibirListaDeGeneros();
    }

    interface Interactor{
        void pedirListaDePeliculasPopularesAlServicio();
        void pedirListaDeGenerosAlServicio();


    }
}
