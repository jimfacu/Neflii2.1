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
import java.util.HashMap;
import java.util.List;

public class Adapter_Films_HomeActivity extends RecyclerView.Adapter {

    private List<Films> filmsList;
    private List<Films> filmsListFilter;
    private HashMap<Integer,String> mapGenres;
    private Context context;
    private CellListener cellListener;

    public Adapter_Films_HomeActivity(CellListener cellListener, Context context) {
        this.filmsList = new ArrayList<>();
        this.filmsListFilter = new ArrayList<>();
        this.mapGenres = new HashMap<>();
        this.context = context;
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

    //Recibimos lista de peliculas populares
    public void insertFilms(List<Films> listaDePeliculas) {
        if (listaDePeliculas != null) {
            filmsListFilter.clear();
            //Eliminamos las peliculas sin titulo
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

    //Recibimos lista de generos
    public void insertGenres(List<Genres> listaDeGeneros) {
        if (listaDeGeneros != null) {
           for(Genres genres :listaDeGeneros){
               mapGenres.put(genres.getId(),genres.getName());
           }
        }
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
            Glide.with(itemView).load(context.getResources().getString(R.string.Poster_MultiSearch_300) + films.getBackdrop_path()).into(imagen_populares);
            imagen_populares.setColorFilter(setColorMatrix());
            category.setText(mapGenres.get(films.getGenre_ids().get(0)));
        }

        //Metodo para ponerle el contraste a las vistas del Recycler
        private ColorMatrixColorFilter setColorMatrix() {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
            return filter;
        }
    }

    //Interfaz del recycler
    public interface CellListener{
        void goToDetail(int id);
    }
}
