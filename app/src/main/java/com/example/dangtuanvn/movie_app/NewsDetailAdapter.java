package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sinhhx on 11/9/16.
 */
public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.ViewHolder>  {
    List<News> newsList;
    Context context;
    int mPage;

    public NewsDetailAdapter(Context context, List<News> newsList, int mPage){
        this.newsList = newsList;
        this.mPage =mPage;
        this.context =context;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView newsPic;
        public TextView title;
        public TextView time;


        public ViewHolder(View itemView) {
            super(itemView);
            newsPic =(ImageView) itemView.findViewById(R.id.newspic);
            title= (TextView) itemView.findViewById(R.id.txtTitle);
            time=(TextView) itemView.findViewById(R.id.txtTime
            );

        }
    }
    public NewsDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_detail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final NewsDetailAdapter.ViewHolder holder, final int position) {
        holder.time.setText(newsList.get(position).getTimeDifference());
        holder.title.setText(newsList.get(position).getNewsTitle());
        displayCarList_Picasso(holder.newsPic,newsList.get(position).getImageFull());
        holder.newsPic.setScaleType(ImageView.ScaleType.FIT_XY);


    }
    private Transformation cropPosterTransformation = new Transformation() {

        @Override public Bitmap transform(Bitmap source) {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            wm.getDefaultDisplay().getMetrics(metrics);
            int targetWidth = metrics.widthPixels-(metrics.widthPixels/20);
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override public String key() {
            return "cropPosterTransformation";
        }
    };


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void displayCarList_Picasso(ImageView imageView, String url){

        Picasso.with(context)
                .load(url)
                .transform(cropPosterTransformation)
                .into(imageView);
    }




}
