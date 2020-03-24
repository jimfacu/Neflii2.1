package com.example.neflii.DetailActivity.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class SubsMovie implements Parcelable {
    private String title;
    private int id;
    private String poster_path;
    private String backdrop_path;
    private boolean suscripto;

    public SubsMovie() {
    }

    public SubsMovie(String title, int id, String poster_path, String backdrop_path) {
        this.title = title;
        this.id = id;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }


    protected SubsMovie(Parcel in) {
        title = in.readString();
        id = in.readInt();
        poster_path = in.readString();
        backdrop_path = in.readString();
        suscripto = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(id);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeByte((byte) (suscripto ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubsMovie> CREATOR = new Creator<SubsMovie>() {
        @Override
        public SubsMovie createFromParcel(Parcel in) {
            return new SubsMovie(in);
        }

        @Override
        public SubsMovie[] newArray(int size) {
            return new SubsMovie[size];
        }
    };

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

    public boolean isSuscripto() {
        return suscripto;
    }

    public void setSuscripto(boolean suscripto) {
        this.suscripto = suscripto;
    }
}
