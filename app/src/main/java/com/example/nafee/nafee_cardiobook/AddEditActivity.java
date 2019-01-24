package com.example.nafee.nafee_cardiobook;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
//import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
//import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonWriter;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Boolean.TRUE;


public class AddEditActivity extends AppCompatActivity {
    // String regex = "^\\d{4}-\\d{2}-\\d{2}$";

    EditText editSystolic;
    EditText editComment;
    EditText editDiastolic;
    EditText editHeartRate;
    EditText pickDate;
    EditText pickTime;
    Button btnSubmit, btnDelete, btnEdit;
    private final Calendar myCalender = Calendar.getInstance();

    static final int ADD_INIT = 0;
    static final int EDIT_TXT = 1;
    static final int ADD_NEW = 2;
    static int type;
    String oldName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        // get current subscription list and get the subscription to be edited from Intent
        // if Add was clicked then expect toBeEdited.isNull()
        editSystolic = (EditText) findViewById(R.id.et_systolic);
        editDiastolic = (EditText) findViewById(R.id.et_diastolic);
        editHeartRate = (EditText) findViewById(R.id.et_heartrate);
        editComment = (EditText) findViewById(R.id.et_comment);
        pickDate = (EditText) findViewById(R.id.et_date);
        pickTime = (EditText) findViewById(R.id.et_time);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        pickDate.setKeyListener(null);
        pickDate.setClickable(false);
        pickTime.setKeyListener(null);
        pickTime.setClickable(false);

        Intent received = getIntent();
        type = received.getExtras().getInt("editType");
        if (type == ADD_INIT) {
            /*isName = false;
            isDate = false;
            isMonthly = false;*/
            pickDate.setClickable(true);
            pickTime.setClickable(true);
            Measurement measurement = new Measurement();
            handleEditing(measurement);
        } else if (type == ADD_NEW) {
            //subBook.addAll((ArrayList<Subscription>) getIntent().getSerializableExtra("CurrentSubbook"));

            /*isName = false;
            isDate = false;
            isMonthly = false;*/
            pickDate.setClickable(true);
            pickTime.setClickable(true);
            Measurement measurement = new Measurement();
            handleEditing(measurement);
        } else if (type == EDIT_TXT) {


            final Measurement m = (Measurement) received.getSerializableExtra("Measurement");
            //pickDate.setText(oldDate);
            editSystolic.setText(m.getSystolicPressure().toString());
            editDiastolic.setText(m.getDiastolicPressure().toString());
            editHeartRate.setText(m.getHeartRate().toString());
            editComment.setText(m.getComment());
            pickTime.setText(m.getTime());
            pickDate.setText(m.getDate());

            editSystolic.setEnabled(false);
            editDiastolic.setEnabled(false);
            editHeartRate.setEnabled(false);
            editComment.setEnabled(false);

            btnSubmit.setVisibility(View.INVISIBLE);
            btnSubmit.setClickable(false);
            pickDate.setClickable(false);
            pickTime.setClickable(false);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!editSystolic.isEnabled() && !editDiastolic.isEnabled() && !editHeartRate.isEnabled() && !editComment.isEnabled()) {

                        editSystolic.setEnabled(true);
                        editComment.setEnabled(true);
                        editDiastolic.setEnabled(true);
                        editHeartRate.setEnabled(true);
                        //pickDate.setClickable(true);

                        //btnEdit.setVisibility(View.GONE);
                        //btnDelete.setVisibility(View.GONE);
                        btnDelete.setVisibility(View.INVISIBLE);
                        btnDelete.setClickable(false);
                        btnEdit.setVisibility(View.INVISIBLE);
                        btnEdit.setClickable(false);
                        btnSubmit.setVisibility(View.VISIBLE);


                        /*isName = true;
                        isDate = true;
                        isMonthly = true;*/

                        handleEditing(m);
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    for (int i = 0; i < subBook.size(); i++) {
                        Subscription s = subBook.get(i);
                        if (s.equals(toBeEdited)) {
                            subBook.remove(i);
                        }
                    }
                    try {
                        FileWriter writer = new FileWriter("subBook.json", false);
                        writer.write("");
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    //storeUpdatedSubbook();

                    Intent result = new Intent();
                    //result.putExtra("newname",oldName);
                    result.putExtra("Measurement", m);
                    result.putExtra("editOrRemove", -1);

                    setResult(RESULT_OK, result);
                    finish();
                }
            });


        }
    }
/*
    private void updateDateLabel() {

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        pickDate.setText(df.format(myCalender.getTime()));
        handleEditing();
        //isDate = true;
    }

    private void updateTimeLabel() {

        DateFormat tf = new SimpleDateFormat("HH:mm z");
        pickTime.setText(tf.format(myCalender.getTime()));
        handleEditing();
    }
*/
    private void handleEditing(final Measurement measurement) {

        //if(!pickDate.isClickable()){
        //pickDate.setClickable(true);
        //}
        //final long longDate = (long) myCalender.getTimeInMillis();
        //btnDelete.setVisibility(View.GONE);
        //btnEdit.setVisibility(View.GONE);
        btnDelete.setVisibility(View.INVISIBLE);
        btnDelete.setClickable(false);
        btnEdit.setVisibility(View.INVISIBLE);
        btnEdit.setClickable(false);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //updateDateLabel();
                DateFormat tf = new SimpleDateFormat("yyyy.MM.dd");
                pickDate.setText(tf.format(myCalender.getTime()));
                //handleEditing(m);
            }
        };

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
                        myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalender.set(Calendar.MINUTE, minute);

                //updateTimeLabel();
                DateFormat tf = new SimpleDateFormat("HH:mm z");
                pickTime.setText(tf.format(myCalender.getTime()));
            }
        };

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddEditActivity.this, time, myCalender.get(Calendar.HOUR_OF_DAY), myCalender.get(Calendar.MINUTE), TRUE).show();
            }
        });



        //Editing Comment

        int commentLength = 30;
        editComment.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(commentLength)
        });

        //Editing Monthly




        //SubmitButton Click event

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {


                    //newEdited.setName(editName.getText().toString());
                    //newEdited.setDate(pickDate.getText().toString());
                    //int newMonthly = Integer.parseInt(editMonthly.getText().toString());

                    //newEdited.setMonthly(result);
                    //newEdited.setComment(editComment.getText().toString());
                    //subBook.add(newEdited);
                    if (type == ADD_INIT || type == ADD_NEW) {

                        //clear jSON file
                        /*FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("subBook.json", Context.MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        PrintWriter writer = new PrintWriter(fos);
                        writer.print("");
                        writer.close();*/

                        Integer systolic = Integer.valueOf(editSystolic.getText().toString());
                        Integer diastolic = Integer.valueOf(editDiastolic.getText().toString());
                        Integer heartrate = Integer.valueOf(editHeartRate.getText().toString());
                        String date = pickDate.getText().toString();
                        String time = pickTime.getText().toString();
                        String comment = editComment.getText().toString();
                        Measurement measurement = new Measurement(date, time, systolic, diastolic, heartrate, comment);
                        measurement.setSystolicPressure(systolic);
                        measurement.setDiastolicPressure(diastolic);
                        measurement.setHeartRate(heartrate);
                        measurement.setDate(date);
                        measurement.setTime(time);
                        measurement.setComment(comment);

                        Intent result = new Intent();
                        //result.putExtra("Measurement", measurement);
                        Gson gson = new Gson();
                        String myjson = gson.toJson(measurement);
                        result.putExtra("Measurement", myjson);
                        setResult(RESULT_OK, result);
                        finish();

                        //storeUpdatedSubbook();

                    } else {

                        Integer systolic = Integer.valueOf(editSystolic.getText().toString());
                        Integer diastolic = Integer.valueOf(editDiastolic.getText().toString());
                        Integer heartrate = Integer.valueOf(editHeartRate.getText().toString());
                        String date = pickDate.getText().toString();
                        String time = pickTime.getText().toString();
                        String comment = editComment.getText().toString();
                        //Measurement measurement = new Measurement(date, time, systolic, diastolic, heartrate, comment);
                        measurement.setSystolicPressure(systolic);
                        measurement.setDiastolicPressure(diastolic);
                        measurement.setHeartRate(heartrate);
                        measurement.setDate(date);
                        measurement.setTime(time);
                        measurement.setComment(comment);



                        Intent result = new Intent();
                        result.putExtra("editOrRemove", 0);
                        //result.putExtra("Measurement", measurement);
                        Gson gson = new Gson();
                        String myjson = gson.toJson(measurement);
                        result.putExtra("Measurement", myjson);
                        //Bundle b = new Bundle();
                        //b.putSerializable("")
                        setResult(RESULT_OK, result);

                        finish();
                        //storeUpdatedSubbook();
                    }

                }
            }
        });


    }
    private boolean validation(){
        boolean systolicValid = false;
        boolean diastolicValid = false;
        boolean heartValid = false;
        boolean dateValid = false;
        boolean timeValid = false;

        if (editSystolic.getText().toString().length() > 0) {
            systolicValid = true;
        }
        if (editDiastolic.getText().toString().length() > 0) {
            diastolicValid = true;
        }
        if (editHeartRate.getText().toString().length() > 0) {
            heartValid = true;
        }
        if (pickDate.getText().toString().length() > 0) {
            dateValid = true;
        }
        if (pickTime.getText().toString().length() > 0) {
            timeValid = true;
        }

        return (systolicValid && diastolicValid && heartValid && dateValid && timeValid);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent result = new Intent();
        setResult(Activity.RESULT_CANCELED, result);
        finish();
    }
}
