package com.example.dangtuanvn.movie_app;

import android.os.Bundle;

import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    //int array contain icon for tabs
    private int[] imageNormalResId = { R.drawable.ic_showing_normal, R.drawable.ic_upcoming_normal,R.drawable.ic_around_normal,R.drawable.ic_news_normal };
    private int[] imageHighlightResId = { R.drawable.ic_showing_highlight, R.drawable.ic_upcoming_highlight,R.drawable.ic_around_highlight,R.drawable.ic_news_highlight };
    private MovieFeedDataStore feedDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager(),
               MainActivity.this));

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

       for(int i=0;i<4;i++){
           //Add customview to tablayout arcoding to tab position
        if(i==0){
            tabLayout.getTabAt(i).setIcon(imageHighlightResId[i]);}
        else{
        tabLayout.getTabAt(i).setIcon(imageNormalResId[i]);
    }}
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //on selected change normal tab icon to highlight icon. set old view to gone
              tab.setIcon(imageHighlightResId[tab.getPosition()]);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
              tab.setIcon(imageNormalResId[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    }

