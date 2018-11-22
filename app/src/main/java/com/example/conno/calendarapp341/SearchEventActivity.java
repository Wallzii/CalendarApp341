package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SearchEventActivity extends AppCompatActivity {

    Switch rangeQuery;
    EditText dateFrom;
    EditText dateTo;
    Spinner calendarCategory;
    Button requestEvents;
    LinearLayout toDateLayout;
    TextView displayEvents;
    TextView eventsTitle;

    boolean rangeBoolean;



    //////////////////////////
    //      Stuff I wont need later
    Data d = new Data(SearchEventActivity.this);
    ////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        rangeQuery = (Switch) findViewById(R.id.switchRagne);
        rangeQuery.setChecked(false);
        dateFrom = (EditText) findViewById(R.id.fromDateInputSearch);
        dateTo = (EditText) findViewById(R.id.toDateInputSearch);
        calendarCategory = (Spinner) findViewById(R.id.categorySpinnerSearch);
        requestEvents = (Button) findViewById(R.id.requestEvents);
        displayEvents = (TextView) findViewById(R.id.displayEventsSearch);
        eventsTitle = (TextView) findViewById(R.id.eventsTitle);

        displayEvents.setVisibility(View.INVISIBLE);
        eventsTitle.setVisibility(View.INVISIBLE);

        toDateLayout = (LinearLayout) findViewById(R.id.toDateLayout);

        rangeBoolean = false;
        toDateLayout.setVisibility(View.INVISIBLE);

        //////////////////////////
        //      Stuff I wont need later
        Log.d("onCreate", "About to create data");
        d.writeData();
        Log.d("onCreate", "data written");
        d.loadEvents();
        Log.d("onCreate", "data loaded");
        ////////////////////////

        Intent intent = getIntent();

        onClickListenter();



    }
    public void onClickListenter(){
        requestEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateFrom.getText().toString().contains("/")){
                    Toast.makeText(SearchEventActivity.this, "Please separate with spaces", Toast.LENGTH_SHORT).show();
                }

                String eventsDisplay = "";

                ArrayList<Event> events = d.events;

                //Converting input from EditText views
                DateFormat df = new SimpleDateFormat("dd MM yyyy");
                Date dateFirst = null;
                Date dateSecond = null;


                if(rangeBoolean){
                try {
                    dateFirst = df.parse(dateFrom.getText().toString());
                    dateSecond = df.parse(dateTo.getText().toString());
                }catch(Exception e){
                    Log.d("error", "parse error");
                }

                GregorianCalendar cal1 = new GregorianCalendar();
                GregorianCalendar cal2 = new GregorianCalendar();
                cal1.setTime(dateFirst);
                cal2.setTime(dateSecond);

                Log.d("display dates", cal1.toString());
                Log.d("display dates", cal2.toString());

                    for(Event e: events){
                        Log.d("search", "adding event to string");
                        String currentEvent = "";
                        if(e.getDate().compareTo(cal1) > -1 && e.getDate().compareTo(cal2) < 1 && (calendarCategory.getSelectedItem().toString().equals("All") || calendarCategory.getSelectedItem().toString().equals(e.getTAG()))){
                            currentEvent += "Title: " + e.getEventName() +"\n" +
                                    "Date: " + e.getDate().get(Calendar.DAY_OF_MONTH) + "/" + e.getDate().get(Calendar.MONTH) + "/" + e.getDate().get(Calendar.YEAR) +  "\n" +
                                    "End time: " + e.getEndTime() + "\n" +
                                    "Category: " + e.getTAG() + "\n" +
                                    "Location: " + e.getLocation() + "\n" +
                                    "Description: " + e.getDesc() + "\n\n";

                            Log.d("search", currentEvent);

                            eventsDisplay += currentEvent;

                        }
                    }


                }else{
                    try {
                        dateFirst = df.parse(dateFrom.getText().toString());
                    }catch(Exception e){
                        Log.d("error", "parse error");
                    }
                    //making second date equal to first date
                    dateSecond = dateFirst;

                    //Decrementing dateFirst,
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateFirst);
                    c.add(Calendar.DATE, -1);
                    dateFirst = c.getTime();

                    //incrementing dateSecond
                    c.setTime(dateSecond);
                    c.add(Calendar.DATE, 1);
                    dateSecond = c.getTime();

                    GregorianCalendar cal1 = new GregorianCalendar();
                    GregorianCalendar cal2 = new GregorianCalendar();
                    cal1.setTime(dateFirst);
                    cal2.setTime(dateSecond);

                    Log.d("display dates", cal1.toString());
                    Log.d("display dates", cal2.toString());

                    for(Event e: events){

                        String currentEvent = "";

                        if(e.getDate().compareTo(cal1) > -1 && e.getDate().compareTo(cal2) < 1 && (calendarCategory.getSelectedItem().toString().equals("All") || calendarCategory.getSelectedItem().toString().equals(e.getTAG()))){
                            currentEvent += "Title: " + e.getEventName() +"\n" +
                                    "Date: " + e.getDate().get(Calendar.DAY_OF_MONTH) + "/" + e.getDate().get(Calendar.MONTH) + "/" + e.getDate().get(Calendar.YEAR) +  "\n" +
                                    "End time: " + e.getEndTime() + "\n" +
                                    "Category: " + e.getTAG() + "\n" +
                                    "Location: " + e.getLocation() + "\n" +
                                    "Description: " + e.getDesc() + "\n\n";

                            Log.d("search", currentEvent);

                            eventsDisplay += currentEvent;
                        }
                    }
                }

                if(eventsDisplay.length() <= "Events: \n".length()){
                    eventsDisplay = "No events to display";
                }

                displayEvents.setText(eventsDisplay);
                displayEvents.setVisibility(View.VISIBLE);
                eventsTitle.setVisibility(View.VISIBLE);

                Toast.makeText(SearchEventActivity.this, "Events Requested", Toast.LENGTH_SHORT).show();
            }
        });

        rangeQuery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rangeQuery.isChecked()){
                    toDateLayout.setVisibility(View.VISIBLE);
                    rangeBoolean = true;
                    Log.d("Switch", "Changed to true");
                }else{
                    toDateLayout.setVisibility(View.INVISIBLE);
                    rangeBoolean = false;
                    Log.d("Switch", "Changed to false");
                }
            }
        });
    }

}
