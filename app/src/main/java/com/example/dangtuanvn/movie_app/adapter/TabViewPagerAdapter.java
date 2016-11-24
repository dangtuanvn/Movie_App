package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dangtuanvn.movie_app.fragment.NoInternetFragment;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.fragment.CinemaTabFragment;
import com.example.dangtuanvn.movie_app.fragment.MovieTabFragment;
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
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (position == 0) {
                return MovieTabFragment.newInstance(MovieTabFragment.CinemaTab.values()[position]);
            } else if (position == 1) {
                return MovieTabFragment.newInstance(MovieTabFragment.CinemaTab.values()[position]);
            } else if (position == 2) {
                return CinemaTabFragment.newInstance();
            } else if (position == 3) {
                return NewsTabFragment.newInstance();
            }
        } else {
            return NoInternetFragment.newInstance();
        }
        return null;
    }

    Drawable myDrawable;
    String title;
    private int[] imageResId = {R.drawable.tabshowing, R.drawable.tabupcomingicon, R.drawable.tabaroundicon, R.drawable.tabnewsicon};

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];

//        myDrawable = context.getResources().getDrawable(imageResId[position]);
//        title = tabTitles[position];
//        SpannableStringBuilder sb = new SpannableStringBuilder(" \n" + title); // space added before text for convenience
//        try {
//            myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
//            ImageSpan span = new ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BASELINE);
//            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } catch (Exception e) {
//
//        }
//        return sb;
    }
}