package com.example.neflii.HomeActivity.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.neflii.HomeActivity.Adapters.Adapter_FilmsMultiSearch_HomeActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_Films_HomeActivity;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.R;

public class MVP_HomeFragmentSearchFilm extends Fragment {

    private static final String ListMultiSearchFilms = "listMultiSearch";
    private Adapter_FilmsMultiSearch_HomeActivity adapterFilmsMultiSearchHomeActivity;
    private RecyclerView recyclerViewMultiSearch;
    private ContainerFilms containerFilmsMultiSearch;

    public static MVP_HomeFragmentSearchFilm buildFragmentPetDetail(ContainerFilms containerFilms) {
        MVP_HomeFragmentSearchFilm mvpHomeFragmentSearchFilm = new MVP_HomeFragmentSearchFilm();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ListMultiSearchFilms, containerFilms);
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
            adapterFilmsMultiSearchHomeActivity.insertFilmsMultiSearch(containerFilmsMultiSearch.getResults());
        }
        return view;
    }

    private void initRecycler(View view) {

        //RecyclerView de las peliculas mas populares
        adapterFilmsMultiSearchHomeActivity = new Adapter_FilmsMultiSearch_HomeActivity();
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMultiSearch.setLayoutManager(linearLayoutManager);
        recyclerViewMultiSearch.setAdapter(adapterFilmsMultiSearchHomeActivity);

    }
}
