package com.example.jkakeno.movienight;

import android.os.Parcel;
import android.os.Parcelable;

public class TVShow implements Parcelable {
    private String mName;
    private String mFirstReleaseDate;
    private String mOverView;

    public TVShow(){}

    public TVShow(Parcel in) {
        mName = in.readString();
        mFirstReleaseDate = in.readString();
        mOverView = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel parcel) {
            return new TVShow(parcel);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mFirstReleaseDate);
        dest.writeString(mOverView);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return mName;
    }

    public void setName(String title) {
        mName = title;
    }

    public String getFirstReleaseDate() {
        return mFirstReleaseDate;
    }

    public void setFirstReleaseDate(String releaseDate) {
        mFirstReleaseDate = releaseDate;
    }

    public String getOverView() {
        return mOverView;
    }

    public void setOverView(String overView) {
        mOverView = overView;
    }
}
