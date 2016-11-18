package com.example.dangtuanvn.movie_app.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangtuanvn on 11/17/16.
 */

public class ScheduleCinemaGroupList implements ParentListItem {
    private List<Schedule> mChildrenList;

    private int id;

    public ScheduleCinemaGroupList(int id) {
        this.id = id;
        mChildrenList = new ArrayList<Schedule>();
    }
    @Override
    public List<Schedule> getChildItemList() {
        return mChildrenList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getTitle() {
        switch (id) {
            case 1:
                return "Lotte Cinema";

            case 2:
                return "Galaxy Cinema";

            case 3:
                return "CGV Cinemas";

            case 4:
                return "BHD Star Cineplex";

            case 13:
                return "Cụm rạp khác";

            case 16:
                return "CineStar";

            case 17:
                return "Mega GS";

            default:
                return null;
        }
    }

    public void addChildObjectList(Schedule post) {
        mChildrenList.add(post);
    }

    public int getId() { return id; }
}