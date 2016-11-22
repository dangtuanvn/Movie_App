package com.example.dangtuanvn.movie_app.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dangtuanvn.movie_app.AllTabFragment;
import com.example.dangtuanvn.movie_app.fragment.CinemaTabFragment;
import com.example.dangtuanvn.movie_app.fragment.MovieTabFragment;
import com.example.dangtuanvn.movie_app.fragment.NewsTabFragment;

/**
 * Created by sinhhx on 11/7/16.
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    public final static int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Showing", "Upcoming", "Cinema around", "News" };
    private FragmentManager fm;

    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0 || position == 1){
            return MovieTabFragment.newInstance(MovieTabFragment.CinemaTab.values()[position]);
        }
        else if(position == 2){
            return CinemaTabFragment.newInstance();
        }
        else if(position == 3){
            return NewsTabFragment.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}