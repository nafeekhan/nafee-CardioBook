package com.example.nafee.nafee_cardiobook;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nafeekhan on 2018-02-04.
 */

public class Measurement implements Serializable {
    private String date;
    private String time;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private Integer heartRate;
    private String comment;
    private long Id;


    public Measurement(String date, String time, Integer systolicPressure, Integer diastolicPressure, Integer heartRate, @Nullable String comment) {
        this.date = date;
        this.time = time;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.comment = comment;
        this.Id = System.currentTimeMillis();

    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    public Measurement(Integer systolicPressure, Integer diastolicPressure, Integer heartRate, @Nullable String comment) {
        //this.date = new Date();
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        DateFormat df = new SimpleDateFormat("HH:mm");
        this.time = df.format(time);
        DateFormat tf = new SimpleDateFormat("yyyy.MM.dd");
        this.date = tf.format(time);
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.comment = comment;
        this.Id = System.currentTimeMillis();

    }

    public Measurement() {
        this.Id = System.currentTimeMillis();
    }

    public long getId() {
        return Id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //@TargetApi(Build.VERSION_CODES.KITKAT)
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //@Override
    /*public boolean equals(Object obj) {

        if (obj instanceof Subscription) {
            Subscription s = (Subscription) obj;
            if (s.getName() == null) {
                return false;
            }
            String n = this.name;
            String sn = s.getName();
            return sn.equals(n);
        } else {
            return false;
        }

    }*/
}
