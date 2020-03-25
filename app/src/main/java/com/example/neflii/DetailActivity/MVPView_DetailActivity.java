package com.example.neflii.DetailActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.neflii.DetailActivity.Entities.Movie;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MVPView_DetailActivity extends AppCompatActivity implements Contract_DetailActivity.View {

    private Contract_DetailActivity.Presenter presenter;

    private List<SubsMovie> tankCollectionSubsMovies;
    private List<SubsMovie> collectionSubsMovies;
    private Movie movieNow;

    public static final String ID_Movie = "ID_MOVIE";
    private int id_Movie;

    @BindView(R.id.portada)
    ImageView imageView_portada;

    @BindView(R.id.tituloDetail)
    TextView textView_tituloDetail;

    @BindView(R.id.añoDetail)
    TextView textView_anioDetail;

    @BindView(R.id.textOfOverview)
    TextView textView_textOfOverView;

    @BindView(R.id.constraint)
    ConstraintLayout constraintLayout;

    @BindView(R.id.backToHomeActivity)
    ImageView imageView_BackToHome;

    @BindView(R.id.suscribirse)
    CardView button_Suscripcion;

    @BindView(R.id.textView_Suscripcion)
    TextView textView_suscripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_detail_activity);
        ButterKnife.bind(this);
        presenter = new MVPPresenter_DetailActivity(this);
        initFirebase();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_Movie = bundle.getInt(ID_Movie);
        }
        button_Suscripcion.setOnClickListener(suscripcionListener);
        peticionesDeListas();
        backToHome();
    }

    //Inicializamos Listas y vistas
    private void initFirebase() {
        button_Suscripcion = findViewById(R.id.suscribirse);
        tankCollectionSubsMovies = new ArrayList<>();
        collectionSubsMovies = new ArrayList<>();
    }

    //Peticiones de listas
    private void peticionesDeListas(){
        pedirListaDePeliculasSuscriptas();
        pedirPeliculaAlServicio();
    }

    private void pedirListaDePeliculasSuscriptas() {
        presenter.pedirListaDePeliculasSuscriptasAlServicio();
    }

    private void pedirPeliculaAlServicio() {
        presenter.pedirPeliculaMendianteIDAlServicio(id_Movie);
    }

    //Guardamos la lista de peliculas suscriptas en la lista auxiliar
    @Override
    public void setearListaDeFilms(List<SubsMovie> subsMovieList) {
        tankCollectionSubsMovies.clear();
        if (subsMovieList != null) {
            tankCollectionSubsMovies.addAll(subsMovieList);
        }
    }



    //Mostramos el detalle de la pelicula
    @SuppressLint("ResourceAsColor")
    @Override
    public void mostrarDetallePelicula(Movie movie) {
        movieNow = movie;
        textView_tituloDetail.setText(movie.getTitle());
        textView_textOfOverView.setText(movie.getOverview());
        textView_anioDetail.setText(movie.getRelease_date().substring(0, 4));
        Glide.with(this).load("https://image.tmdb.org/t/p/original" + movie.getPoster_path()).into(imageView_portada);
    }

    //Guardamos la pelicula , si estamos suscriptos , mostramos mensaje
    private void setFilmOnSups() {
        boolean ok = false;
        collectionSubsMovies.clear();
        collectionSubsMovies.addAll(tankCollectionSubsMovies);
        SubsMovie newFilm = new SubsMovie(movieNow.getTitle(), movieNow.getId(), movieNow.getPoster_path(), movieNow.getBackdrop_path());
        for (SubsMovie film : collectionSubsMovies) {
            if (film.getTitle().equals(newFilm.getTitle())) {
                ok = true;
            }
        }
        if (ok) {
            Toast.makeText(this, getResources().getString(R.string.ya_estas_suscripto_a_esta_pelicula), Toast.LENGTH_SHORT).show();
        } else {
            collectionSubsMovies.add(newFilm);
            tankCollectionSubsMovies.clear();
            tankCollectionSubsMovies.addAll(collectionSubsMovies);
            presenter.recibirListaConNuevaPelicula(tankCollectionSubsMovies);
        }
    }

    //Mensaje de errores
    @Override
    public void falloAlDescargarListaDePeliculasSuscriptas() {
        Toast.makeText(this,getResources().getString(R.string.Fallo_al_recibir_lista_de_peliculas_suscriptas), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void falloConRetrofitPeliculasSuscriptas() {
        Toast.makeText(this, getResources().getString(R.string.Fallo_de_Retrofit_lista_de_peliculas_suscriptas), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void falloAlRecibirPeliculaMedianteID() {
        Toast.makeText(this, getResources().getString(R.string.Fallo_descarga_de_pelicula_mediante_id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void falloConRetrofitPeliculaMedianteID() {
        Toast.makeText(this, getResources().getString(R.string.Fallo_con_retrofit_pelicula_mediante_ID), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void recibirOkDelPresenter() {
        Toast.makeText(this,getResources().getString(R.string.Exito_al_añadir_la_nueva_Pelicula), Toast.LENGTH_SHORT).show();
    }
    View.OnClickListener suscripcionListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilmOnSups();
                }
            };

    private void backToHome(){
        imageView_BackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MVPView_DetailActivity.super.onBackPressed();
            }
        });
    }
}


