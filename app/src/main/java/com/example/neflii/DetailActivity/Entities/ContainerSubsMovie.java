package com.example.neflii.DetailActivity.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContainerSubsMovie implements Parcelable {
    private List<SubsMovie> subsMovieList;

    public ContainerSubsMovie(List<SubsMovie> subsMovieList) {
        this.subsMovieList = subsMovieList;
    }

    protected ContainerSubsMovie(Parcel in) {
        subsMovieList = in.createTypedArrayList(SubsMovie.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(subsMovieList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContainerSubsMovie> CREATOR = new Creator<ContainerSubsMovie>() {
        @Override
        public ContainerSubsMovie createFromParcel(Parcel in) {
            return new ContainerSubsMovie(in);
        }

        @Override
        public ContainerSubsMovie[] newArray(int size) {
            return new ContainerSubsMovie[size];
        }
    };

    public List<SubsMovie> getSubsMovieList() {
        return subsMovieList;
    }

    public void setSubsMovieList(List<SubsMovie> subsMovieList) {
        this.subsMovieList = subsMovieList;
    }
}
