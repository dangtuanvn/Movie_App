package com.example.dangtuanvn.movie_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

//import com.example.dangtuanvn.movie_app.MovieDetailActivity;
import com.example.dangtuanvn.movie_app.NoInternetActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.viewmodel.MyDataBindingComponent;
import com.example.dangtuanvn.movie_app.viewmodel.TabViewModel;

import java.util.List;

/**
 * Created by dangtuanvn on 11/22/16.
 */

public class MovieTabFragment extends Fragment {
    public enum CinemaTab {
        Showing,
        Upcoming
    }

    private TabViewModel vm;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private CinemaTab tab;

    public static MovieTabFragment newInstance(CinemaTab tab) {
        Bundle args = new Bundle();
        args.putSerializable("cinema_tab", tab);
        MovieTabFragment fragment = new MovieTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab = (CinemaTab) getArguments().getSerializable("cinema_tab");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateListView(inflater, container);
        return view;
    }

    public View inflateListView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.view_pager_tab, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.green));

        switch (tab) {
            case Showing:
                DataBindingUtil.setDefaultComponent(new MyDataBindingComponent(getContext(), 0));
                vm = new TabViewModel(getContext(), swipeLayout, 0);
                break;
            case Upcoming:
                DataBindingUtil.setDefaultComponent(new MyDataBindingComponent(getContext(), 1));
                vm = new TabViewModel(getContext(), swipeLayout, 1);

                break;
        }

        com.example.dangtuanvn.movie_app.databinding.ViewPagerTabBinding binding = DataBindingUtil.bind(view);
        vm.getListData();
        binding.setTabViewModel(vm);
        return view;
    }

    public void addOnMovieTouch(final List<Movie> movieList) {
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // Check for network connection
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    final View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && mGestureDetector.onTouchEvent(e)) {
//                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
//                        intent.putExtra("movieId", movieList.get(rv.getChildAdapterPosition(childView)).getFilmId());
//                        intent.putExtra("posterUrl", movieList.get(rv.getChildAdapterPosition(childView)).getPosterLandscape());
//
//                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(getActivity(), NoInternetActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }
}
