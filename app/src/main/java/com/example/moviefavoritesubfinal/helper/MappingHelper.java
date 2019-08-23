package com.example.moviefavoritesubfinal.helper;
import android.database.Cursor;

import com.example.moviefavoritesubfinal.entity.Movie;

import java.util.ArrayList;

import static android.provider.Telephony.ThreadsColumns.DATE;
import static com.example.moviefavoritesubfinal.database.DatabaseContract.FavoritColumns.DESCRIPTION;
import static com.example.moviefavoritesubfinal.database.DatabaseContract.FavoritColumns.IDFAV;
import static com.example.moviefavoritesubfinal.database.DatabaseContract.FavoritColumns.PHOTO_PATH;
import static com.example.moviefavoritesubfinal.database.DatabaseContract.FavoritColumns.RELEASE_DATE;
import static com.example.moviefavoritesubfinal.database.DatabaseContract.FavoritColumns.TITLE;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();

        if(notesCursor != null) {
            while (notesCursor.moveToNext()) {
                int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(IDFAV));
                String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
                String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DESCRIPTION));
                String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_DATE));
                String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(PHOTO_PATH));
                notesList.add(new Movie(id, title, description, date, photo));
            }
        }

        return notesList;
    }
}