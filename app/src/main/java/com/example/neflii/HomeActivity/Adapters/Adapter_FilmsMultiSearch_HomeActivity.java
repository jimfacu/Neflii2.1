package com.example.neflii.HomeActivity.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Entities.Films;
import com.example.neflii.HomeActivity.Entities.Genres;
import com.example.neflii.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_FilmsMultiSearch_HomeActivity extends RecyclerView.Adapter {

    private List<Films> filmsList;
    private List<Films> tankFilmsList;
    private List<Genres> genresListMultiSearch;
    private List<SubsMovie> subsListMovies;
    private CellListenerMultiSearch cellListenerMultiSearch;

    public Adapter_FilmsMultiSearch_HomeActivity(CellListenerMultiSearch cellListenerMultiSearch) {
        this.filmsList = new ArrayList<>();
        this.tankFilmsList = new ArrayList<>();
        this.genresListMultiSearch = new ArrayList<>();
        this.subsListMovies = new ArrayList<>();
        this.cellListenerMultiSearch = cellListenerMultiSearch;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.mvp_cell_filmsmultisearch_activity_home, parent, false);
        FilmsMultiSearch viewHolder = new FilmsMultiSearch(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Films films = filmsList.get(position);
        FilmsMultiSearch filmsMultiSearch = (FilmsMultiSearch) holder;
        filmsMultiSearch.setFilms(films);

    }

    //Recibimos lista de Generos
    public void insertListGenresMultiSearch(List<Genres> genresList){
        if(genresList!= null){
            genresListMultiSearch.clear();
            genresListMultiSearch.addAll(genresList);
            notifyDataSetChanged();
        }
    }

    //Recibimos lista de peliculas suscriptas
    public void insertListSubsMovies(List<SubsMovie> subsMovieList){
        if(subsMovieList != null){
            subsListMovies.clear();
            subsListMovies.addAll(subsMovieList);
            notifyDataSetChanged();
        }
    }

    //Recibimos lista multi search
    public void insertFilmsMultiSearch(List<Films> listaDePeliculas) {
        if (listaDePeliculas != null) {
            tankFilmsList.clear();
            for(Films films: listaDePeliculas){
                //Eliminamos peliculas sin titulo o imagen
                if(films.getTitle()!= null & films.getPoster_path() != null ){
                    if(films.getGenre_ids().size()!=0){
                        tankFilmsList.add(films);
                    }
                }
            }
            filmsList.clear();
            filmsList.addAll(tankFilmsList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return filmsList.size();
    }


    class FilmsMultiSearch extends RecyclerView.ViewHolder {
        private ImageView imageView_FilmsMultiSearch;
        private TextView textView_FilmsMultiSearch;
        private TextView textView_filmsMultiSearchCategory;
        private Button button_AddSups;

        public FilmsMultiSearch(@NonNull View itemView) {
            super(itemView);
            imageView_FilmsMultiSearch = itemView.findViewById(R.id.image_filmsMultiSearch);
            textView_FilmsMultiSearch = itemView.findViewById(R.id.tituloFilmsMultiSearch);
            textView_filmsMultiSearchCategory = itemView.findViewById(R.id.category_MultiSearch);
            button_AddSups = itemView.findViewById(R.id.button_AddFilm);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellListenerMultiSearch.detailMultiSearch(filmsList.get(getAdapterPosition()).getId());
                }
            });

            button_AddSups.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    cellListenerMultiSearch.addFilmSups(filmsList.get(getAdapterPosition()));
                    button_AddSups.setText("Agregado");
                    button_AddSups.setTextColor(R.color.negro);
                    button_AddSups.setBackgroundResource(R.color.Blanco);
                }
            });

        }
        @SuppressLint("ResourceAsColor")
        public void setFilms(Films films){
            textView_FilmsMultiSearch.setText(films.getTitle());
            textView_filmsMultiSearchCategory.setText(setCategoria(films));
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w300" + films.getPoster_path()).into(imageView_FilmsMultiSearch);
            if(chekeoSups(films)){
                button_AddSups.setText("Agregado");
                button_AddSups.setTextColor(R.color.negro);
                button_AddSups.setBackgroundResource(R.color.Blanco);
            }
        }
        private String setCategoria(Films films) {
            String categoria = "";
            for (Genres genres : genresListMultiSearch) {
                if (films.getGenre_ids().get(0) == genres.getId()) {
                    categoria = genres.getName();
                }
            }
            return categoria;
        }

        //Chequeo si la pelicula ya esta en mi lista de suscriptas
        private boolean chekeoSups(Films films){
            boolean ok = false;
            for(SubsMovie subsMovie : subsListMovies){
                if(subsMovie.getId() == films.getId()){
                    ok = true;
                }
            }
            return ok;
        }
    }
    public interface CellListenerMultiSearch{
        void detailMultiSearch(int ID);
        void addFilmSups(Films film);
    }

}
