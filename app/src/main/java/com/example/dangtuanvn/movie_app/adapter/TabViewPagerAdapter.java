package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.dangtuanvn.movie_app.AllTabFragment;
import com.example.dangtuanvn.movie_app.NoInternetFragment;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.fragment.CinemaTabFragment;
import com.example.dangtuanvn.movie_app.fragment.MovieDetailFragment;
import com.example.dangtuanvn.movie_app.fragment.MovieTabFragment;
import com.example.dangtuanvn.movie_app.fragment.MovieTabFragmentListener;
import com.example.dangtuanvn.movie_app.fragment.NewsTabFragment;

/**
 * Created by sinhhx on 11/7/16.
 */
public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    private final class FirstPageListener implements MovieTabFragmentListener {
        public void onSwitchToNextFragment(int movieId, String posterUrl) {
            fragmentManager.beginTransaction().remove(mFragmentAtPos0).commit();
            if (mFragmentAtPos0 instanceof MovieTabFragment){
                mFragmentAtPos0 = new MovieDetailFragment(firstListener, movieId, posterUrl);
            }else{ // Instance of NextFragment
                mFragmentAtPos0 = new MovieTabFragment(firstListener, MovieTabFragment.CinemaTab.values()[0]);
            }
            notifyDataSetChanged();
        }
    }

    private final class SecondPageListener implements MovieTabFragmentListener {
        public void onSwitchToNextFragment(int movieId, String posterUrl) {
            fragmentManager.beginTransaction().remove(mFragmentAtPos1).commit();
            if (mFragmentAtPos1 instanceof MovieTabFragment){
                mFragmentAtPos1 = new MovieDetailFragment(secondListener, movieId, posterUrl);
            }else{ // Instance of NextFragment
                mFragmentAtPos1 = new MovieTabFragment(secondListener, MovieTabFragment.CinemaTab.values()[1]);
            }
            notifyDataSetChanged();
        }
    }

    private FirstPageListener firstListener = new FirstPageListener();
    private SecondPageListener secondListener = new SecondPageListener();
    private final static int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Showing", "Upcoming", "Cinema around", "News" };
    private Context context;
    private FragmentManager fragmentManager;
    private Fragment mFragmentAtPos0, mFragmentAtPos1;

    public TabViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
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
            if (position == 0){
                if (mFragmentAtPos0 == null)
                {
                    mFragmentAtPos0 = new MovieTabFragment(firstListener, MovieTabFragment.CinemaTab.values()[position]);
                }
                return mFragmentAtPos0;

            }
            else if(position == 1) {
                if (mFragmentAtPos1 == null)
                {
                    mFragmentAtPos1 = new MovieTabFragment(secondListener, MovieTabFragment.CinemaTab.values()[position]);
                }
                return mFragmentAtPos1;

            } else if (position == 2) {
                return NewsTabFragment.newInstance();
            } else if (position == 3) {
                return NewsTabFragment.newInstance();
            }
        }
        else{
        return NoInternetFragment.newInstance();
        }
        return null;
    }

    private int[] imageResId = {R.drawable.tabshowing, R.drawable.tabupcomingicon, R.drawable.tabaroundicon, R.drawable.tabnewsicon};
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
//        Drawable image = context.getResources().getDrawable(imageResId[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        SpannableString sb = new SpannableString(tabTitles[position]);
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BASELINE);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;
    }

    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof MovieTabFragment &&
                mFragmentAtPos0 instanceof MovieDetailFragment) {
            return POSITION_NONE;
        }
        if (object instanceof MovieDetailFragment &&
                mFragmentAtPos0 instanceof MovieTabFragment) {
            return POSITION_NONE;
        }
        if (object instanceof MovieTabFragment &&
                mFragmentAtPos1 instanceof MovieDetailFragment) {
            return POSITION_NONE;
        }
        if (object instanceof MovieDetailFragment &&
                mFragmentAtPos1 instanceof MovieTabFragment) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

}