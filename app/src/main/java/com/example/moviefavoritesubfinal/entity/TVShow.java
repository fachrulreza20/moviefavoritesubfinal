package com.example.moviefavoritesubfinal.entity;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.moviefavoritesubfinal.database.DatabaseContractTVS;

import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.getColumnInt;
import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.getColumnString;

public class TVShow implements Parcelable  {

    private int id;
    private String title, description, date;
    private String photo;
    final String urlPhoto = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.photo);
        dest.writeString(this.date);
    }





    public TVShow() {
    }

    public TVShow(int id, String title, String description, String date, String photo) {
        this.id = id;
        this.photo = photo;
        this.title = title;
        this.description = description;
        this.date = date;
    }


    public TVShow(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContractTVS.FavoritColumns.IDFAV);
        this.title = getColumnString(cursor, DatabaseContractTVS.FavoritColumns.TITLE);
        this.description = getColumnString(cursor, DatabaseContractTVS.FavoritColumns.DESCRIPTION);
        this.date = getColumnString(cursor, DatabaseContractTVS.FavoritColumns.RELEASE_DATE);
        this.photo= getColumnString(cursor, DatabaseContractTVS.FavoritColumns.PHOTO_PATH);
    }

    private TVShow(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.photo = in.readString();
        this.description = in.readString();
        this.date = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }


        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
