package com.example.neflii.HomeActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neflii.HomeActivity.Entities.Films;
import com.example.neflii.HomeActivity.Utils.ImageHelper;
import com.example.neflii.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_FilmsMultiSearch_HomeActivity extends RecyclerView.Adapter {

    private List<Films> filmsList;

    public Adapter_FilmsMultiSearch_HomeActivity() {
        this.filmsList = new ArrayList<>();
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
    public void insertFilmsMultiSearch(List<Films> listaDePeliculas) {
        if (listaDePeliculas != null) {
            filmsList.clear();
            filmsList.addAll(listaDePeliculas);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return filmsList.size();
    }

    class FilmsMultiSearch extends RecyclerView.ViewHolder {
        private ImageView imageView_FilmsSups;
        private CardView cardViewFilmsSups;
        private ImageHelper imageHelper;

        public FilmsMultiSearch(@NonNull View itemView) {
            super(itemView);
        }
        public void setFilms(Films films){

        }
    }



}
