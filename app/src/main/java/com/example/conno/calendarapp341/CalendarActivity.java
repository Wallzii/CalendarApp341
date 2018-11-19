package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private  static final String TAG = "CalendarActivity";
    private  GregorianCalendar selected;
    private TextView dateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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


    }
}
