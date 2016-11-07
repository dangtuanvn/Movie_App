package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder>  {

        Context context;
public MovieDetailAdapter(Context context){

        this.context =context;

        }



public static class ViewHolder extends RecyclerView.ViewHolder{
    public ImageView moviePic;
    public TextView IMDB;


    public ViewHolder(View itemView) {
        super(itemView);
        moviePic =(ImageView) itemView.findViewById(R.id.moviepic);
        IMDB= (TextView) itemView.findViewById(R.id.IMDB);
    }
}
    public MovieDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showing_movie_detail, parent, false);
        v.setBackgroundColor(Color.WHITE);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(MovieDetailAdapter.ViewHolder holder, final int position) {



    }


    @Override
    public int getItemCount() {
        return 0;
    }


}
