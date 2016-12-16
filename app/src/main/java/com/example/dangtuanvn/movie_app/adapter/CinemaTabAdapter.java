//package com.example.dangtuanvn.movie_app.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.dangtuanvn.movie_app.R;
//import com.example.dangtuanvn.movie_app.model.Cinema;
//
//import java.util.List;
//
///**
// * Created by sinhhx on 11/11/16.
// */
//public class CinemaTabAdapter extends TabAdapter {
//    private List<Cinema> cinemaList;
//
//    public CinemaTabAdapter(Context context, List<Cinema> cinemaList) {
//        super(context, cinemaList);
//        this.cinemaList = cinemaList;
//    }
//
//
//    protected static class ViewHolder extends RecyclerView.ViewHolder {
//        private ImageView cinemaIcon;
//        private TextView cinemaName;
//        private TextView cinemaAddress;
//        private TextView distanceText;
//        private ViewHolder(View itemView) {
//            super(itemView);
//            cinemaIcon = (ImageView) itemView.findViewById(R.id.location_icon);
//            cinemaName = (TextView) itemView.findViewById(R.id.cinema_name);
//            cinemaAddress = (TextView) itemView.findViewById(R.id.address);
//            distanceText = (TextView) itemView.findViewById(R.id.distance);
//        }
//    }
//
//    public CinemaTabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_detail, parent, false);
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        CinemaTabAdapter.ViewHolder cinemaHolder = (CinemaTabAdapter.ViewHolder) holder;
//        cinemaHolder.cinemaIcon.setImageResource(R.drawable.cinema_holder);
//        cinemaHolder.cinemaName.setText(cinemaList.get(position).getCinemaName());
//        cinemaHolder.cinemaAddress.setText(cinemaList.get(position).getCinemaAddress());
//        double distance = cinemaList.get(position).getDistance();
//        if(distance < 100) {
//            cinemaHolder.distanceText.setText(String.format("%.2f", distance));
//        }
//        else{
//            cinemaHolder.distanceText.setText(">100");
//        }
//    }
//}
