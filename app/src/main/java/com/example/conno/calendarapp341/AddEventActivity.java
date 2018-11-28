package com.example.conno.calendarapp341;

import android.content.Context;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    private TextView dateText;
    TextView titleText, startText, endText, descText, locText, tagText;
    private BottomNavigationView bottom_Nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();
        String theDate = intent.getStringExtra("date");
        dateText = findViewById(R.id.dateText);
        dateText.setText(theDate);
        titleText = findViewById(R.id.titleText);
        startText = findViewById(R.id.startText);
        endText = findViewById(R.id.endText);
        descText = findViewById(R.id.descText);
        locText = findViewById(R.id.locationText);
        tagText = findViewById(R.id.tagText);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Event");


        Button confirmButton = findViewById(R.id.acceptButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO

                if(checkValidFields()) {
                    String event = retrieveEventText();

                    // Year, Month, dayOfMonth, StartTime,EndTime,Tag,Title,Description,Location
                    TextView test = findViewById(R.id.testt);
                    test.setText(event);
                    Data data = new Data(AddEventActivity.this);
                    //data.writeData();
                    try {
                        File file = new File("data.txt");
                        FileOutputStream outputStream;
                        outputStream = AddEventActivity.this.openFileOutput("data.txt", Context.MODE_APPEND);
                        outputStream.write(event.getBytes());
                        outputStream.close();
                    } catch (Exception e){
                        test.setText(e.toString());
                    }
                    Toast.makeText(getApplicationContext(),"Event added successfully.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
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
     * Checks that all required editText fields are not empty, prompting the user to complete the
     * required fields if they are not present.
     * @return boolean
     */
    public boolean checkValidFields() {
        if(titleText.getText().toString().equals("")||dateText.getText().toString().equals("")
                ||startText.getText().toString().equals("")||endText.getText().toString().equals("")||
                tagText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Date, title, tag, start, and end time fields are required. Please fill them out and try again.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(titleText.getText().toString().contains(",")||dateText.getText().toString().contains(",")||
                startText.getText().toString().contains(",")||endText.getText().toString().contains(",")||
                descText.getText().toString().contains(",")||locText.getText().toString().contains(",")||
                tagText.getText().toString().contains(",")){
            Toast.makeText(getApplicationContext(),"Fields may not contain commas. Please remove commas and try again.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!dateText.getText().toString().matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")){
            Toast.makeText(getApplicationContext(),"You have entered an invalid date field. Please use dd/mm/yyyy in 24 hour format.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!startText.getText().toString().matches("^(1[0-9]|2[0-3]|[0-9]):[0-5][0-9]")||!endText.getText().toString().matches("^(1[0-9]|2[0-3]|[0-9]):[0-5][0-9]")){
            Toast.makeText(getApplicationContext(),"You have entered an invalid time field. Please use hours:minutes in 24 hour format.",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Listener for button that passes event data to InviteSMS.class to send as an SMS reminder
     * to the specified recipient phone number.
     * @param view
     */
    public void buttonSendEventSMSReminder(View view) {
        if(checkValidFields()) {
            String[] attributes = retrieveEventText().split(",");
            Event event = new Event(attributes);
            Intent intent = new Intent(AddEventActivity.this, InviteSMS.class);
            Bundle bundle = new Bundle();
            bundle.putString("eventDate", event.getDateString());
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
    }

    /**
     * Pulls all event data from editText fields to be used for data entry and SMS reminder feature.
     * @return String
     */
    public String retrieveEventText() {
        StringBuilder builder = new StringBuilder();
        // Denote character used to separate indices in data file.
        String indexChar = ",";
        // Denote string to take place of empty and non-required data fields for writing to data file.
        String missingText = " ";
        builder.append(dateText.getText().toString().substring(1+dateText.getText().toString().lastIndexOf('/')));
        builder.append(indexChar);
        builder.append(dateText.getText().toString().substring(1+dateText.getText().toString().indexOf('/'), dateText.getText().toString().lastIndexOf('/')));
        builder.append(indexChar);
        builder.append(dateText.getText().toString().substring(0, dateText.getText().toString().indexOf('/')));
        builder.append(indexChar);
        builder.append(startText.getText().toString().substring(0,startText.getText().toString().indexOf(':')));
        builder.append(indexChar);
        builder.append(startText.getText().toString().substring(startText.getText().toString().indexOf(':')+1));
        builder.append(indexChar);
        builder.append(endText.getText().toString());
        builder.append(indexChar);
        builder.append(tagText.getText().toString());
        builder.append(indexChar);
        builder.append(titleText.getText().toString());
        builder.append(indexChar);
        // While descText isn't required, populate Event with whitespace to prevent SearchEventActivity failure.
        if(descText.getText().toString().equals(""))
            builder.append(missingText);
        else
            builder.append(descText.getText().toString());
        builder.append(indexChar);
        // While locText isn't required, populate Event with whitespace character to prevent SearchEventActivity failure.
        if(locText.getText().toString().equals(""))
            builder.append(missingText);
        else
            builder.append(locText.getText().toString());
        builder.append("\n");

        // Return contents of StringBuilder as String.
        return builder.toString();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent intent;
            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    intent = new Intent(AddEventActivity.this,MainMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    break;
                case R.id.nav_calender:
                    intent = new Intent(AddEventActivity.this,CalendarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(AddEventActivity.this,SearchEventActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };


}
