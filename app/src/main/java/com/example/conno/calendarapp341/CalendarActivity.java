package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView mCalendarView;
    private static final String TAG = "CalendarActivity";
    private GregorianCalendar selected;
    private TextView dateText;
    private FloatingActionButton addEventButton;
    private BottomNavigationView bottom_Nav;
    private Intent intent;
    private ListView eventList;
    private ArrayList<Event> eventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Calendar");

        //TODO ListView init
        eventList = findViewById(R.id.eventList);
        fillEventList();
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

        dateText = findViewById(R.id.dateText);
        selected = new GregorianCalendar();
        String dateDisplay = "" + new DateFormatSymbols().getMonths()[selected.get(Calendar.MONTH)]
                + " " + selected.get(Calendar.DAY_OF_MONTH)
                + " " + selected.get(Calendar.YEAR);
        dateText.setText(dateDisplay);

        mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                selected.set(year, month, dayOfMonth);
                String dateDisplay = "" + new DateFormatSymbols().getMonths()[selected.get(Calendar.MONTH)]
                        + " " + selected.get(Calendar.DAY_OF_MONTH)
                        + " " + selected.get(Calendar.YEAR);
                dateText.setText(dateDisplay);
                //TODO show events in ListView for given day
                //Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
                //Intent intent = new Intent(CalendarActivity.this,MainActivity.class);
                //intent.putExtra("date",date);
                //startActivity(intent);
            }
        });

        addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this,AddEventActivity.class);
                String sendDate = "" + selected.get(Calendar.DAY_OF_MONTH)
                        + "/" + (selected.get(Calendar.MONTH)+1)
                        + "/" + selected.get(Calendar.YEAR);
                intent.putExtra("date", sendDate);
                //TODO
                //startActivityForResult(intent, );
                startActivity(intent);
            }
        });
        bottom_Nav = findViewById(R.id.bottom_nav_calender);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottom_Nav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    intent = new Intent(CalendarActivity.this,MainMenu.class);
                    startActivity(intent);
                    break;
                case R.id.nav_group:
                    intent = new Intent(CalendarActivity.this,InviteSMS.class);
                    startActivity(intent);
                    break;
                case R.id.nav_calender:
                    break;
                case R.id.nav_search:
                    intent = new Intent(CalendarActivity.this,SearchEventActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };
    private void fillEventList(){
        //TODO Fill event list with clickable entries
        //final ArrayAdapter adapter = new ArrayAdapter(this,
        //        android.R.layout.simple_list_item_1, eventData);
        //eventList.setAdapter(adapter);


    }
    private void getEventList(){
        //TODO
    }

}

