package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private BottomNavigationView bottom_Nav;
    private Intent intent;
    boolean rangeBoolean;



    ///////////////////////////////////////////////
    //      Stuff I wont need later
    Data d = new Data(SearchEventActivity.this);
    ////////////////////////////////////////////////
      

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

        bottom_Nav = findViewById(R.id.bottom_nav_search);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottom_Nav.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    intent = new Intent(SearchEventActivity.this,MainMenu.class);
                    startActivity(intent);
                    break;
                case R.id.nav_group:
                    intent = new Intent(SearchEventActivity.this,InviteSMS.class);
                    startActivity(intent);
                    break;
                case R.id.nav_calender:
                    intent = new Intent(SearchEventActivity.this, CalendarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    break;
            }
            return false;
        }
    };

    public void onClickListenter(){
        requestEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date dateFirst = null;
                Date dateSecond = null;

                ArrayList<Event> events = d.events;
                String eventsDisplay = "";

                //Things to keep track of where i am
                int countLoops = 0;
                int countEvents = 0;
                boolean enteredSearch = false;
                boolean addedToArray = false;

                //if the input matches the proper format
                if ((dateFrom.getText().toString().matches("\\d{2}\\/\\d{2}\\/\\d{4}") && dateTo.getText().toString().matches("\\d{2}\\/\\d{2}\\/\\d{4}")) && rangeBoolean) {
                    Log.d("Search", "Multiday Search");

                    //Converting input from EditText views
                    try {
                        dateFirst = df.parse(dateFrom.getText().toString()); //sending to correct date to subtract a month from the date
                        dateSecond = df.parse(dateTo.getText().toString()); //sending to correct date to subtract a month from the date
                    } catch (Exception e) {
                        Log.d("error", "parse error");
                    }

                    GregorianCalendar cal1 = new GregorianCalendar();
                    cal1.setTime(dateFirst);
                    GregorianCalendar cal2 = new GregorianCalendar();
                    cal2.setTime(dateSecond);

                    Log.d("display dates", cal1.toString());
                    Log.d("display dates", cal2.toString());

                    for (Event e : events) {
                        //Keeping track of of how many time it loops and if it has been entered

                        String currentEvent = "";

                        if (e.getDate().compareTo(cal1) > -1 && e.getDate().compareTo(cal2) < 1 && (calendarCategory.getSelectedItem().toString().equals("All") || calendarCategory.getSelectedItem().toString().equals(e.getTAG()))) {

                            if(!addedToArray) {
                                Log.d("Array", "Value entered to array");
                                addedToArray = true;
                            }
                            String min = "";
                            if(e.getStartMin().length()<2){
                                min = e.getStartMin() + "0";
                            }
                            currentEvent += "Title: " + e.getEventName() + "\n" +
                                    "Date: " + e.getDate().get(Calendar.DAY_OF_MONTH) + "/" + (e.getDate().get(Calendar.MONTH) + 1) + "/" + e.getDate().get(Calendar.YEAR) + "\n" +
                                    "Start time: " + e.getStartHour() + ":"  + min + "\n" +
                                    "End time: " + e.getEndTime() + "\n" +
                                    "Category: " + e.getTAG() + "\n" +
                                    "Location: " + e.getLocation() + "\n" +
                                    "Description: " + e.getDesc() + "\n\n";

                        }

                        eventsDisplay += currentEvent;

                        Log.d("test", currentEvent);
                        countLoops ++;
                        if(!enteredSearch) {
                            Log.d("Search", "Search has been entered");
                            enteredSearch = true;
                        }

                    }

                    Log.d("Search", "looped through " + countLoops + " items");
                    Log.d("Search", "Added " + countEvents + " events");

                    if (eventsDisplay.length() <= ("Events: \n").length()) {
                        eventsDisplay = "No events to display";
                    }

                    displayEvents.setText(eventsDisplay);
                    displayEvents.setVisibility(View.VISIBLE);
                    eventsTitle.setVisibility(View.VISIBLE);

                } else if (dateFrom.getText().toString().matches("\\d{2}\\/\\d{2}\\/\\d{4}")  && !rangeBoolean) {

                    Log.d("Search", "Single Day Search");

                    //Converting input from EditText views

                    try {
                        dateFirst = df.parse(dateFrom.getText().toString()); //sending to correct date to subtract a month from the date
                    } catch (Exception e) {
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
                    cal1.setTime(dateFirst);
                    GregorianCalendar cal2 = new GregorianCalendar();
                    cal2.setTime(dateSecond);

                    Log.d("display dates", cal1.toString());
                    Log.d("display dates", cal2.toString());

                    for (Event e : events) {
                        countLoops++;
                        if(!enteredSearch) {
                            Log.d("Search", "Search has been entered");
                            enteredSearch = true;
                        }

                        String currentEvent = "";

                        if (e.getDate().compareTo(cal1) > -1 && e.getDate().compareTo(cal2) < 1 && (calendarCategory.getSelectedItem().toString().equals("All") || calendarCategory.getSelectedItem().toString().equals(e.getTAG()))) {

                            countEvents ++;

                            currentEvent += "Title: " + e.getEventName() + "\n" +
                                    "Date: " + e.getDate().get(Calendar.DAY_OF_MONTH) + "/" + (e.getDate().get(Calendar.MONTH) + 1) + "/" + e.getDate().get(Calendar.YEAR) + "\n" +
                                    "End time: " + e.getEndTime() + "\n" +
                                    "Category: " + e.getTAG() + "\n" +
                                    "Location: " + e.getLocation() + "\n" +
                                    "Description: " + e.getDesc() + "\n\n";

                            eventsDisplay += currentEvent;
                        }
                    }

                    Log.d("Search", "looped through " + countLoops + " items");
                    Log.d("Search", "Added " + countEvents + " events");

                    if (eventsDisplay.length() <= "Events: \n".length()) {
                        eventsDisplay = "No events to display";
                    }

                    displayEvents.setText(eventsDisplay);
                    displayEvents.setVisibility(View.VISIBLE);
                    eventsTitle.setVisibility(View.VISIBLE);

                    Toast.makeText(SearchEventActivity.this, "Events Requested", Toast.LENGTH_SHORT).show();
                } else { // if the input does not match the format let them know
                    Toast.makeText(SearchEventActivity.this, "Please use the suggested format", Toast.LENGTH_SHORT).show();
                }
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

    public String getCorrectDate(String currentMonth){

        String [] splitDate = currentMonth.split("/");
        int month = Integer.parseInt(splitDate[1]);
        month --;

        String stringMonth = "";

        if(month<10){
            stringMonth += "0" + Integer.toString(month);
        }else{
            stringMonth += Integer.toString(month);
        }

        String correctedDate = splitDate[0] + "/" + stringMonth + "/" + splitDate[2]; //fromDateCorrected
        Log.d("Conversion", correctedDate);

        return correctedDate;


    }
    public String increaseMonth (String month){

        int rightMonth = Integer.parseInt(month) + 1;

        return Integer.toString(rightMonth);
    }



}
