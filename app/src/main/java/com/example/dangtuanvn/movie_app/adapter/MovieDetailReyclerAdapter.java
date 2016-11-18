package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieTrailerFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.ScheduleFeedDataStore;
import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.MovieTrailer;
import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.ScheduleCinemaGroupList;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sinhhx on 11/18/16.
 */
public class MovieDetailReyclerAdapter extends RecyclerView.Adapter<MovieDetailReyclerAdapter.ViewHolder> implements ScheduleExpandableAdapter.OnItemClick{

    Context context;
    int movieId;
    public MovieDetailReyclerAdapter(Context context, int movieId){
        this.movieId = movieId;
        this.context =context;

    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        final VideoView video;
        FrameLayout videolayout;
        final Button playbtn ;
        final TextView movieTitle;
        final TextView PG ;
        final TextView IMDB ;
        final TextView length ;
        final TextView date ;
        final TextView movieDescription ;
        final TextView directorName ;
        final TextView writerName;
        final TextView starName ;
        final RecyclerView allSchedule;
        final Button more;
        final GridView movieSchedule;
        public ViewHolder(View itemView) {
            super(itemView);
            video=(VideoView) itemView.findViewById(R.id.video_view);
            videolayout =(FrameLayout) itemView.findViewById(R.id.video_layout);
            playbtn = (Button) itemView.findViewById(R.id.play_button);
            movieTitle = (TextView)itemView.findViewById(R.id.movie_title);
            PG = (TextView)itemView.findViewById(R.id.PG);
            IMDB = (TextView)itemView.findViewById(R.id.IMDB);
            length = (TextView)itemView.findViewById(R.id.movie_duration);
            date = (TextView)itemView.findViewById(R.id.date);
            allSchedule=(RecyclerView) itemView.findViewById(R.id.all_schedule_view);
            movieDescription = (TextView)itemView.findViewById(R.id.movie_description);
            directorName = (TextView)itemView.findViewById(R.id.director_name);
            writerName = (TextView)itemView.findViewById(R.id.writer_name);
            starName = (TextView)itemView.findViewById(R.id.star_name);
            more = (Button) itemView.findViewById(R.id.more);
            movieSchedule = (GridView) itemView.findViewById(R.id.movie_schedule);
        }
    }
    public MovieDetailReyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(viewType ==0){
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_player_layout, parent, false);}
        if(viewType ==1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_info, parent, false);}
        if(viewType ==2){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_info, parent, false);}
        if(viewType==3){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_layout, parent, false);
        }

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            final MediaController videoMediaController = new MediaController(context, false);
            videoMediaController.setAnchorView(holder.videolayout);
            videoMediaController.setMediaPlayer(holder.video);
            holder.video.setMediaController(videoMediaController);
            final FeedDataStore movieTrailerFDS = new MovieTrailerFeedDataStore(context, movieId);
            movieTrailerFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    List<MovieTrailer> movieTrailer = (List<MovieTrailer>) list;
                    if(movieTrailer.get(0).getV720p()!=null) {
                        Uri uri = Uri.parse(movieTrailer.get(0).getV720p());
                        holder.video.setVideoURI(uri);
                    }
                }
            });
            holder.playbtn.setBackgroundResource(R.drawable.bt_play);
            holder.playbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.video.start();
                    videoMediaController.setVisibility(View.VISIBLE);
                    holder.playbtn.setVisibility(View.GONE);
                }
            });

        }


        if (position == 1) {
            holder.IMDB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_60, 0, 0, 0);
            holder.length.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
            holder.date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_white, 0, 0, 0);

            FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(context, movieId);
            movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    List<MovieDetail> detailList = (List<MovieDetail>) list;
                    holder.movieTitle.setText(detailList.get(0).getFilmName());
                    holder.PG.setText(detailList.get(0).getPgRating());
                    holder.IMDB.setText(detailList.get(0).getImdbPoint() + " IMDB");
                    holder.length.setText(detailList.get(0).getDuration() + "");
                    holder.date.setText(detailList.get(0).getPublishDate());
                }
            });
        }

        if (position == 2) {
            FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(context, movieId);
            movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    List<MovieDetail> detailList = (List<MovieDetail>) list;
                    holder.movieDescription.setText(" " + detailList.get(0).getDescriptionMobile());
                    holder.directorName.setText(" " + detailList.get(0).getDirectorName());
                    String actor = "";
                    for (int i = 0; i < (detailList.get(0).getListActors().size()); i++) {
                        actor = actor + detailList.get(0).getListActors().get(i) + " ";
                    }
                    holder.starName.setText(" " + actor);
                }
            });

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.movieDescription.getMaxLines() == 3) {
                        holder.movieDescription.setMaxLines(Integer.MAX_VALUE);
                        holder.more.setText("Less");
                    } else {
                        holder.movieDescription.setMaxLines(3);
                        holder.more.setText("More");
                    }
                }
            });
        }
        if (position == 3) {
            final Calendar c = Calendar.getInstance();
            final Calendar dateTime =Calendar.getInstance();
            MovieScheduleAdapter movieScheduleAdapter = new MovieScheduleAdapter(context, c);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final ArrayList<String> dateList = new ArrayList<String>();
           for(int i=0;i<7;i++){

               if(i>0) {
                   dateTime.add(dateTime.DATE, 1);
               }
               dateList.add(i,df.format(dateTime.getTime()));
           }


            holder.movieSchedule.setAdapter(movieScheduleAdapter);
            holder.movieSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                    FeedDataStore scheduleFDS = new ScheduleFeedDataStore(context, movieId, dateList.get(position));
                    scheduleFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                        @Override
                        public void onDataRetrievedListener(List<?> list, Exception ex) {
                            displayRecyclerExpandableList((List<Schedule>) list,holder);
                        }
                    });
                }


            });

        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }


    protected void displayRecyclerExpandableList(final List<Schedule> scheduleList, ViewHolder holder) {

        List<Integer> cinemaGroupList = new ArrayList<>();
        for (int i = 0; i < scheduleList.size(); i++) {
            cinemaGroupList.add(scheduleList.get(i).getpCinemaId());
        }

        Set<Integer> filterSet = new LinkedHashSet<>(cinemaGroupList);
        cinemaGroupList = new ArrayList<>(filterSet);

        for (int i = 0; i < cinemaGroupList.size(); i++) {
            Log.i("CINEMA GROUP NAME", "" + cinemaGroupList.get(i));
        }

        List<ScheduleCinemaGroupList> groupList = new ArrayList<>();
        for (int i = 0; i < cinemaGroupList.size(); i++) {
            groupList.add(new ScheduleCinemaGroupList(cinemaGroupList.get(i)));
        }


        for (Schedule schedule : scheduleList) {
            for (int i = 0; i < groupList.size(); i++) {
                if (schedule.getpCinemaId() == groupList.get(i).getId()) {
                    groupList.get(i).addChildObjectList(schedule);
                    break;
                }
            }
        }

        ScheduleExpandableAdapter recyclerExpandableView = new ScheduleExpandableAdapter(context, groupList);
        recyclerExpandableView.setOnItemClick(this);
       holder.allSchedule.setAdapter(recyclerExpandableView);
        holder.allSchedule.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        Log.i("ITEM CLICK", "" + position);
    }
}



//    protected void displayImagePicasso(ImageView imageView, String url) {
//        Picasso.with(context)
//                .load(url)
//                .placeholder(R.drawable.white_placeholder)
//                .transform(getCropPosterTransformation())
//                .into(imageView);
//    }
//
//    protected Transformation getCropPosterTransformation(){
//        Transformation cropPosterTransformation = new Transformation() {
//            @Override
//            public Bitmap transform(Bitmap source) {
//                DisplayMetrics metrics = new DisplayMetrics();
//                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                wm.getDefaultDisplay().getMetrics(metrics);
//                int targetWidth = metrics.widthPixels - (metrics.widthPixels / 20);
//                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
//                int targetHeight = (int) (targetWidth * aspectRatio);
//                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
//                if (result != source) {
//                    // Same bitmap is returned if sizes are the same
//                    source.recycle();
//                }
//                return result;
//            }
//
//            @Override
//            public String key() {
//                return "cropPosterTransformation";
//            }
//        };
//        return cropPosterTransformation;
//    }




