package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class ViewEventActivity extends AppCompatActivity {
    private BottomNavigationView bottom_Nav;

    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("View Event");

        String received=getIntent().getStringExtra("event");
        event = new Event(received.split(","));

        String dateDisplay = "" + new DateFormatSymbols().getMonths()[event.getDate().get(Calendar.MONTH)]
                + " " + event.getDate().get(Calendar.DAY_OF_MONTH)
                + " " + event.getDate().get(Calendar.YEAR);
        String strTime =event.getStartHour()+":"+event.getStartMin();

        TextView titleT = findViewById(R.id.titleText);
        TextView dateT = findViewById(R.id.dateText);
        TextView descT = findViewById(R.id.descText);
        TextView endT = findViewById(R.id.endText);
        TextView tagT = findViewById(R.id.tagText);
        TextView startT = findViewById(R.id.startText);
        TextView locT = findViewById(R.id.locationText);
        titleT.setText(event.getEventName());
        dateT.setText(dateDisplay);
        descT.setText(event.getDesc());
        endT.setText(event.getEndTime());
        tagT.setText(event.getTAG());
        startT.setText(strTime);
        locT.setText(event.getLocation());

        Button cancelButton = findViewById(R.id.backBut);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bottom_Nav = findViewById(R.id.bottom_nav_calender);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottom_Nav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

    }

    /**
     * Listener for button that passes event data to InviteSMS.class to send as an SMS reminder
     * to the specified recipient phone number.
     * @param view
     */
    public void buttonSendEventSMSReminder(View view) {

            Intent intent = new Intent(getApplicationContext(), InviteSMS.class);
            Bundle bundle = new Bundle();
            String dateDisplay = "" + new DateFormatSymbols().getMonths()[event.getDate().get(Calendar.MONTH)]
                + " " + event.getDate().get(Calendar.DAY_OF_MONTH)
                + " " + event.getDate().get(Calendar.YEAR);
            bundle.putString("eventDate", dateDisplay);
            bundle.putString("eventTitle", event.getEventName());
            bundle.putString("eventStartHour", event.getStartHour());
            bundle.putString("eventStartMin", event.getStartMin());
            bundle.putString("eventEndHour", event.getEndTime());
            bundle.putString("eventTag", event.getTAG());
            bundle.putString("eventDescription", event.getDesc());
            bundle.putString("eventLocation", event.getLocation());
            intent.putExtras(bundle);
            startActivity(intent);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent intent;
            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    intent = new Intent(ViewEventActivity.this,MainMenu.class);
                    startActivity(intent);
                    break;
                case R.id.nav_calender:
                    intent = new Intent(ViewEventActivity.this,CalendarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(ViewEventActivity.this,SearchEventActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };
}
