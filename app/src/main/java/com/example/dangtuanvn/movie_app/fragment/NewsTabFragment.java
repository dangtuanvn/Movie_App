package com.example.dangtuanvn.movie_app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.databinding.MovieTabRecyclerBinding;
import com.example.dangtuanvn.movie_app.viewmodel.MyDataBindingComponent;
import com.example.dangtuanvn.movie_app.viewmodel.ViewModelVM;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dangtuanvn on 11/22/16.
 */

public class NewsTabFragment extends Fragment {
    private CompositeSubscription compositeSubscription;
    public static NewsTabFragment newInstance() {
        NewsTabFragment fragment = new NewsTabFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateListView(inflater, container);
        return view;
    }

    public View inflateListView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.movie_tab_recycler, container, false);

        SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.green));

        // Applying 2nd answer
        // http://stackoverflow.com/questions/39283855/what-is-databindingcomponent-class-in-android-databinding
        // This has to happened before binding the view
        // Doing this to avoid casting static context in Databinding functions

        DataBindingUtil.setDefaultComponent(new MyDataBindingComponent(getContext()));

        MovieTabRecyclerBinding binding = DataBindingUtil.bind(view);
        ViewModelVM vm = new ViewModelVM(getContext(), swipeLayout);
        vm.getNewsData();

        binding.setViewModelVM(vm);
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
