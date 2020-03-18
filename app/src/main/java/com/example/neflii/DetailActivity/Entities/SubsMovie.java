package com.example.neflii.DetailActivity.Entities;

public class SubsMovie {
    private String title;
    private int id;
    private String poster_path;
    private String backdrop_path;

    public SubsMovie() {
    }

    public SubsMovie(String title, int id, String poster_path, String backdrop_path) {
        this.title = title;
        this.id = id;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
