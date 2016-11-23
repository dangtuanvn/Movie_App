package com.example.dangtuanvn.movie_app;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.dangtuanvn.movie_app.adapter.TabViewPagerAdapter;
import com.example.dangtuanvn.movie_app.fragment.MovieDetailFragment;
import com.example.dangtuanvn.movie_app.fragment.MovieTabFragment;

public class MainActivity extends AppCompatActivity {
    // int array contain icon for tabs
    private TabLayout tabLayout;
    private int[] imageResId = {R.drawable.tabshowing, R.drawable.tabupcomingicon, R.drawable.tabaroundicon, R.drawable.tabnewsicon};

    TabViewPagerAdapter adapter;
    ViewPager viewPager;

    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0) {
            if (adapter.getItem(0) instanceof MovieDetailFragment) {
                ((MovieDetailFragment) adapter.getItem(0)).backPressed();
            }
            else if (adapter.getItem(0) instanceof MovieTabFragment) {
                finish();
            }
        }

        if(viewPager.getCurrentItem() == 1) {
            if (adapter.getItem(1) instanceof MovieDetailFragment) {
                ((MovieDetailFragment) adapter.getItem(1)).backPressed();
            }
            else if (adapter.getItem(1) instanceof MovieTabFragment) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new TabViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);



        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
//        for (int i = 0; i < 4; i++) {
//            tabLayout.getTabAt(i).setIcon(imageResId[i]);
//        }
        
    }
}