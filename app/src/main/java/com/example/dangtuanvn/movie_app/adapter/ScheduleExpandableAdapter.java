package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.ScheduleCinemaGroupList;

import java.util.List;

/**
 * Created by dangtuanvn on 11/17/16.
 */

public class ScheduleExpandableAdapter  extends ExpandableRecyclerAdapter<ScheduleExpandableAdapter.GroupViewHolder, ChildViewHolder> {
    private LayoutInflater inflater;
    private List<ScheduleCinemaGroupList> groupList;
    private OnItemClick onItemClick;
    private Context context;

    public ScheduleExpandableAdapter(Context context, List<ScheduleCinemaGroupList> groupList) {
        super(groupList);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.groupList = groupList;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return super.onCreateViewHolder(viewGroup, viewType);
    }

    @Override
    public GroupViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
//        View view = inflater.inflate(R.layout.list_group, parentViewGroup, false);
//        return new GroupViewHolder(view);
        View view = inflater.inflate(R.layout.group_cinema_item, parentViewGroup, false);
        return new GroupViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
//        View view = inflater.inflate(R.layout.list_view_item, childViewGroup, false);
//        return new GroupCinemaViewHolder(view);
        View view = inflater.inflate(R.layout.cinema_item, childViewGroup, false);
        return new GroupCinemaViewHolder(view);
    }


    @Override
    public void onBindParentViewHolder(GroupViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        ScheduleCinemaGroupList group = (ScheduleCinemaGroupList) parentListItem;
        parentViewHolder.bind(group);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int position, Object childListItem) {
        final Schedule schedule = (Schedule) childListItem;
        GroupCinemaViewHolder postView = (GroupCinemaViewHolder) childViewHolder;
        postView.bind(schedule);
    }

    static class GroupViewHolder extends ParentViewHolder {
        private TextView groupTitleText;
        private GroupViewHolder(View itemView) {
            super(itemView);
            groupTitleText = (TextView) itemView.findViewById(R.id.lblListHeader);
        }

        private void bind(ScheduleCinemaGroupList group){
            groupTitleText.setText(group.getTitle() + " (" + group.getChildItemList().size() + ")");
        }
    }

    private class GroupCinemaViewHolder extends ChildViewHolder {
        private TextView cinemaLocationNameText;
        private RecyclerView cinemaGroupView;
//        private OnItemClick onItemClick;

        private GroupCinemaViewHolder(View itemView) {
            super(itemView);
            cinemaLocationNameText = (TextView) itemView.findViewById(R.id.cinema_location_name);
            cinemaGroupView = (RecyclerView) itemView.findViewById(R.id.group_cinema_view);
            cinemaGroupView.addItemDecoration(new SimpleDividerItemDecoration(context));
        }

        private void bind(Schedule schedule){
            // TODO: BIND ITEM
            cinemaLocationNameText.setText(schedule.getCinemaName());

            GroupCinemaAdapter groupCinemaAdapter = new GroupCinemaAdapter(context, schedule.getListSessions());
            cinemaGroupView.setAdapter(groupCinemaAdapter);
            cinemaGroupView.setLayoutManager(new LinearLayoutManager(context));


//            itemView.setTag(schedule);
        }
    }

    public interface OnItemClick {
        void onItemClick(View view, Object data, int position);
    }

    private class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        private SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.session_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft() + 195;
            int right = parent.getWidth() - parent.getPaddingRight() - 100;

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin - 10;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}

