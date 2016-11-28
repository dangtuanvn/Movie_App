package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.dangtuanvn.movie_app.adapter.MovieDetailAdapter;
import com.example.dangtuanvn.movie_app.adapter.MovieScheduleAdapter;
import com.example.dangtuanvn.movie_app.adapter.ScheduleExpandableAdapter;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieTrailerFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.ScheduleFeedDataStore;
import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.MovieTrailer;
import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.ScheduleCinemaGroupList;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sinhhx on 11/15/16.
 */
public class MovieDetailActivity extends AppCompatActivity {
    private Toolbar mytoolbar;
    private  VideoView video;
    private FrameLayout videolayout;
    private  Button playbtn;
    private  TextView movieTitle;
    private  TextView PG;
    private  TextView IMDB;
    private  TextView length;
    private  TextView date;
    private  TextView movieDescription;
    private  TextView directorName;
    private  TextView writerName;
    private  TextView starName;
    private  RecyclerView allSchedule;
    private  Button more;
    private  GridView movieSchedule;
    private Calendar dateTime = Calendar.getInstance();
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> displayDate = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        video = (VideoView) findViewById(R.id.video_view);
        videolayout = (FrameLayout) findViewById(R.id.video_layout);
        playbtn = (Button) findViewById(R.id.play_button);
        movieTitle = (TextView) findViewById(R.id.movie_title);
        PG = (TextView) findViewById(R.id.PG);
        IMDB = (TextView) findViewById(R.id.IMDB);
        length = (TextView) findViewById(R.id.movie_duration);
        date = (TextView) findViewById(R.id.date);
        allSchedule = (RecyclerView) findViewById(R.id.all_schedule_view);
        movieDescription = (TextView) findViewById(R.id.movie_description);
        directorName = (TextView) findViewById(R.id.director_name);
        writerName = (TextView) findViewById(R.id.writer_name);
        starName = (TextView) findViewById(R.id.star_name);
        more = (Button) findViewById(R.id.more);
        movieSchedule = (GridView) findViewById(R.id.movie_schedule);
//        mytoolbar = (Toolbar)findViewById(R.id.my_toolbar);
//        mytoolbar.setNavigationIcon(R.drawable.back_btn);
//        mytoolbar.setTitle("");
//        setSupportActionBar(mytoolbar);
//        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        final int movieId = getIntent().getIntExtra("movieId", 0);
        final String posterUrl = getIntent().getStringExtra("posterUrl");
//
//        final RecyclerView movieDetail = (RecyclerView) findViewById(R.id.movie_detail_recycler);
//        movieDetail.getRecycledViewPool().setMaxRecycledViews(0,0);
//        movieDetail.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
//            @Override
//            public void onChildViewAttachedToWindow(View view) {
//            }
//
//            @Override
//            public void onChildViewDetachedFromWindow(View view) {
//             movieDetail.getChildLayoutPosition(view);
//            }
//        });
//        MovieDetailAdapter adapter = new MovieDetailAdapter(this, movieId, posterUrl);
//        movieDetail.setAdapter(adapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        movieDetail.setLayoutManager(layoutManager);

        final MediaController videoMediaController = new MediaController(this, false);
        videoMediaController.setAnchorView(videolayout);
        videoMediaController.setMediaPlayer(video);
        video.setMediaController(videoMediaController);

        final FeedDataStore movieTrailerFDS = new MovieTrailerFeedDataStore(this, movieId);
        playbtn.setBackgroundResource(R.drawable.bt_play3);

        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                DisplayMetrics metrics = new DisplayMetrics();
                WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                wm.getDefaultDisplay().getMetrics(metrics);
                int targetWidth = metrics.widthPixels;
                double aspectRatio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                ViewGroup.LayoutParams params = videolayout.getLayoutParams();
                params.height = targetHeight;
                params.width = metrics.widthPixels;
                videolayout.setLayoutParams(params);
                Bitmap result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
                BitmapDrawable ob = new BitmapDrawable(getResources(), result);
                video.setBackground(ob);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };


        // Create a handler with delay of 500 so these code will run after the image has loaded
        Handler handlerVideo = new Handler();
        handlerVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getApplication())
                        .load(posterUrl)
                        .into(mTarget);

                movieTrailerFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                    @Override
                    public void onDataRetrievedListener(List<?> list, Exception ex) {
                        List<MovieTrailer> movieTrailer = (List<MovieTrailer>) list;
                        try {
                            Uri uri = Uri.parse(movieTrailer.get(0).getV720p());

                            video.setVideoURI(uri);
                        } catch (NullPointerException e) {
                            // TODO fix url null
                        }
                    }
                });

                playbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        video.setBackgroundResource(0);
                        video.start();
                        videoMediaController.setVisibility(View.VISIBLE);
                        playbtn.setVisibility(View.GONE);
                    }
                });
                video.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (video.isPlaying() == false) {
                            video.setBackgroundResource(0);
                            video.start();
                            videoMediaController.setVisibility(View.VISIBLE);
                            playbtn.setVisibility(View.GONE);
                            return true;
                        }
                        return false;
                    }

                });
            }
        }, 500);


        IMDB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_60, 0, 0, 0);
        length.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
        date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_grey, 0, 0, 0);

        FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(this, movieId);
        movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                List<MovieDetail> detailList = (List<MovieDetail>) list;
                movieTitle.setText(detailList.get(0).getFilmName());
                PG.setText(detailList.get(0).getPgRating());
                IMDB.setText(detailList.get(0).getImdbPoint() + " IMDB");
                String duration = detailList.get(0).getDuration() / 60 + "h " + detailList.get(0).getDuration() % 60 + "min";
                length.setText(duration);
                date.setText(detailList.get(0).getPublishDate());
                movieDescription.setText("" + detailList.get(0).getDescriptionMobile());
                if (detailList.get(0).getDirectorName() == null) {
                    directorName.setText("");
                } else {
                    directorName.setText(" " + detailList.get(0).getDirectorName());
                }
                String actor = "";
                for (int i = 0; i < detailList.get(0).getListActors().size(); i++) {
                    actor = actor + detailList.get(0).getListActors().get(i);
                    if (i != detailList.get(0).getListActors().size() - 1) {
                        actor += ", ";
                    }
                }
                starName.setText(" " + actor);
            }
        });




        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieDescription.getMaxLines() == 3) {
                    movieDescription.setMaxLines(Integer.MAX_VALUE);
                    more.setText("Less ");
                } else {
                    movieDescription.setMaxLines(3);
                    more.setText("More ");
                }
            }
        });


        final Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("EE");
        SimpleDateFormat f = new SimpleDateFormat("dd-MM");
        if (dateList.size() < 7) {
            for (int i = 0; i < 7; i++) {

                if (i > 0) {
                    dateTime.add(dateTime.DATE, 1);
                }
                dateList.add(i, df.format(dateTime.getTime()));
                timeList.add(i, sdf.format(dateTime.getTime()));
                displayDate.add(i, f.format(dateTime.getTime()));
            }
        }
        MovieScheduleAdapter movieScheduleAdapter = new MovieScheduleAdapter(this, displayDate, timeList);

        movieSchedule.setAdapter(movieScheduleAdapter);

        movieSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view.setSelected(true);
                movieSchedule.getPositionForView(view);
                FeedDataStore scheduleFDS = new ScheduleFeedDataStore(getApplicationContext(), movieId, dateList.get(position));
                scheduleFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                    @Override
                    public void onDataRetrievedListener(List<?> list, Exception ex) {
                        displayScheduleExpandableList((List<Schedule>) list);
                    }
                });
            }


        });

        movieSchedule.performItemClick(movieSchedule.getAdapter().getView(0, null, movieSchedule), 0, movieSchedule.getItemIdAtPosition(0));


    }
    private void displayScheduleExpandableList(final List<Schedule> scheduleList) {
        // TODO: Simplify this process
//        List<Integer> cinemaGroupListID = new ArrayList<>();
        List<String> cinemaGroupListName = new ArrayList<>();
        for (int i = 0; i < scheduleList.size(); i++) {
//            cinemaGroupListID.add(scheduleList.get(i).getpCinemaId());
            cinemaGroupListName.add(scheduleList.get(i).getpCinemaName());
        }

//        Set<Integer> filterSetId = new LinkedHashSet<>(cinemaGroupListID);
//        cinemaGroupListID = new ArrayList<>(filterSetId);

        Set<String> filterSetName = new LinkedHashSet<>(cinemaGroupListName);
        cinemaGroupListName = new ArrayList<>(filterSetName);

//        for (int i = 0; i < cinemaGroupList.size(); i++) {
//            Log.i("CINEMA GROUP NAME", "" + cinemaGroupList.get(i));
//        }

        List<ScheduleCinemaGroupList> groupList = new ArrayList<>();
        for (int i = 0; i < cinemaGroupListName.size(); i++) {
            groupList.add(new ScheduleCinemaGroupList(cinemaGroupListName.get(i)));
        }

        for (Schedule schedule : scheduleList) {
            for (int i = 0; i < groupList.size(); i++) {
//                if (schedule.getpCinemaId() == groupList.get(i).getCinemaId()) {
                if (schedule.getpCinemaName().equals(groupList.get(i).getCinemaName())) {
                    groupList.get(i).addChildObjectList(schedule);
                    break;
                }
            }
        }

        ScheduleExpandableAdapter recyclerExpandableView = new ScheduleExpandableAdapter(this, groupList);
//        recyclerExpandableView.setOnItemClick(this);
        allSchedule.setAdapter(recyclerExpandableView);
        allSchedule.setLayoutManager(new LinearLayoutManager(this));
    }

//    @Override
//    public void onItemClick(View view, Object data, int position) {
//        Log.i("ITEM CLICK", "" + position);
//    }
}




