package com.example.neflii.HomeActivity.Adapters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.neflii.HomeActivity.Entities.Films;
import com.example.neflii.HomeActivity.Entities.Genres;
import com.example.neflii.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Films_HomeActivity extends RecyclerView.Adapter {

    private List<Films> filmsList;
    private List<Films> filmsListFilter;
    private List<Genres> genresList;
    private CellListener cellListener;

    public Adapter_Films_HomeActivity(CellListener cellListener) {
        this.filmsList = new ArrayList<>();
        this.filmsListFilter = new ArrayList<>();
        this.genresList = new ArrayList<>();
        this.cellListener = cellListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.mvp_cell_home_activity, parent, false);
        FilmsViewHolder viewHolder = new FilmsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Films films = filmsList.get(position);
        FilmsViewHolder filmsViewHolder = (FilmsViewHolder) holder;
        filmsViewHolder.setFilms(films);

    }

    public void insertFilms(List<Films> listaDePeliculas) {
        if (listaDePeliculas != null) {
            filmsListFilter.clear();
            for (Films films : listaDePeliculas) {
                if (films.getTitle() != null) {
                    filmsListFilter.add(films);
                }
            }
            //Lista auxiliar completa
            filmsList.clear();
            filmsList.addAll(filmsListFilter);
            notifyDataSetChanged();
        }
    }

    public void insertGenres(List<Genres> listaDeGeneros) {
        if (listaDeGeneros != null) {
            genresList.clear();
            genresList.addAll(listaDeGeneros);
        }
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return filmsList.size();
    }

    class FilmsViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private ImageView imagen_populares;
        private TextView category;


        public FilmsViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.activityHome_title_cell);
            imagen_populares = itemView.findViewById(R.id.activityHome_imageView_cell);
            category = itemView.findViewById(R.id.activityHome_category_cell);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellListener.goToDetail(filmsList.get(getAdapterPosition()).getId());
                }
            });

        }

        public void setFilms(Films films) {
            titulo.setText(films.getTitle());
            Glide.with(itemView).load("https://image.tmdb.org/t/p/w300" + films.getBackdrop_path()).into(imagen_populares);
            imagen_populares.setColorFilter(setColorMatrix());
            category.setText(setCategoria(films));
        }

        private ColorMatrixColorFilter setColorMatrix() {

            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
            return filter;
        }

        private String setCategoria(Films films) {
            String categoria = "";
            for (Genres genres : genresList) {
                Log.d("GenreList", "el nombre de la categoria es" + genres.getName());
                if (films.getGenre_ids().get(0) == genres.getId()) {
                    categoria = genres.getName();
                }
            }
            return categoria;
        }
    }
    public interface CellListener{
        void goToDetail(int id);
    }
}
