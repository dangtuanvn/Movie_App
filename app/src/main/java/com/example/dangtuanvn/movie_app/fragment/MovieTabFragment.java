package com.example.dangtuanvn.movie_app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.viewmodel.MyDataBindingComponent;
import com.example.dangtuanvn.movie_app.viewmodel.TabViewModel;

/**
 * Created by dangtuanvn on 11/22/16.
 */

public class MovieTabFragment extends Fragment {
    public enum CinemaTab {
        Showing,
        Upcoming
    }

    private TabViewModel vm;
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

    @Override
    public void onDestroy(){
        if(vm != null){
            vm.onDestroy();
        }
        super.onDestroy();
    }
}
