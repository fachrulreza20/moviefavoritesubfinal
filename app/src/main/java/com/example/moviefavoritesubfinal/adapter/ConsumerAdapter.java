package com.example.moviefavoritesubfinal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviefavoritesubfinal.R;
import com.example.moviefavoritesubfinal.entity.Movie;

import java.util.ArrayList;

import static com.example.moviefavoritesubfinal.database.DatabaseContract.FavoritColumns.CONTENT_URI;

public class ConsumerAdapter extends RecyclerView.Adapter<ConsumerAdapter.NoteViewHolder> {

    private final ArrayList<Movie> listMovies = new ArrayList<>();
    private final Activity activity;

    private Context context;
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public ConsumerAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Movie> getListNotes() {
        return listMovies;
    }

    public void setListNotes(ArrayList<Movie> listNotes) {
        this.listMovies.clear();
        this.listMovies.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int i) {
        holder.tvTitle.setText(getListNotes().get(i).getTitle());
        holder.tvDate.setText(getListNotes().get(i).getDate());
        holder.tvDescription.setText(getListNotes().get(i).getDescription());

        Glide.with(holder.itemView.getContext())
                .load(getListNotes().get(i).getPhoto())
                .into(holder.imgview);

        holder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
//                Intent intent = new Intent(activity, FormActivity.class);
//
//                Uri uri = Uri.parse(CONTENT_URI + "/" + getListNotes().get(position).getId());
//                intent.setData(uri);
//                activity.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription, tvDate;
        final ImageView imgview;
        View rootView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            imgview = itemView.findViewById(R.id.img_photo);
        }
    }
}