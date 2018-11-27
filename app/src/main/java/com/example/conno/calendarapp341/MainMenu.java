package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    private Intent intent;
    private Intent intent2;
    private BottomNavigationView bottom_Nav;
    ArrayList<Entry> yvalues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bottom_Nav = findViewById(R.id.bottom_nav);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        PieChart pieChart = (PieChart) findViewById(R.id.pie);
        pieChart.setUsePercentValues(true);

        yvalues = new ArrayList<>();

        yvalues.add(new Entry(8f, 0));
        yvalues.add(new Entry(15f, 1));
        yvalues.add(new Entry(12f, 2));
        yvalues.add(new Entry(25f, 3));
        yvalues.add(new Entry(23f, 4));
        yvalues.add(new Entry(17f, 5));

        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");

        PieData data = new PieData(xVals, dataSet);

        data.setValueFormatter(new PercentFormatter());

        pieChart.setData(data);


        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    break;
                case R.id.nav_group:
                    intent = new Intent(MainMenu.this,InviteSMS.class);
                    startActivity(intent);
                    break;
                case R.id.nav_calender:
                    intent = new Intent(MainMenu.this,CalendarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent2 = new Intent(MainMenu.this,SearchEventActivity.class);
                    startActivity(intent2);
                    break;
            }
            return false;
        }
    };
}
