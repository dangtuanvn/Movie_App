package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.multidex.MultiDex;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.dangtuanvn.movie_app.adapter.TabViewPagerAdapter;


public class MainActivity extends AppCompatActivity {
    // int array contain icon for tabs
    private int[] imageResId = {R.drawable.tabshowing, R.drawable.tabupcomingicon, R.drawable.tabaroundicon, R.drawable.tabnewsicon};

    // http://stackoverflow.com/questions/36694726/noclassdeffounderror-com-squareup-picasso-picasso-with-phone
    // need this for picasso and databinding library to work on Android 4.4 (api 19)
    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//         Check for network connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            setContentView(R.layout.activity_main);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager(), this));
            viewPager.setOffscreenPageLimit(4);

            // Give the TabLayout the ViewPager
            TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
            for (int i = 0; i < 4; i++) {
                tabLayout.getTabAt(i).setIcon(imageResId[i]);
            }
        } else {
            Intent intent = new Intent(this, NoInternetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        }
    }
}