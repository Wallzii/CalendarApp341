package com.example.conno.calendarapp341;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private Data data;
    private ArrayList<Event> selectedData;

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
                //TODO show events in ListView for given day
                fillEventList();

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
                startActivity(intent);
            }
        });
        bottom_Nav = findViewById(R.id.bottom_nav_calender);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottom_Nav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);


        //TODO ListView init
        data = new Data(CalendarActivity.this);
        //data.writeData();
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
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    intent = new Intent(CalendarActivity.this,MainMenu.class);
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

        getEventData();
        selectedData = new ArrayList<>();
        for(Event event : eventData){
            if(selected.get(Calendar.YEAR)==event.getDate().get(Calendar.YEAR)&&
                    selected.get(Calendar.MONTH)==event.getDate().get(Calendar.YEAR)&&
                    selected.get(Calendar.DAY_OF_MONTH)==event.getDate().get(Calendar.DAY_OF_MONTH)){
                selectedData.add(event);
            }
        }
        //TODO Sort selectedData by start time
        EventAdapter adapter = new EventAdapter(this, selectedData);
        eventList.setAdapter(adapter);
        for(int i = 0 ; i < selectedData.size() ; i++ ){
            adapter.getView(i,null, eventList);
        }
        //final ArrayAdapter adapter = new ArrayAdapter(this,
        //        android.R.layout.simple_list_item_1, eventData);
        //eventList.setAdapter(adapter);


    }
    private void getEventData(){
        //TODO
        data.loadEvents();
        eventData = data.events;
    }

}
class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }
        // Lookup view for data population
        TextView eTitle = (TextView) convertView.findViewById(R.id.list_event_name);
        TextView eTimes = (TextView) convertView.findViewById(R.id.list_event_times);
        // Populate the data into the template view using the data object
        eTitle.setText(event.getEventName());
        String times = event.getDate().getTime().toString() + " - " + event.getEndTime();
        eTimes.setText(times);
        // Return the completed view to render on screen
        return convertView;
    }
}

