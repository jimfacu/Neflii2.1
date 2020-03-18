package com.example.neflii.HomeActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.DetailActivity.MVPView_DetailActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_FilmsSubs_HomeActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_Films_HomeActivity;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.R;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

import static com.example.neflii.DetailActivity.MVPView_DetailActivity.ID_Movie;

public class MVPView_HomeActivity extends AppCompatActivity implements ContractHomeActivity.View , Adapter_Films_HomeActivity.CellListener, Adapter_FilmsSubs_HomeActivity.CellListenerFilmsSups {

    private Adapter_Films_HomeActivity adapterFilmsHomeActivity;
    private ContractHomeActivity.Presenter presenterHomeActivity;
    private Adapter_FilmsSubs_HomeActivity adapterFilmsSubsHomeActivity;
    private Boolean upLoadList = false;

    private List<SubsMovie> subsMovieList = new ArrayList<>();

    @BindView(R.id.activityHome_editText_SearchMovie)
    EditText editTextSearchMovie;

    @BindView(R.id.activityHome_lupa)
    ImageView imageViewLupa;

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

        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reciveGenres();
                swipe.setRefreshing(false);
            }
        });
        presenterHomeActivity = new MVPPresenter_HomeActivity(this);
        initRecycler();
        reciveFilmsSups();
        reciveFilms();
        reciveGenres();
    }

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
}
