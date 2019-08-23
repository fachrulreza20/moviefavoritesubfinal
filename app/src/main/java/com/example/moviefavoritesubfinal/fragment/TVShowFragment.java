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

import com.example.moviefavoritesubfinal.LoadNotesCallBackTVS;
import com.example.moviefavoritesubfinal.MainActivity;
import com.example.moviefavoritesubfinal.R;
import com.example.moviefavoritesubfinal.adapter.ConsumerAdapterTVS;
import com.example.moviefavoritesubfinal.entity.TVShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.moviefavoritesubfinal.database.DatabaseContractTVS.FavoritColumns.CONTENT_URI;
import static com.example.moviefavoritesubfinal.helper.MappingHelperTVS.mapCursorToArrayListTVS;

public class TVShowFragment extends Fragment implements LoadNotesCallBackTVS {

    private ConsumerAdapterTVS consumerAdapter;
    private DataObserver myObserver;



    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_tvshow, container, false);

        if(((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("TV Show Favorite");
        }

        return rootview;
    }










    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView rvTVshow = getActivity().findViewById(R.id.rv_category_tvshow);
        consumerAdapter = new ConsumerAdapterTVS(getActivity());
        rvTVshow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTVshow.setHasFixedSize(true);
        rvTVshow.setAdapter(consumerAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(CONTENT_URI,true,myObserver);
        new getData(getContext(), (LoadNotesCallBackTVS) this).execute();



    }



















    public void postExecute(Cursor notes) {

        ArrayList<TVShow> listNotes = mapCursorToArrayListTVS(notes);
        if (listNotes.size() > 0) {
            consumerAdapter.setListNotes(listNotes);
        } else {
            Toast.makeText(getContext(), "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            consumerAdapter.setListNotes(new ArrayList<TVShow>());
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallBackTVS> weakCallback;


        private getData(Context context, LoadNotesCallBackTVS callback) {
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
            new getData(context, (LoadNotesCallBackTVS) this).execute();
        }
    }


}
