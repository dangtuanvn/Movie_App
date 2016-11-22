package com.example.dangtuanvn.movie_app.adapter.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.model.SessionTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dangtuanvn on 11/18/16.
 */

public class SessionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_OVERTIME = 2;
    private List<SessionTime> listSessionTime;
    private Context context;

    public SessionAdapter(Context context, List<SessionTime> listSessionTime) {
        this.listSessionTime = listSessionTime;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        if(viewType == TYPE_OVERTIME){
            LayoutInflater inflater = LayoutInflater.from(context);
            View listView = inflater.inflate(R.layout.grid_session_time_overtime, parent, false);
            return new ViewHolder(listView);
        }
        else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View listView = inflater.inflate(R.layout.grid_session_time, parent, false);
            return new ViewHolder(listView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
//        if(viewHolder instanceof RecyclerListAdapter.FooterViewHolder) {
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, PostViewActivity.class);
//                    intent.putExtra("url", "https://www.reddit.com/r/androiddev/");
//                    mContext.startActivity(intent);
//                }
//            });
//        }

        SessionTime sessionTime = listSessionTime.get(position);
        SessionAdapter.ViewHolder itemViewHolder = (SessionAdapter.ViewHolder) viewHolder;

        try {
            String sessionTimeString = sessionTime.getSessionTime();
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            DateFormat targetFormat = new SimpleDateFormat("HH:mm");
            Date date = originalFormat.parse(sessionTimeString);
            String formattedDate = targetFormat.format(date);  // 20120821
            itemViewHolder.sessionTimeText.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//          viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, PostViewActivity.class);
//                    intent.putExtra("url", postList.get(position).getUrl());
//                    mContext.startActivity(intent);
//                }
//            });
    }

    @Override
    public int getItemCount() {
        return listSessionTime.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sessionTimeText;

        private ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            sessionTimeText = (TextView) itemView.findViewById(R.id.session_time);
        }
    }

    @Override
    public int getItemViewType (int position) {
        if(isOvertime (position)) {
            return TYPE_OVERTIME;
        }
        return TYPE_ITEM;
    }

    private boolean isOvertime(int position) {
        return listSessionTime.get(position).getStatusId() == SessionTime.StatusId.OVERTIME;
    }
}

