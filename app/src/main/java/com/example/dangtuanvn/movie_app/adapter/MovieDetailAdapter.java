//package com.example.dangtuanvn.movie_app.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Handler;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.GridView;
//import android.widget.MediaController;
//import android.widget.TextView;
//import android.widget.VideoView;
//
//import com.example.dangtuanvn.movie_app.R;
//import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
//import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
//import com.example.dangtuanvn.movie_app.datastore.MovieTrailerFeedDataStore;
//import com.example.dangtuanvn.movie_app.datastore.ScheduleFeedDataStore;
//import com.example.dangtuanvn.movie_app.model.MovieDetail;
//import com.example.dangtuanvn.movie_app.model.MovieTrailer;
//import com.example.dangtuanvn.movie_app.model.Schedule;
//import com.example.dangtuanvn.movie_app.model.ScheduleCinemaGroupList;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by sinhhx on 11/18/16.
// */
//public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder> {
//// implements ScheduleExpandableAdapter.OnItemClick {
//
//    private Context context;
//    private int movieId;
//    private int duration;
//    private Calendar dateTime = Calendar.getInstance();
//    private String posterUrl;
//    private ArrayList<String> dateList = new ArrayList<>();
//    private ArrayList<String> displayDate = new ArrayList<>();
//    private ArrayList<String> timeList = new ArrayList<>();
//
//    public MovieDetailAdapter(Context context, int movieId, String posterUrl) {
//        this.posterUrl = posterUrl;
//        this.movieId = movieId;
//        this.context = context;
//    }
//
//
//
//    protected static class ViewHolder extends RecyclerView.ViewHolder {
//        private final VideoView video;
//        private FrameLayout videolayout;
//        private final Button playbtn;
//        private final TextView movieTitle;
//        private final TextView PG;
//        private final TextView IMDB;
//        private final TextView length;
//        private final TextView date;
//        private final TextView movieDescription;
//        private final TextView directorName;
//        private final TextView writerName;
//        private final TextView starName;
//        private final RecyclerView allSchedule;
//        private final Button more;
//        private final GridView movieSchedule;
//
//        private ViewHolder(View itemView) {
//            super(itemView);
//            video = (VideoView) itemView.findViewById(R.id.video_view);
//            videolayout = (FrameLayout) itemView.findViewById(R.id.video_layout);
//            playbtn = (Button) itemView.findViewById(R.id.play_button);
//            movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
//            PG = (TextView) itemView.findViewById(R.id.PG);
//            IMDB = (TextView) itemView.findViewById(R.id.IMDB);
//            length = (TextView) itemView.findViewById(R.id.movie_duration);
//            date = (TextView) itemView.findViewById(R.id.date);
//            allSchedule = (RecyclerView) itemView.findViewById(R.id.all_schedule_view);
//            movieDescription = (TextView) itemView.findViewById(R.id.movie_description);
//            directorName = (TextView) itemView.findViewById(R.id.director_name);
//            writerName = (TextView) itemView.findViewById(R.id.writer_name);
//            starName = (TextView) itemView.findViewById(R.id.star_name);
//            more = (Button) itemView.findViewById(R.id.more);
//            movieSchedule = (GridView) itemView.findViewById(R.id.movie_schedule);
//        }
//    }
//
//    public MovieDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = null;
//        if (viewType == 0) {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_player_layout, parent, false);
//        }
//        if (viewType == 1) {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_info, parent, false);
//        }
//        if (viewType == 2) {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_info, parent, false);
//        }
//        if (viewType == 3) {
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_layout, parent, false);
//        }
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        if (position == 0) {
//            final MediaController videoMediaController = new MediaController(context, false);
//            videoMediaController.setAnchorView(holder.videolayout);
//            videoMediaController.setMediaPlayer(holder.video);
//            holder.video.setMediaController(videoMediaController);
//
//            final FeedDataStore movieTrailerFDS = new MovieTrailerFeedDataStore(context, movieId);
//
//
//            final Target mTarget = new Target() {
//                @Override
//                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                    DisplayMetrics metrics = new DisplayMetrics();
//                    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                    wm.getDefaultDisplay().getMetrics(metrics);
//                    int targetWidth = metrics.widthPixels;
//                    double aspectRatio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
//                    int targetHeight = (int) (targetWidth * aspectRatio);
//                    ViewGroup.LayoutParams params = holder.videolayout.getLayoutParams();
//                    params.height = targetHeight;
//                    params.width = metrics.widthPixels;
//                    holder.videolayout.setLayoutParams(params);
//                    Bitmap result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
//                    BitmapDrawable ob = new BitmapDrawable(context.getResources(), result);
//                    holder.video.setBackground(ob);
//                }
//
//                @Override
//                public void onBitmapFailed(Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            };
//
//
//            // Create a handler with delay of 500 so these code will run after the image has loaded
//            Handler handlerVideo = new Handler();
//            handlerVideo.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Picasso.with(context)
//                            .load(posterUrl)
//                            .into(mTarget);
//
//                    movieTrailerFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//                        @Override
//                        public void onDataRetrievedListener(List<?> list, Exception ex) {
//                            List<MovieTrailer> movieTrailer = (List<MovieTrailer>) list;
//                            try {
//                                Uri uri = Uri.parse(movieTrailer.get(0).getV720p());
//                                if(duration!=0){
//                                    holder.video.seekTo(duration);
//                                }
//                                holder.video.setVideoURI(uri);
//                            } catch (NullPointerException e) {
//                                // TODO fix url null
//                            }
//                        }
//                    });
//
//                 holder.video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                     @Override
//                     public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                         mediaPlayer.isPlaying();
//                         return false;
//                     }
//                 });
//                    holder.playbtn.setBackgroundResource(R.drawable.bt_play3);
//                    holder.playbtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            holder.video.setBackgroundResource(0);
//                            holder.video.start();
//                            videoMediaController.setVisibility(View.VISIBLE);
//                            holder.playbtn.setVisibility(View.GONE);
//                        }
//                    });
//                    holder.video.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            if (holder.video.isPlaying() == false) {
//                                holder.video.setBackgroundResource(0);
//                                holder.video.start();
//                                videoMediaController.setVisibility(View.VISIBLE);
//                                holder.playbtn.setVisibility(View.GONE);
//                                return true;
//                            }
//                            return false;
//                        }
//
//                    });
//                }
//            }, 500);
//
//        }
//
//        if (position == 1) {
//            holder.IMDB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_60, 0, 0, 0);
//            holder.length.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
//            holder.date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_grey, 0, 0, 0);
//
//            FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(context, movieId);
//            movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//                @Override
//                public void onDataRetrievedListener(List<?> list, Exception ex) {
//                    List<MovieDetail> detailList = (List<MovieDetail>) list;
//                    holder.movieTitle.setText(detailList.get(0).getFilmName());
//                    holder.PG.setText(detailList.get(0).getPgRating());
//                    holder.IMDB.setText(detailList.get(0).getImdbPoint() + " IMDB");
//                    String duration = detailList.get(0).getDuration() / 60 + "h " + detailList.get(0).getDuration() % 60 + "min";
//                    holder.length.setText(duration);
//                    holder.date.setText(detailList.get(0).getPublishDate());
//                }
//            });
//        }
//
//        if (position == 2) {
//            FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(context, movieId);
//            movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//                @Override
//                public void onDataRetrievedListener(List<?> list, Exception ex) {
//                    List<MovieDetail> detailList = (List<MovieDetail>) list;
//                    holder.movieDescription.setText("" + detailList.get(0).getDescriptionMobile());
//                    if (detailList.get(0).getDirectorName() == null) {
//                        holder.directorName.setText("");
//                    } else {
//                        holder.directorName.setText(" " + detailList.get(0).getDirectorName());
//                    }
//                    String actor = "";
//                    for (int i = 0; i < detailList.get(0).getListActors().size(); i++) {
//                        actor = actor + detailList.get(0).getListActors().get(i);
//                        if (i != detailList.get(0).getListActors().size() - 1) {
//                            actor += ", ";
//                        }
//                    }
//                    holder.starName.setText(" " + actor);
//                }
//            });
//
//            holder.more.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (holder.movieDescription.getMaxLines() == 3) {
//                        holder.movieDescription.setMaxLines(Integer.MAX_VALUE);
//                        holder.more.setText("Less ");
//                    } else {
//                        holder.movieDescription.setMaxLines(3);
//                        holder.more.setText("More ");
//                    }
//                }
//            });
//        }
//        if (position == 3) {
//            final Calendar c = Calendar.getInstance();
//
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat sdf = new SimpleDateFormat("EE");
//            SimpleDateFormat f = new SimpleDateFormat("dd-MM");
//            if (dateList.size() < 7) {
//                for (int i = 0; i < 7; i++) {
//
//                    if (i > 0) {
//                        dateTime.add(dateTime.DATE, 1);
//                    }
//                    dateList.add(i, df.format(dateTime.getTime()));
//                    timeList.add(i, sdf.format(dateTime.getTime()));
//                    displayDate.add(i, f.format(dateTime.getTime()));
//                }
//            }
//            MovieScheduleAdapter movieScheduleAdapter = new MovieScheduleAdapter(context, displayDate, timeList);
//
//            holder.movieSchedule.setAdapter(movieScheduleAdapter);
//
//            holder.movieSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    view.setSelected(true);
//                    holder.movieSchedule.getPositionForView(view);
//                    FeedDataStore scheduleFDS = new ScheduleFeedDataStore(context, movieId, dateList.get(position));
//                    scheduleFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//                        @Override
//                        public void onDataRetrievedListener(List<?> list, Exception ex) {
//                            displayScheduleExpandableList((List<Schedule>) list, holder);
//                        }
//                    });
//                }
//
//
//            });
//
//                holder.movieSchedule.performItemClick(holder.movieSchedule.getAdapter().getView(0, null, holder.movieSchedule), 0, holder.movieSchedule.getItemIdAtPosition(0));
//
//
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 4;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//
//
//
//    private void displayScheduleExpandableList(final List<Schedule> scheduleList, ViewHolder holder) {
//        // TODO: Simplify this process
////        List<Integer> cinemaGroupListID = new ArrayList<>();
//        List<String> cinemaGroupListName = new ArrayList<>();
//        for (int i = 0; i < scheduleList.size(); i++) {
////            cinemaGroupListID.add(scheduleList.get(i).getpCinemaId());
//            cinemaGroupListName.add(scheduleList.get(i).getpCinemaName());
//        }
//
////        Set<Integer> filterSetId = new LinkedHashSet<>(cinemaGroupListID);
////        cinemaGroupListID = new ArrayList<>(filterSetId);
//
//        Set<String> filterSetName = new LinkedHashSet<>(cinemaGroupListName);
//        cinemaGroupListName = new ArrayList<>(filterSetName);
//
////        for (int i = 0; i < cinemaGroupList.size(); i++) {
////            Log.i("CINEMA GROUP NAME", "" + cinemaGroupList.get(i));
////        }
//
//        List<ScheduleCinemaGroupList> groupList = new ArrayList<>();
//        for (int i = 0; i < cinemaGroupListName.size(); i++) {
//            groupList.add(new ScheduleCinemaGroupList(cinemaGroupListName.get(i)));
//        }
//
//        for (Schedule schedule : scheduleList) {
//            for (int i = 0; i < groupList.size(); i++) {
////                if (schedule.getpCinemaId() == groupList.get(i).getCinemaId()) {
//                if (schedule.getpCinemaName().equals(groupList.get(i).getCinemaName())) {
//                    groupList.get(i).addChildObjectList(schedule);
//                    break;
//                }
//            }
//        }
//
//        ScheduleExpandableAdapter recyclerExpandableView = new ScheduleExpandableAdapter(context, groupList);
////        recyclerExpandableView.setOnItemClick(this);
//        holder.allSchedule.setAdapter(recyclerExpandableView);
//        holder.allSchedule.setLayoutManager(new LinearLayoutManager(context));
//    }
//
////    @Override
////    public void onItemClick(View view, Object data, int position) {
////        Log.i("ITEM CLICK", "" + position);
////    }
//}
//
//
//
//
//
//
//
//
//
//
//
