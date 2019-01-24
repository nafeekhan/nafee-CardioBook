package com.example.nafee.nafee_cardiobook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    /*
    No writing to storage is done on this activity. This activity only loads from storage and displays
    for selection.
    Clicks allowed on either:
        a. Items
        b. "Add" Button
     Both leads user to AddEditActivity Page which receives the ArrayList of measurements and the
     clicked on  Measurement item, if an item was clicked. If the Add button is clicked then
     this Activity sends the ArrayList of measurements and a pseudo-nullObject Measurement (developer
     defined Measurement with all required fields blank and comment field containing "null".)
     everytime OnResume is called the ArrayList measurements gets refreshed(and so does the screen
     through the adapter)
     */
    public static final String APP_DATA_FILE = "subBook.json";
    static final int INIT_REQUEST = 0;
    static final int ADD_REQUEST = 2;
    static final int EDIT_REQUEST = 1;
    static ArrayList<Measurement> measurements = new ArrayList<Measurement>();
    CardioBookAdapter adapter;// = new CardioBookAdapter(MainActivity.this, measurements);
    ListView lvCardioBook;
    Button btnAdd;
    TextView altText;
    TextView tvCalc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File appFile = new File(getFilesDir(), "subBook.json");
        if (!appFile.exists()) {
            try {
                appFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean init = false;
        if (init) {//measurements.size()==0||!appFile.exists()) {

            altText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent initIntent = new Intent(MainActivity.this, AddEditActivity.class);
                    initIntent.putExtra("editType", 0);
                    startActivityForResult(initIntent, INIT_REQUEST);
                }
            });

        } else {

            setContentView(R.layout.activity_main);
            btnAdd = findViewById(R.id.add_button);
            // = findViewById(R.id.tv_calculate);
            lvCardioBook = findViewById(R.id.lv_cardiobook);

            readJsonFromFile();


            adapter = new CardioBookAdapter(MainActivity.this, R.layout.measurement_item, measurements);
            lvCardioBook.setAdapter(adapter);
            //CalcTotalMongthly(measurements);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    if (measurements.size() == 0) {
                        intent.putExtra("editType", 0);
                        startActivityForResult(intent, INIT_REQUEST);
                    } else {
                        intent.putExtra("editType", 2);
                        startActivityForResult(intent, ADD_REQUEST);
                    }
                }
            });

            lvCardioBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = lvCardioBook.getItemAtPosition(position);
                    Measurement s = (Measurement) o;
                    //Measurement s = (Measurement) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    intent.putExtra("Measurement", s);
                    intent.putExtra("editType", 1);
                    startActivityForResult(intent, EDIT_REQUEST);

                }
            });
        }
    }

    public void readJsonFromFile() {


        Type type = new TypeToken<ArrayList<Measurement>>() {}.getType();
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        if(json.length() != 0 ) {

            ArrayList<Object> list = new ArrayList<Object>();
            measurements = gson.fromJson(json, type);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();


        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(measurements);
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(measurements);
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == EDIT_REQUEST) {//|| requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_CANCELED) {


            } else if (resultCode == RESULT_OK) {

                int edt = data.getExtras().getInt("editOrRemove");
                if (edt == -1) {


                    //Gson gson = new Gson();
                    Measurement m = (Measurement) data.getSerializableExtra("Measurement");
                   // String newName = data.getExtras().getString("newname");

                    for (int x = 0; x < measurements.size(); x++) {
                        Measurement s = measurements.get(x);
                        if (s.getId() == m.getId()) {
                            measurements.remove(x);
                        }
                    }
                    if (lvCardioBook.getAdapter() == null) {
                        adapter = new CardioBookAdapter(MainActivity.this, R.layout.measurement_item, measurements);
                        lvCardioBook.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }

                } else if (edt == 0) {


                    Gson gson = new Gson();
                    Measurement m = (Measurement) gson.fromJson(data.getStringExtra("Measurement"), Measurement.class);                    for (int x = 0; x < measurements.size(); x++) {
                        Measurement s = measurements.get(x);
                        if (s.getId() == m.getId()){
                            measurements.remove(x);
                            measurements.add(m);
                        }

                    }
                    //adapter.notifyDataSetChanged();
                    if (lvCardioBook.getAdapter() == null) {
                        adapter = new CardioBookAdapter(MainActivity.this, R.layout.measurement_item, measurements);
                        lvCardioBook.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }

                }


            }
        } else if (requestCode == INIT_REQUEST || requestCode == ADD_REQUEST) {


            if (resultCode == RESULT_CANCELED) {

            } else if (resultCode == RESULT_OK) {

                //Measurement m = (Measurement) getIntent().getSerializableExtra("Measurement");
                Gson gson = new Gson();
                Measurement m = (Measurement) gson.fromJson(data.getStringExtra("Measurement"), Measurement.class);
                measurements.add(m);

                if (lvCardioBook.getAdapter() == null) {
                    adapter = new CardioBookAdapter(MainActivity.this, R.layout.measurement_item, measurements);
                    lvCardioBook.setAdapter(adapter);
                } else {

                    adapter.notifyDataSetChanged();

                }




            }
        }

    }

}