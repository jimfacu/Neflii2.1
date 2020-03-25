package com.example.neflii.DetailActivity.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

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

    protected Movie(Parcel in) {
        backdrop_path = in.readString();
        id = in.readInt();
        overview = in.readString();
        poster_path = in.readString();
        title = in.readString();
        release_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdrop_path);
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(title);
        dest.writeString(release_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getBackdrop_path() {
        return backdrop_path;
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


    public String getPoster_path() {
        return poster_path;
    }


    public String getTitle() {
        return title;
    }


    public String getRelease_date() {
        return release_date;
    }
}
