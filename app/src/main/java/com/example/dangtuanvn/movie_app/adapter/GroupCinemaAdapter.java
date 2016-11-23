package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.model.Schedule;

import java.util.List;

/**
 * Created by dangtuanvn on 11/18/16.
 */

public class GroupCinemaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Schedule.Session> listSessions;
    private Context context;

    public GroupCinemaAdapter(Context context, List<Schedule.Session> listSessions) {
        this.listSessions = listSessions;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.session_view_item, parent, false);
        return new ViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Schedule.Session session = listSessions.get(position);

        GroupCinemaAdapter.ViewHolder itemViewHolder = (GroupCinemaAdapter.ViewHolder) viewHolder;
        itemViewHolder.version.setText(session.getVersion().split("\\s+")[0]);

        SessionAdapter sessionAdapter = new SessionAdapter(session.getListTime());

        itemViewHolder.sessionGroupView.setAdapter(sessionAdapter);
        itemViewHolder.sessionGroupView.setLayoutManager(new GridLayoutManager(context, 4));

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
        return listSessions.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView version;
        private RecyclerView sessionGroupView;

        private ViewHolder(View itemView) {
            super(itemView);
            version = (TextView) itemView.findViewById(R.id.version_text);
            sessionGroupView = (RecyclerView) itemView.findViewById(R.id.session_view);
        }
    }
}

