package com.example.dangtuanvn.movie_app;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sinhhx on 11/7/16.
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    public final static int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Showing", "Upcoming", "Cinema around","News"};

    public TabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return MovieTabFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}