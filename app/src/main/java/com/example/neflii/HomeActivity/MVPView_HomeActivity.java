package com.example.neflii.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
    private List<SubsMovie> tankListAddFilm;
    private List<SubsMovie> listOfMoviesSups;
    private int mostrarFragmenten=0;



    @BindView(R.id.activityHome_recyclerView)
    RecyclerView recyclerViewListFilms;

    @BindView(R.id.activityHome_recyclerViewFilmsSups)
    RecyclerView recyclerViewListFilmsSubs;

    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipe;

    @BindView(R.id.progressBarMainActivity)
    ProgressBar progressBar_MainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_home_activity);
        ButterKnife.bind(this);
        initLists();
        initRecycler();
        setToolbar();
        setSwipe();
        presenterHomeActivity = new MVPPresenter_HomeActivity(this,this);
        peticionDeListas();

    }
    // Inicializo las listas
    private void initLists() {
        subsMovieList = new ArrayList<>();
        tankListAddFilm = new ArrayList<>();
        listOfMoviesSups = new ArrayList<>();
    }

    //Inicializo los recycler
    private void initRecycler() {
        //RecyclerView de las peliculas mas populares
        adapterFilmsHomeActivity = new Adapter_Films_HomeActivity(this,this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewListFilms.setLayoutManager(linearLayoutManager);
        recyclerViewListFilms.setAdapter(adapterFilmsHomeActivity);

        //RecyclerView de las peliculas supscriptas
        adapterFilmsSubsHomeActivity = new Adapter_FilmsSubs_HomeActivity(this,this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewListFilmsSubs.setLayoutManager(linearLayoutManager1);
        recyclerViewListFilmsSubs.setAdapter(adapterFilmsSubsHomeActivity);
    }

    //Toolbar y Menu
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
        mSearchView.setQueryHint(getResources().getString(R.string.Ingrese_nombre_de_pelicula_a_Buscar));
        return super.onCreateOptionsMenu(menu);
    }

    //SearchView!
    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            //Existe la variable mostrarFragment ya que la primera vez va a remover un fragment que todavia no esta seteado
            if(s.length() == 0 & mostrarFragmenten > 0){
                 removeFragment();
                 mostrarFragmenten = 0;
             }else {
                mostrarFragmenten = 1;
                presenterHomeActivity.pedirListaMultiSearch(s);
            }
            return false;
        }
    };
    //Swipe !
    private void setSwipe(){
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
             public void onRefresh() {
                peticionDeListas();
                swipe.setRefreshing(false);
         }
     });
    }

    //Peticion de listas para la HomeActivity
    private void peticionDeListas(){
            progressBar_MainActivity.setVisibility(View.VISIBLE);
            presenterHomeActivity.pedirListaDeFilmsPopulares();
            presenterHomeActivity.pedirListaDeFilmsSups();
            presenterHomeActivity.pedirListaDeGeneros();
    }


    //Mostrar las listas recibidas
    @Override
    public void mostrarListaMultiSearch(ContainerFilms containerFilms) {
        if (containerFilms != null) {
            mvpHomeFragmentSearchFilm = MVP_HomeFragmentSearchFilm.buildFragmentMultiSearch(containerFilms,containerGenresList,containerSubsMovie);
            setFragment(mvpHomeFragmentSearchFilm);
        }
    }

    @Override
    public void mostrarListaDeFilms(ContainerFilms containerFilms) {
        if(progressBar_MainActivity.isShown()){
            progressBar_MainActivity.setVisibility(View.GONE);
        }
        adapterFilmsHomeActivity.insertFilms(containerFilms.getResults());
    }

    @Override
    public void mostrarListaDeFilmsSups(List<SubsMovie> listSupsFilm) {
        if(progressBar_MainActivity.isShown()){
            progressBar_MainActivity.setVisibility(View.GONE);
        }
        if(listSupsFilm != null) {
            subsMovieList.clear();
            tankListAddFilm.clear();
            subsMovieList.addAll(listSupsFilm);
            tankListAddFilm.addAll(listSupsFilm);
            adapterFilmsSubsHomeActivity.insertFilmsSups(listSupsFilm);
            //Guardamos la lista recibida en el container de peliculas suscriptas para mandarsela al fragment
            containerSubsMovie = new ContainerSubsMovie(listSupsFilm);
        }
    }

    @Override
    public void darListaGenerosRecycler(ContainerGenres containerGenres) {
        adapterFilmsHomeActivity.insertGenres(containerGenres.getGenres());
        //Guardamos la lista recibida en el container de generos para mandarsela al fragment
        containerGenresList = containerGenres;
    }


    //Ir al detalle de la pelicula seleccionada mediante una ID
    @Override
    public void goToDetail(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie, id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //Ir al detalle de la pelicula suscripta mediante una ID
    @Override
    public void goToDetailFilmsSups(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie, id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //Ir al detalle de la pelicula buscada mediante un ID
    @Override
    public void goToDetailViewSearch(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie, id);
        intent.putExtras(bundle);
        removeFragment();
        startActivity(intent);
    }

    //Guardamos la pelicula en firebase
    private void setFilmOnSups(Films film){
        boolean estoysuscripto = false;
        listOfMoviesSups.clear();
        listOfMoviesSups.addAll(tankListAddFilm);
        SubsMovie newFilm = new SubsMovie(film.getTitle(),film.getId(),film.getPoster_path(),film.getBackdrop_path());
        for(SubsMovie films : listOfMoviesSups){
            if(films.getId() == newFilm.getId()){
                estoysuscripto = true;
            }
        }
        if(estoysuscripto){
            Toast.makeText(this, getResources().getString(R.string.ya_estas_suscripto_a_esta_pelicula), Toast.LENGTH_SHORT).show();
        }else{
            listOfMoviesSups.add(newFilm);
            tankListAddFilm.clear();
            tankListAddFilm.addAll(listOfMoviesSups);
            adapterFilmsSubsHomeActivity.insertFilmsSups(tankListAddFilm);
            presenterHomeActivity.recibirListaConNuevaPeliculaDeLaView(tankListAddFilm);
        }
    }

    //Interfaz del Fragment
    //Agregar una pelicula a la lista de suscriptas seleccionada en el fragment
    @Override
    public void addFilmToSups(Films film) {
        setFilmOnSups(film);
    }

    //Creamos el fragment del buscador de peliculas
    private void setFragment(MVP_HomeFragmentSearchFilm fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityHome_ConteinerFragment, fragment);
        fragmentTransaction.commit();
    }

    //Removemos el fragment creado , si tenemos mas de uno le llegaria por parametro cual fragment quiere remover
    private void removeFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mvpHomeFragmentSearchFilm);
        fragmentTransaction.commit();
    }

    //Cuando el search view se cierra , removemos el fragment , puede mejorar
    @Override
    public void onBackPressed() {
        finish();
    }
    //Mensaje de error
    @Override
    public void mostrarMensajeDeFallo(String s) {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensajeDePeliculaAgregada() {
        Toast.makeText(this, getResources().getString(R.string.Exito_al_añadir_la_nueva_Pelicula), Toast.LENGTH_SHORT).show();
    }
}