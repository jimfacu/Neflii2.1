package com.example.neflii.HomeActivity.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Films implements Parcelable {
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

    protected Films(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Films> CREATOR = new Creator<Films>() {
        @Override
        public Films createFromParcel(Parcel in) {
            return new Films(in);
        }

        @Override
        public Films[] newArray(int size) {
            return new Films[size];
        }
    };

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
