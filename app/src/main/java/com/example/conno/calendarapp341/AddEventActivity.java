package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
