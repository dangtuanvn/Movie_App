package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dangtuanvn.movie_app.MainActivity;
import com.example.dangtuanvn.movie_app.NoInternetActivity;
//import com.example.dangtuanvn.movie_app.fragment.MovieTabFragment;
import com.example.dangtuanvn.movie_app.fragment.MovieTabFragment;
import com.example.dangtuanvn.movie_app.fragment.NoInternetFragment;
//import com.example.dangtuanvn.movie_app.fragment.CinemaTabFragment;
//import com.example.dangtuanvn.movie_app.fragment.MovieTabFragment;
import com.example.dangtuanvn.movie_app.fragment.NewsTabFragment;

/**
 * Created by sinhhx on 11/7/16.
 */
public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    private final static int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"Showing", "Upcoming", "Cinema around", "News"};
    private Context context;

    public TabViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        // Check for network connection
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
            switch(position) {
                case 0:
                    return MovieTabFragment.newInstance(MovieTabFragment.CinemaTab.Showing);
                case 1:
                    return MovieTabFragment.newInstance(MovieTabFragment.CinemaTab.Upcoming);
                case 2:
                case 3:
                    return NewsTabFragment.newInstance();
            }
//        } else {
//            Intent intent = new Intent(context, NoInternetActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(intent);
//            return NoInternetFragment.newInstance();
//        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}