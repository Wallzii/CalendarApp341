package com.example.conno.calendarapp341;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    private Intent intent;
    private Intent intent2;
    private BottomNavigationView bottom_Nav;
    ArrayList<Event> events;
    Data data = new Data(MainMenu.this);
    float work = 0f;
    float school = 0f;
    float personal = 0f;
    float family = 0f;
    ArrayList<Entry> yvalues = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Schedule Breakdown");
        bottom_Nav = findViewById(R.id.bottom_nav);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        PieChart pieChart = (PieChart) findViewById(R.id.pie);
        pieChart.setUsePercentValues(true);
        data.loadEvents();
        events = data.events;

        for (Event e : events) {
            if (e.getTAG().toLowerCase().equals("work"))
                work++;
            else if (e.getTAG().toLowerCase().equals("school"))
                school++;
            else if (e.getTAG().toLowerCase().equals("personal"))
                personal++;
            else if (e.getTAG().toLowerCase().equals("family"))
                family++;
        }

        yvalues.add(new Entry(work, 0));
        yvalues.add(new Entry(school, 1));
        yvalues.add(new Entry(personal, 2));
        yvalues.add(new Entry(family, 3));

        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Work");
        xVals.add("School");
        xVals.add("Personal");
        xVals.add("Family");

        PieData data = new PieData(xVals, dataSet);

        data.setValueFormatter(new PercentFormatter());

        pieChart.setData(data);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.setRotationEnabled(false);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    break;
                case R.id.nav_calender:
                    intent = new Intent(MainMenu.this, CalendarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(MainMenu.this, SearchEventActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };
}
