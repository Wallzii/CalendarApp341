package com.example.conno.calendarapp341;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {

    public static final String SELECTED_EVENTS = "SELECTED_EVENTS";

    private CalendarView mCalendarView;
    private GregorianCalendar selected;
    private TextView dateText;
    private FloatingActionButton addEventButton;
    private BottomNavigationView bottom_Nav;
    private Intent intent;
    private ListView eventList;
    private ArrayList<Event> eventData;
    private Data data;
    private static EventAdapter adapter;
    private Event[] todayEvents;

    //@Override
    protected void onResume() {
        super.onResume();
        data = new Data(CalendarActivity.this);
        data.loadEvents();
        eventData = data.events;
        showEventsForDay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        selected = new GregorianCalendar();
        data = new Data(CalendarActivity.this);
        data.writeData();
        data.loadEvents();
        eventData = data.events;
        eventList = findViewById(R.id.eventList);
        showEventsForDay();

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Click ListItem Number " + position, Toast.LENGTH_LONG).show();
                Intent vintent = new Intent(getApplicationContext(), ViewEventActivity.class);
                Event sendingEvent = todayEvents[position];
                // Year, Month, dayOfMonth, StartTime,EndTime,Tag,Title,Description,Location
                String sending = sendingEvent.getDateString() + "," + sendingEvent.getStartHour() + "," + sendingEvent.getStartMin() +
                        "," + sendingEvent.getEndTime() + "," + sendingEvent.getTAG() + "," + sendingEvent.getEventName()
                        + "," + sendingEvent.getDesc() + "," + sendingEvent.getLocation();
                vintent.putExtra("event", sending);
                startActivity(vintent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Calendar");

        dateText = findViewById(R.id.dateText);
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
                showEventsForDay();

            }
        });

        addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, AddEventActivity.class);
                String sendDate = "" + selected.get(Calendar.DAY_OF_MONTH)
                        + "/" + (selected.get(Calendar.MONTH) + 1)
                        + "/" + selected.get(Calendar.YEAR);
                intent.putExtra("date", sendDate);
                startActivity(intent);
            }
        });

        bottom_Nav = findViewById(R.id.bottom_nav_calender);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottom_Nav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    intent = new Intent(CalendarActivity.this, MainMenu.class);
                    startActivity(intent);
                    break;
                case R.id.nav_calender:
                    break;
                case R.id.nav_search:
                    intent = new Intent(CalendarActivity.this, SearchEventActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

    public void showEventsForDay() {
        todayEvents = getTodayEvents();
        adapter = new EventAdapter(this, todayEvents);
        eventList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    public Event[] getTodayEvents() {
        //TODO SORT EVENTS AND MAKE THEM CLICKABLE
        ArrayList<Event> today = new ArrayList<>();
        for (Event e : eventData) {

            if ((selected.get(Calendar.YEAR) == e.getDate().get(Calendar.YEAR)) &&
                    (selected.get(Calendar.MONTH) == e.getDate().get(Calendar.MONTH)) &&
                    (selected.get(Calendar.DAY_OF_MONTH) == e.getDate().get(Calendar.DAY_OF_MONTH))) {
                today.add(e);
            }
        }
        Event[] todayArr = new Event[today.size()];
        todayArr = today.toArray(todayArr);
        return todayArr;
    }
}

