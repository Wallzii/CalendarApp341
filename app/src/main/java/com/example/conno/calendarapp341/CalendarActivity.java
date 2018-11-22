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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import org.w3c.dom.Text;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Calendar");

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
                String sendDate = "" + selected.get(Calendar.MONTH)
                        + " " + selected.get(Calendar.DAY_OF_MONTH)
                        + " " + selected.get(Calendar.YEAR);
                intent.putExtra("date", sendDate);
                //TODO
                //startActivityForResult(intent, );
                startActivity(intent);
            }
        });
        bottom_Nav = findViewById(R.id.bottom_nav_calender);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
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
                    break;
                case R.id.nav_calender:
                    break;
            }
            return false;
        }
    };


}

