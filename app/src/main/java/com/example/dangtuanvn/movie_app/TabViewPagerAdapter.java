package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

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
        return MovieTabFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}