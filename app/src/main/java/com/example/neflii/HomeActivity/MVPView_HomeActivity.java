package com.example.neflii.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.neflii.DetailActivity.MVPView_DetailActivity;
import com.example.neflii.HomeActivity.Adapters.Adapter_Films_HomeActivity;
import com.example.neflii.HomeActivity.Entities.ContainerFilms;
import com.example.neflii.HomeActivity.Entities.ContainerGenres;
import com.example.neflii.R;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

import static com.example.neflii.DetailActivity.MVPView_DetailActivity.ID_Movie;

public class MVPView_HomeActivity extends AppCompatActivity implements ContractHomeActivity.View , Adapter_Films_HomeActivity.CellListener {

    private Adapter_Films_HomeActivity adapterFilmsHomeActivity;
    private ContractHomeActivity.Presenter presenterHomeActivity;

    @BindView(R.id.activityHome_editText_SearchMovie)
    EditText editTextSearchMovie;

    @BindView(R.id.activityHome_lupa)
    ImageView imageViewLupa;

    @BindView(R.id.activityHome_recyclerView)
    RecyclerView recyclerViewListFilms;

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
        reciveFilms();
        reciveGenres();
    }

    private void initRecycler(){
        adapterFilmsHomeActivity = new Adapter_Films_HomeActivity(this);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewListFilms.setLayoutManager(linearLayoutManager);
        recyclerViewListFilms.setAdapter(adapterFilmsHomeActivity);
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
    public void goToDetail(int id) {
        Intent intent = new Intent(this, MVPView_DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ID_Movie,id);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
