package com.example.neflii.HomeActivity.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neflii.DetailActivity.Entities.ContainerSubsMovie;
import com.example.neflii.HomeActivity.Adapters.Adapter_FilmsMultiSearch_HomeActivity;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.HomeActivity.Entities.Films;
import com.example.neflii.R;

import java.util.HashMap;

public class MVP_HomeFragmentSearchFilm extends Fragment implements Adapter_FilmsMultiSearch_HomeActivity.CellListenerMultiSearch {

    private static final String ListMultiSearchFilms = "listMultiSearch";
    private static final String ListMultiSearchGenres = "listGenresMultiSearch";
    private static final String ListSubsMoviesFilms = "listSuubsMoviesFilms";

    private Adapter_FilmsMultiSearch_HomeActivity adapterFilmsMultiSearchHomeActivity;
    private RecyclerView recyclerViewMultiSearch;

    private ContainerFilms containerFilmsMultiSearch;
    private ContainerSubsMovie containerSubsMovie;
    private ContainerGenres containerGenres;

    //Interfaz
    private GoToDetaiSearchView goToDetaiSearchView;


    public static MVP_HomeFragmentSearchFilm buildFragmentMultiSearch(ContainerFilms containerFilms, ContainerGenres containerGenres, ContainerSubsMovie containerSubsMovie) {
        MVP_HomeFragmentSearchFilm mvpHomeFragmentSearchFilm = new MVP_HomeFragmentSearchFilm();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ListMultiSearchFilms, containerFilms);
        bundle.putParcelable(ListMultiSearchGenres,containerGenres);
        bundle.putParcelable(ListSubsMoviesFilms,containerSubsMovie);
        mvpHomeFragmentSearchFilm.setArguments(bundle);
        return mvpHomeFragmentSearchFilm;
    }


    public MVP_HomeFragmentSearchFilm() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mvp_searchfilm_fragment, container, false);

        recyclerViewMultiSearch = view.findViewById(R.id.reciclerView_Fragment);
        initRecycler(view);
        Bundle bundle = getArguments();
        if(bundle != null) {
            containerFilmsMultiSearch = bundle.getParcelable(ListMultiSearchFilms);
            containerSubsMovie = bundle.getParcelable(ListSubsMoviesFilms);
            containerGenres = bundle.getParcelable(ListMultiSearchGenres);
            adapterFilmsMultiSearchHomeActivity.insertListGenres(containerGenres);
            adapterFilmsMultiSearchHomeActivity.insertListSubsMovies(containerSubsMovie.getSubsMovieList());
            adapterFilmsMultiSearchHomeActivity.insertFilmsMultiSearch(containerFilmsMultiSearch.getResults());
        }
        return view;
    }


    private void initRecycler(View view) {
        adapterFilmsMultiSearchHomeActivity = new Adapter_FilmsMultiSearch_HomeActivity(this,getContext());
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMultiSearch.addItemDecoration(new DividerItemDecoration(recyclerViewMultiSearch.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewMultiSearch.setLayoutManager(linearLayoutManager);
        recyclerViewMultiSearch.setAdapter(adapterFilmsMultiSearchHomeActivity);

    }

    //Ir al detalle de la pelicula desde el fragment multi search
    @Override
    public void detailMultiSearch(int ID) {
        goToDetaiSearchView.goToDetailViewSearch(ID);
    }

    //Agregar pelicula desde el fragment multi search
    @Override
    public void addFilmSups(Films film) {
        goToDetaiSearchView.addFilmToSups(film);
    }

    //On Attach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.goToDetaiSearchView = (GoToDetaiSearchView) context;
    }

    public interface GoToDetaiSearchView {
        void goToDetailViewSearch(int id);
        void addFilmToSups(Films film);
    }

}
