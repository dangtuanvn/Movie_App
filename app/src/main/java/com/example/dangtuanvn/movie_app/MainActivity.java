package com.example.dangtuanvn.movie_app;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // int array contain icon for tabs
    private TabLayout tabLayout;
    private int[] imageResId = {R.drawable.tabshowing, R.drawable.tabupcomingicon, R.drawable.tabaroundicon, R.drawable.tabnewsicon};


    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
                if(fragment != null){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.remove(fragment);
                    transaction.commit();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 4; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }
    }
}