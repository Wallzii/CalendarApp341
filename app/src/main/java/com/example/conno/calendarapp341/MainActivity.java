package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Creating branch 'dev'.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button butcalendar = findViewById(R.id.butcalendar);
        Button butSearch = findViewById(R.id.butSearch);

        butcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CalendarActivity.class);
                startActivity(intent);
            }
        });

        butSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchEventActivity.class);
                startActivity(intent);
            }
        });

    }

    public void buttonSMS(View view) {
        Intent intent = new Intent(MainActivity.this, InviteSMS.class);
        startActivity(intent);
    }
}