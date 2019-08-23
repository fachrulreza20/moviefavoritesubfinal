package com.example.moviefavoritesubfinal.helper;

import android.database.Cursor;

import com.example.moviefavoritesubfinal.entity.TVShow;

import java.util.ArrayList;

import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.FavoritColumns.DESCRIPTION;
import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.FavoritColumns.IDFAV;
import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.FavoritColumns.PHOTO_PATH;
import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.FavoritColumns.RELEASE_DATE;
import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.FavoritColumns.TITLE;

public class MappingHelperTVS {

    public static ArrayList<TVShow> mapCursorToArrayListTVS(Cursor notesCursorTVS) {
        ArrayList<TVShow> notesListTVS = new ArrayList<>();

        if(notesCursorTVS != null){
            while (notesCursorTVS.moveToNext()) {
                int id = notesCursorTVS.getInt(notesCursorTVS.getColumnIndexOrThrow(IDFAV));
                String title = notesCursorTVS.getString(notesCursorTVS.getColumnIndexOrThrow(TITLE));
                String description = notesCursorTVS.getString(notesCursorTVS.getColumnIndexOrThrow(DESCRIPTION));
                String date = notesCursorTVS.getString(notesCursorTVS.getColumnIndexOrThrow(RELEASE_DATE));
                String photo = notesCursorTVS.getString(notesCursorTVS.getColumnIndexOrThrow(PHOTO_PATH));
                notesListTVS.add(new TVShow(id, title, description, date, photo));
            }

        }

        return notesListTVS;
    }
}