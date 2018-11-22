package com.example.conno.calendarapp341;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InviteSMS extends AppCompatActivity {

    private BottomNavigationView bottom_Nav;
    private Intent intent;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_sms);

        data = new Data(this);
        data.writeData();
        data.loadEvents();

        // Check appropriate permissions are enabled.
        checkPermissions();

        bottom_Nav = findViewById(R.id.bottom_nav_group);
        bottom_Nav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottom_Nav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_piechart:
                    intent = new Intent(InviteSMS.this,MainMenu.class);
                    startActivity(intent);
                    break;
                case R.id.nav_group:
                    break;
                case R.id.nav_calender:
                    intent = new Intent(InviteSMS.this,CalendarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    break;
            }
            return false;
        }
    };

    public void sendSMS(String phoneNumber, String textData) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textData, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "You must provide a recipient phone number.", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void buttonSensSMS(View view) {
        checkPermissions();
        EditText phoneNumberET = (EditText)findViewById(R.id.editText_recipientPhoneNumber);
        EditText textDataET = (EditText)findViewById(R.id.editText_testData);
        String phoneNumber = phoneNumberET.getText().toString();
        String textData = textDataET.getText().toString();

//        sendSMS(phoneNumber, textData);
        sendSMS(phoneNumber, data.events.get(0).toString());
//        sendSMS("12503170864", "TESTING!");
    }

    public void checkPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
