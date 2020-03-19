package com.example.neflii.HomeActivity.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerFilms implements Parcelable {
    private int page;
    private List<Films> results;
    private int total_pages;
    private int total_results;

    public ContainerFilms(List<Films> results) {
        this.results = results;
    }

    protected ContainerFilms(Parcel in) {
        page = in.readInt();
        total_pages = in.readInt();
        total_results = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(total_pages);
        dest.writeInt(total_results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerFilms> CREATOR = new Creator<ContainerFilms>() {
        @Override
        public ContainerFilms createFromParcel(Parcel in) {
            return new ContainerFilms(in);
        }

        @Override
        public ContainerFilms[] newArray(int size) {
            return new ContainerFilms[size];
        }
    };

    public List<Films> getResults() {
        return results;
    }

    public void setResults(List<Films> results) {
        this.results = results;
    }
}
