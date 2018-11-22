package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenu extends AppCompatActivity {
    private Intent intent;
    private Intent intent2;
    private BottomNavigationView bottom_Nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bottom_Nav = findViewById(R.id.bottom_nav);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    break;
                case R.id.nav_group:
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
