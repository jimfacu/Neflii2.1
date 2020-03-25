package com.example.neflii.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.neflii.DetailActivity.Entities.SubsMovie;
import com.example.neflii.HomeActivity.Utils.ImageHelper;
import com.example.neflii.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_FilmsSubs_HomeActivity extends RecyclerView.Adapter {

    private List<SubsMovie> subsMovieList;
    private CellListenerFilmsSups cellListenerFilmsSups;

    public Adapter_FilmsSubs_HomeActivity(CellListenerFilmsSups cellListenerFilmsSups) {
        this.subsMovieList = new ArrayList<>();
        this.cellListenerFilmsSups = cellListenerFilmsSups;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.mvp_cell_filmsups_home_activity, parent, false);
        FilmsSupsViewHolder viewHolder = new FilmsSupsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SubsMovie subsMovie = subsMovieList.get(position);
        FilmsSupsViewHolder filmsViewHolder = (FilmsSupsViewHolder) holder;
        filmsViewHolder.setFilms(subsMovie);
    }
    //Recibo lista de peliculas suscriptas
    public void insertFilmsSups(List<SubsMovie> listMovieSups){
        if(listMovieSups != null){
            subsMovieList.clear();
            subsMovieList.addAll(listMovieSups);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return subsMovieList.size();
    }

    class FilmsSupsViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView_FilmsSups;


        public FilmsSupsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_FilmsSups = itemView.findViewById(R.id.imageView_filmsups_ActivityHome);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellListenerFilmsSups.goToDetailFilmsSups(subsMovieList.get(getAdapterPosition()).getId());
                }
            });
        }
        public void setFilms(SubsMovie filmsups) {
           Glide.with(itemView).load("https://image.tmdb.org/t/p/w300" + filmsups.getPoster_path()).into(imageView_FilmsSups);
        }
    }
    public interface CellListenerFilmsSups{
        void goToDetailFilmsSups(int id);
    }
}
