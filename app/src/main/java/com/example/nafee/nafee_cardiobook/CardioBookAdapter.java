package com.example.nafee.nafee_cardiobook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



/**
 * Created by nafeekhan on 2018-02-04.
 */

public class CardioBookAdapter extends ArrayAdapter<Measurement>{
    static class Viewholder {
        TextView tvSystolic;
        TextView tvDiastolic;
        TextView tvHeartRate;
        TextView tvDate;
    }

    //private Context con;
    private final int res;
    //private LayoutInflater mInflater;
    private static ArrayList<Measurement> roster;

    //public int getCount(){
    //return roster.size();
    //}
    public CardioBookAdapter(Context context, int resource, ArrayList<Measurement> roster){
        super(context, resource, roster);
        //this.con = context;
        this.res = resource;
        this.roster = roster;
        //this.mInflater = (LayoutInflater) LayoutInflater.from(context);
    }
    //@Override
    //public Measurement getItem(int position){
    //return roster.get(position);
    //}
    //public long getItemId(int position) {
    //return position;
    //}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Viewholder holder;
        View v = convertView;
        Measurement measurement = roster.get(position);
        if (convertView == null){
            //convertView = mInflater.inflate(R.layout.Measurement_item, parent,false);
            //convertView = mInflater.inflate(R.layout.Measurement_item, null);
            //v = mInflater.inflate(R.layout.Measurement_item, null);
            LayoutInflater li = (LayoutInflater) LayoutInflater.from(getContext());
            v = li.inflate(R.layout.measurement_item, parent, false);
            holder = new Viewholder();
            holder.tvSystolic = (TextView) v.findViewById(R.id.tv_systolic);
            holder.tvDiastolic = (TextView) v.findViewById(R.id.tv_diastolic);
            holder.tvHeartRate = (TextView) v.findViewById(R.id.tv_heartrate);
            holder.tvDate = (TextView) v.findViewById(R.id.tv_date);
            v.setTag(holder);
        }else{
            holder = (Viewholder) v.getTag();
        }

        holder.tvSystolic.setText("Systolic Preassure: " + measurement.getSystolicPressure().toString());
        holder.tvDiastolic.setText("Diastolic Pressure: " + measurement.getDiastolicPressure().toString());
        holder.tvHeartRate.setText("Heartrate: " + measurement.getHeartRate().toString());
        holder.tvHeartRate.setTextColor(Color.BLACK);
        //DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        holder.tvDate.setText(measurement.getDate());
        if(measurement.getSystolicPressure() > 140 || measurement.getSystolicPressure() < 90){
            holder.tvSystolic.setTextColor(Color.RED);
        } else {
            holder.tvSystolic.setTextColor(Color.BLACK);
        }
        if(measurement.getDiastolicPressure() > 90 || measurement.getDiastolicPressure() < 60){
            holder.tvDiastolic.setTextColor(Color.RED);
        } else {
            holder.tvDiastolic.setTextColor(Color.BLACK);
        }

        //return convertView;
        return v;
    }

    public void refreshRoster(ArrayList<Measurement> newRoster){
        this.roster.clear();
        this.roster.addAll(newRoster);
        notifyDataSetChanged();
    }

}