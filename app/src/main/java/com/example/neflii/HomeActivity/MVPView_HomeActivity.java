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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.DetailActivity.MVPView_DetailActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_FilmsSubs_HomeActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_Films_HomeActivity;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.neflii.DetailActivity.MVPView_DetailActivity.ID_Movie;

public class MVPView_HomeActivity extends AppCompatActivity implements ContractHomeActivity.View , Adapter_Films_HomeActivity.CellListener, Adapter_FilmsSubs_HomeActivity.CellListenerFilmsSups {

    private Adapter_Films_HomeActivity adapterFilmsHomeActivity;
    private ContractHomeActivity.Presenter presenterHomeActivity;
    private Adapter_FilmsSubs_HomeActivity adapterFilmsSubsHomeActivity;

    private MenuItem mSearch;
    private SearchView mSearchView;
    private Toolbar toolbar;

    private List<SubsMovie> subsMovieList = new ArrayList<>();

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
        initRecycler();
        setToolbar();
        reciveFilmsSups();
        reciveFilms();
        reciveGenres();

    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    private void reciveFilmsSups() {
        presenterHomeActivity.pedirListaDeFilmsSups();
    }

    private void initRecycler(){

        //RecyclerView de las peliculas mas populares
        adapterFilmsHomeActivity = new Adapter_Films_HomeActivity(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewListFilms.setLayoutManager(linearLayoutManager);
        recyclerViewListFilms.setAdapter(adapterFilmsHomeActivity);

        //RecyclerView de las peliculas supscriptas
        adapterFilmsSubsHomeActivity = new Adapter_FilmsSubs_HomeActivity(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewListFilmsSubs.setLayoutManager(linearLayoutManager1);
        recyclerViewListFilmsSubs.setAdapter(adapterFilmsSubsHomeActivity);
    }

    public void reciveFilms(){
        presenterHomeActivity.pedirListaDeFilmsPopulares();
    }

    public void reciveGenres(){
        presenterHomeActivity.pedirListaDeGeneros();
    }

    @Override
    public void mostrarListaDeFilms(ContainerFilms containerFilms) {
        adapterFilmsHomeActivity.insertFilms(containerFilms.getResults());

    }

    @Override
    public void darListaGenerosRecycler(ContainerGenres containerGenres){
        adapterFilmsHomeActivity.insertGenres(containerGenres.getGenres());

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
        subsMovieList.clear();
        subsMovieList.addAll(listSupsFilm);
        adapterFilmsSubsHomeActivity.insertFilmsSups(listSupsFilm);
    }

    @Override
    public void goToDetail(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie,id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void goToDetailFilmsSups(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie,id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    private void setToolbar(){
        toolbar = findViewById(R.id.noFakeToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(R.color.LetrasBuscador_homeActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items,menu);
        mSearch = menu.findItem(R.id.searchView_BuscadorDeFilm_activityHome);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(onQueryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

}
