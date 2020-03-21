package com.example.neflii.HomeActivity.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerGenres implements Parcelable {
    private List<Genres> genres;

    public ContainerGenres(List<Genres> genres) {
        this.genres = genres;
    }

    protected ContainerGenres(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerGenres> CREATOR = new Creator<ContainerGenres>() {
        @Override
        public ContainerGenres createFromParcel(Parcel in) {
            return new ContainerGenres(in);
        }

        @Override
        public ContainerGenres[] newArray(int size) {
            return new ContainerGenres[size];
        }
    };

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }
}
