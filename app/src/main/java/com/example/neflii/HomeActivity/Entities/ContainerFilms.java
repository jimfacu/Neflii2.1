package com.example.neflii.HomeActivity.Entities;

import java.util.List;

public class ContainerFilms {
    private int page;
    private List<Films> results;
    private int total_pages;
    private int total_results;

    public ContainerFilms(List<Films> results) {
        this.results = results;
    }

    public List<Films> getResults() {
        return results;
    }

    public void setResults(List<Films> results) {
        this.results = results;
    }
}
