package com.example.neflii.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.neflii.DetailActivity.Entities.ContainerSubsMovie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.DetailActivity.MVPView_DetailActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_FilmsSubs_HomeActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_Films_HomeActivity;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.HomeActivity.Entities.Films;
import com.example.neflii.HomeActivity.Entities.Genres;
import com.example.neflii.HomeActivity.Fragments.MVP_HomeFragmentSearchFilm;
import com.example.neflii.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.neflii.DetailActivity.MVPView_DetailActivity.ID_Movie;

public class MVPView_HomeActivity extends AppCompatActivity implements ContractHomeActivity.View , Adapter_Films_HomeActivity.CellListener, Adapter_FilmsSubs_HomeActivity.CellListenerFilmsSups , MVP_HomeFragmentSearchFilm.GoToDetaiSearchView {

    private Adapter_Films_HomeActivity adapterFilmsHomeActivity;
    private ContractHomeActivity.Presenter presenterHomeActivity;
    private Adapter_FilmsSubs_HomeActivity adapterFilmsSubsHomeActivity;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MVP_HomeFragmentSearchFilm mvpHomeFragmentSearchFilm;

    private MenuItem mSearch;
    private SearchView mSearchView;
    private Toolbar toolbar;

    private List<SubsMovie> subsMovieList;
    private ContainerGenres containerGenresList;
    private ContainerSubsMovie containerSubsMovie;
    private ContainerSubsMovie tankContainerSubsMovie;
    private List<SubsMovie> tankListAddFilm;
    private List<SubsMovie> listOfMoviesSups;



    @BindView(R.id.activityHome_recyclerView)
    RecyclerView recyclerViewListFilms;

    @BindView(R.id.activityHome_recyclerViewFilmsSups)
    RecyclerView recyclerViewListFilmsSubs;

    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_home_activity);
        ButterKnife.bind(this);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reciveGenres();
                swipe.setRefreshing(false);
            }
        });
        presenterHomeActivity = new MVPPresenter_HomeActivity(this);
        initLists();
        initRecycler();
        setToolbar();
        reciveFilmsSups();
        reciveFilms();
        reciveGenres();

    }

    private void initLists() {
        subsMovieList = new ArrayList<>();
        tankListAddFilm = new ArrayList<>();
        listOfMoviesSups = new ArrayList<>();
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            presenterHomeActivity.pedirListaMultiSearch(s);
            return false;
        }
    };


    private void reciveFilmsSups() {
        presenterHomeActivity.pedirListaDeFilmsSups();
    }

    private void initRecycler() {

        //RecyclerView de las peliculas mas populares
        adapterFilmsHomeActivity = new Adapter_Films_HomeActivity(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewListFilms.setLayoutManager(linearLayoutManager);
        recyclerViewListFilms.setAdapter(adapterFilmsHomeActivity);

        //RecyclerView de las peliculas supscriptas
        adapterFilmsSubsHomeActivity = new Adapter_FilmsSubs_HomeActivity(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewListFilmsSubs.setLayoutManager(linearLayoutManager1);
        recyclerViewListFilmsSubs.setAdapter(adapterFilmsSubsHomeActivity);
    }

    public void reciveFilms() {
        presenterHomeActivity.pedirListaDeFilmsPopulares();
    }

    public void reciveGenres() {
        presenterHomeActivity.pedirListaDeGeneros();
    }


    @Override
    public void mostrarListaMultiSearch(ContainerFilms containerFilms) {
        if (containerFilms != null) {
            mvpHomeFragmentSearchFilm = MVP_HomeFragmentSearchFilm.buildFragmentPetDetail(containerFilms, containerGenresList,containerSubsMovie);
            setFragment(mvpHomeFragmentSearchFilm);
        }

    }

    @Override
    public void mostrarListaDeFilms(ContainerFilms containerFilms) {
        adapterFilmsHomeActivity.insertFilms(containerFilms.getResults());
    }

    @Override
    public void darListaGenerosRecycler(ContainerGenres containerGenres) {
        adapterFilmsHomeActivity.insertGenres(containerGenres.getGenres());
        containerGenresList = containerGenres;
        containerSubsMovie = tankContainerSubsMovie;
    }

    @Override
    public void mostrarMensajeFalloListaFilms() {
        Toast.makeText(this, "Fallo al bajar la lista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensajeFalloRetrofit() {
        Toast.makeText(this, "Fallo con Retrofit", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void mostrarListaDeFilmsSups(List<SubsMovie> listSupsFilm) {
        if(listSupsFilm != null) {
            subsMovieList.clear();
            tankListAddFilm.clear();
            subsMovieList.addAll(listSupsFilm);
            tankListAddFilm.addAll(listSupsFilm);
            adapterFilmsSubsHomeActivity.insertFilmsSups(listSupsFilm);
            containerSubsMovie = new ContainerSubsMovie(listSupsFilm);

            }
        }


    @Override
    public void goToDetail(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie, id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void goToDetailFilmsSups(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie, id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    private void setToolbar() {
        toolbar = findViewById(R.id.noFakeToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(R.color.LetrasBuscador_homeActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        mSearch = menu.findItem(R.id.searchView_BuscadorDeFilm_activityHome);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(onQueryTextListener);
        mSearchView.setQueryHint("Ingrese nombre de pelicula a Buscar");
        return super.onCreateOptionsMenu(menu);
    }

    private void setFragment(MVP_HomeFragmentSearchFilm fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityHome_ConteinerFragment, fragment);
        fragmentTransaction.commit();
    }

    private boolean collapseActionView() {
        boolean open=false;
        if(mSearchView.isIconified()){
            open = true;
        }
        return open;
    }

    @Override
    public void onBackPressed() {
        if (!collapseActionView()) {
            removeFragment();
        }
    }

    @Override
    public void goToDetailViewSearch(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie, id);
        intent.putExtras(bundle);
        removeFragment();
        startActivity(intent);
    }

    @Override
    public void addFilmToSups(Films film) {
        setFilmOnSups(film);
    }

    //Guardamos la pelicula en firebase
    private void setFilmOnSups(Films film){
        boolean ok = false;
        listOfMoviesSups.clear();
        listOfMoviesSups.addAll(tankListAddFilm);
        SubsMovie newFilm = new SubsMovie(film.getTitle(),film.getId(),film.getPoster_path(),film.getBackdrop_path());
        for(SubsMovie films : listOfMoviesSups){
            if(films.getTitle().equals(newFilm.getTitle())){
                ok = true;
            }
        }
        if(ok){
            Toast.makeText(this, "ya estas suscripto a esta pelicula", Toast.LENGTH_SHORT).show();
        }else{
            listOfMoviesSups.add(newFilm);
            tankListAddFilm.clear();
            tankListAddFilm.addAll(listOfMoviesSups);
            adapterFilmsSubsHomeActivity.insertFilmsSups(tankListAddFilm);
            presenterHomeActivity.recibirListaConNuevaPeliculaDeLaView(tankListAddFilm);
        }

    }



    private void removeFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mvpHomeFragmentSearchFilm);
        fragmentTransaction.commit();
    }


}


