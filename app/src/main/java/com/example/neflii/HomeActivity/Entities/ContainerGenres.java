package com.example.neflii.HomeActivity.Entities;

import java.util.List;

public class ContainerGenres {
    private List<Genres> genres;

    public ContainerGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
}
