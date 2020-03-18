package com.example.neflii.DetailActivity.Entities;

public class Movie {

    private String backdrop_path;
    private int id;
    private String overview;
    private String poster_path;
    private String title;
    private String release_date;



    public Movie(String backdrop_path, int id, String overview, String poster_path, String title, String release_date) {
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.overview = overview;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
