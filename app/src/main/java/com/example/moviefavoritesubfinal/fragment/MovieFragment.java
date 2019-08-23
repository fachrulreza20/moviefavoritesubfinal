package com.example.moviefavoritesubfinal.fragment;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moviefavoritesubfinal.LoadNotesCallBack;
import com.example.moviefavoritesubfinal.MainActivity;
import com.example.moviefavoritesubfinal.R;
import com.example.moviefavoritesubfinal.adapter.ConsumerAdapter;
import com.example.moviefavoritesubfinal.entity.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.moviefavoritesubfinal.database.DatabaseContract.FavoritColumns.CONTENT_URI;
import static com.example.moviefavoritesubfinal.helper.MappingHelper.mapCursorToArrayList;

public class MovieFragment extends Fragment implements LoadNotesCallBack {


    private ConsumerAdapter consumerAdapter;
    private DataObserver myObserver;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_movie, container, false);

        if(((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Movie Favorite");
        }


        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView rvMovie = getActivity().findViewById(R.id.rv_category_movie);
        consumerAdapter = new ConsumerAdapter(getActivity());
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(consumerAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(CONTENT_URI,true,myObserver);
        new getData(getContext(), (LoadNotesCallBack) this).execute();



    }



















    public void postExecute(Cursor notes) {

        ArrayList<Movie> listNotes = mapCursorToArrayList(notes);
        if (listNotes.size() > 0) {
            consumerAdapter.setListNotes(listNotes);
        } else {
            Toast.makeText(getContext(), "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            consumerAdapter.setListNotes(new ArrayList<Movie>());
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallBack> weakCallback;


        private getData(Context context, LoadNotesCallBack callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }

    }


    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (LoadNotesCallBack) this).execute();
        }
    }

}
