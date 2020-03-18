package com.example.neflii.HomeActivity.Entities;

import java.util.List;

public class Films {
    private int id;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private List<Integer> genre_ids;

    public Films(int id, String title, String poster_path, String backdrop_path, List<Integer> genre_ids) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }
}
