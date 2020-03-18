package com.example.neflii.DetailActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    private CardView button_Suscripcion;
    private Movie movieNow;

    private DatabaseReference reference;

    public static final String ID_Movie = "ID_MOVIE";
    private int id_Movie;

  

    @BindView(R.id.textOfOverview)
    TextView textView_textOfOverView;

    @BindView(R.id.constraint)
    ConstraintLayout constraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_detail_activity);
        ButterKnife.bind(this);
        presenter = new MVPPresenter_DetailActivity(this);
        initFirebase();
        reciveListSups();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id_Movie = bundle.getInt(ID_Movie);
        }
        requestMovie();
        button_Suscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilmOnSups();
            }
        });


    }
    private void reciveListSups() {
        presenter.pedirListaDeSubsAlServicio();
    }

    private void requestMovie(){
        presenter.pedirPeliculaMendianteID(id_Movie);

    }

    @Override
    public void mostrarDetallePelicula(Movie movie) {
        movieNow = movie;
        textView_tituloDetail.setText(movie.getTitle());
        textView_textOfOverView.setText(movie.getOverview());
        textView_anioDetail.setText(movie.getRelease_date().substring(0,4));
        Glide.with(this).load("https://image.tmdb.org/t/p/original" + movie.getPoster_path()).into(imageView_portada);
    }

    @Override
    public void setearListaDeFilms(List<SubsMovie> subsMovieList) {
        tankCollectionSubsMovies.clear();
        if(subsMovieList != null){
            tankCollectionSubsMovies.addAll(subsMovieList);
        }

    }

    @Override
    public void falloAlDescargarListaDeFilms() {
        Toast.makeText(this, "Fallo descarga de lista de films", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void recibirOkDelPresenter() {
        Toast.makeText(this, "Se subio la info", Toast.LENGTH_SHORT).show();

    }

    private void setFilmOnSups(){
        boolean exist = false;
        collectionSubsMovies.clear();
        collectionSubsMovies.addAll(tankCollectionSubsMovies);
        SubsMovie newFilm = new SubsMovie(movieNow.getTitle(),movieNow.getId(),movieNow.getPoster_path(),movieNow.getBackdrop_path());
        for(SubsMovie films : collectionSubsMovies){
            if (films.getTitle().equals(newFilm.getTitle())){
                exist = true;
            }
        }
        if(exist){
            Toast.makeText(this, "Ya supscripto a esta pelicula !!", Toast.LENGTH_SHORT).show();
        }else{
            collectionSubsMovies.add(newFilm);
        }
        tankCollectionSubsMovies.clear();
        tankCollectionSubsMovies.addAll(collectionSubsMovies);
    }

    private void initFirebase(){
        button_Suscripcion = findViewById(R.id.suscribirse);
        tankCollectionSubsMovies = new ArrayList<>();
        collectionSubsMovies = new ArrayList<>();
    }
}