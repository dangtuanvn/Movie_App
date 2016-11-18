package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sinhhx on 11/18/16.
 */
public class MovieScheduleAdapter extends BaseAdapter {
    private final Context context;
    private Calendar date;

    public MovieScheduleAdapter(Context context, Calendar date) {

        this.date= date;
        this.context = context;
    }
    static class ViewHolder {
        TextView date;
        TextView time;


    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.movie_date, parent, false);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();

        }
        if(position>0) {
            date.add(Calendar.DATE, 1);
        }
            SimpleDateFormat sdf = new SimpleDateFormat("EE");


            String dayOfTheWeek = sdf.format(date.getTime());
//            if(position==0){
//            holder.date.setText("Today");}
//            else {
                holder.date.setText(dayOfTheWeek);

           // }
        SimpleDateFormat df = new SimpleDateFormat("dd-MM");
        String formattedDate = df.format(date.getTime());
        holder.time.setText(formattedDate);

        return convertView;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
