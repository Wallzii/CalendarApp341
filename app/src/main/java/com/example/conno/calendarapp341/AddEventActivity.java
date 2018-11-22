package com.example.conno.calendarapp341;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Event");


        Button confirmButton = findViewById(R.id.acceptButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                TextView titleText = findViewById(R.id.titleText);
                TextView dateText = findViewById(R.id.dateText);
                TextView startText = findViewById(R.id.startText);
                TextView endText = findViewById(R.id.endText);
                TextView descText = findViewById(R.id.descText);
                TextView locText = findViewById(R.id.locationText);
                TextView tagText = findViewById(R.id.tagText);

                if(titleText.getText().toString().equals("")||dateText.getText().toString().equals("")
                        ||startText.getText().toString().equals("")||endText.getText().toString().equals("")||
                        tagText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Date, title, tag and start and end time fields are required. Please fill them out and try again.",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(titleText.getText().toString().contains(",")||dateText.getText().toString().contains(",")||
                        startText.getText().toString().contains(",")||endText.getText().toString().contains(",")||
                        descText.getText().toString().contains(",")||locText.getText().toString().contains(",")||
                        tagText.getText().toString().contains(",")){
                    Toast.makeText(getApplicationContext(),"Fields may not contain commas. Please remove commas and try again.",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!dateText.getText().toString().matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")){
                    Toast.makeText(getApplicationContext(),"You have entered an invalid date field. Please use dd/mm/yyyy in 24 hour format.",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!startText.getText().toString().matches("^(1[0-9]|2[0-3]|[0-9]):[0-5][0-9]")||!endText.getText().toString().matches("^(1[0-9]|2[0-3]|[0-9]):[0-5][0-9]")){
                    Toast.makeText(getApplicationContext(),"You have entered an invalid time field. Please use hours:minutes in 24 hour format.",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    String event;
                    event = dateText.getText().toString().substring(1+dateText.getText().toString().lastIndexOf('/'))+ ", "+
                            dateText.getText().toString().substring(1+dateText.getText().toString().indexOf('/'), dateText.getText().toString().lastIndexOf('/'))+", "+
                            dateText.getText().toString().substring(0, dateText.getText().toString().indexOf('/'))+", "+
                            startText.getText().toString()+", "+endText.getText().toString()+", "+tagText.getText().toString()+", "+
                            titleText.getText().toString()+", "+descText.getText().toString()+", "+locText.getText().toString()+"\n";
                    // Year, Month, dayOfMonth, StartTime,EndTime,Tag,Title,Description,Location
                    //TODO testing
                    TextView test = findViewById(R.id.testt);
                    test.setText(event);
                    //TODO implement adding to file.
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
                    //finishActivity(22);
                }
                //Intent intent = new Intent(CalendarActivity.this,AddEventActivity.class);
                //String sendDate = "" + selected.get(Calendar.MONTH)
                //        + " " + selected.get(Calendar.DAY_OF_MONTH)
                //        + " " + selected.get(Calendar.YEAR);
                //intent.putExtra("date", sendDate);
                //startActivity(intent);
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                goBack();

                // Intent intent = new Intent(CalendarActivity.this,AddEventActivity.class);
                //String sendDate = "" + selected.get(Calendar.MONTH)
                //        + " " + selected.get(Calendar.DAY_OF_MONTH)
                //        + " " + selected.get(Calendar.YEAR);
                //intent.putExtra("date", sendDate);
                //startActivity(intent);
            }
        });


    }
    @Override
    public void onBackPressed() {
        //TODO
        //Intent mIntent = getIntent();
        //mIntent.putExtra("playState", isPlaying);
        //mIntent.putExtra("progress", seekbarProgress);
        //setResult(RESULT_OK, mIntent);
        finish();
    }
    public void goBack(){

    }


}
