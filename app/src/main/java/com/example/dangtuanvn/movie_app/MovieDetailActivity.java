package com.example.dangtuanvn.movie_app;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieTrailerFeedDataStore;
import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by sinhhx on 11/15/16.
 */
public class MovieDetailActivity extends AppCompatActivity{

    public static final String API ="AIzaSyBzKfqegqROEnxpAy5H9DzXXzWzWNIuqVU";
    public static final String ID="o-ACsZZncQtd14CERv798CahM03qbHLgqUVPi1EnjOBTu4";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
//        YouTubePlayerView videoplayer = (YouTubePlayerView) findViewById(R.id.videoView);
//        videoplayer.initialize(API,this);
        //Creating MediaController
        final MediaController videoMediaController = new MediaController(this,false);
        videoMediaController.setVisibility(View.INVISIBLE);
        String path1= "http://r7---sn-jhjup-nbol.googlevideo.com/videoplayback?mv=m&expire=1479300814&source=youtube&ratebypass=yes&signature=A192A06E416155B6F89AA62E875122B774EA2F57.11FB8C1FF0D5B2D8C2C9341AB147DD572851AD59&ms=au&mime=video/mp4&dur=143.777&pl=22&id=o-AEBeWvbkic0xY123UaApEeuRTAjQ7tnybWujVaReuMoQ&key=yt6&upn=GbZFZ0x1wSI&itag=22&sparams=dur,id,ip,ipbits,itag,lmt,mime,mm,mn,ms,mv,pl,ratebypass,source,upn,expire&mn=sn-jhjup-nbol&ipbits=0&mm=31&ip=120.138.70.156&mt=1479278971&lmt=1472394128541981&signature=22";
        Uri uri=Uri.parse(path1);

        final VideoView video=(VideoView)findViewById(R.id.videoView);
        FrameLayout videolayout =(FrameLayout) findViewById(R.id.videolayout);
        final Button playbtn = (Button) findViewById(R.id.play_button);
        playbtn.setBackgroundResource(R.drawable.bt_play);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.start();
                videoMediaController.setVisibility(View.VISIBLE);
                playbtn.setVisibility(View.GONE);
            }
        });
        videoMediaController.setAnchorView(videolayout);
        videoMediaController.setMediaPlayer(video);

        video.setMediaController(videoMediaController);
        video.setVideoURI(uri);
        final TextView movieTitle = (TextView)findViewById(R.id.movie_title);
        final TextView PG = (TextView)findViewById(R.id.PG);
        final TextView IMDB = (TextView)findViewById(R.id.IMDB);
        final TextView length = (TextView)findViewById(R.id.movie_duration);
        final TextView date = (TextView)findViewById(R.id.date);
        IMDB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_60, 0, 0, 0);
        length.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clock, 0, 0, 0);
        date.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calendar_white, 0, 0, 0);

        FeedDataStore movieDetailFDS = new MovieDetailFeedDataStore(this, 1165);
        movieDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
            List<MovieDetail> detailList = (List<MovieDetail>) list;
                movieTitle.setText(detailList.get(0).getFilmName());
                PG.setText(detailList.get(0).getPgRating());
                IMDB.setText(detailList.get(0).getImdbPoint()+" IMDB");
                length.setText(detailList.get(0).getDuration()+"");
                date.setText(detailList.get(0).getPublishDate());


            }
        });


    }
}

