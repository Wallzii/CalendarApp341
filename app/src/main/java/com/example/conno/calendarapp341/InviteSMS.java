package com.example.conno.calendarapp341;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InviteSMS extends AppCompatActivity {


    Intent intent;
    //    private Data data;
    TextView displayEvents;
    private String date, title, startHour, startMin, endHour, tag, location, description;
    private BottomNavigationView bottom_Nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_sms);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("SMS Event Reminder");
        displayEvents = findViewById(R.id.textView_eventSMSDetails);

        // Pull event data from previous activity.
        pullEventData();

        // Output event data to textView.
        displayEvents.setText(buildEventData());

        // Used if the activity is to pull data from the on-device textfile containing events. Comment
        // out if this is not a feature to be used.
//        data = new Data(this);
//        data.writeData();
//        data.loadEvents();

        // Check appropriate permissions are enabled.
        checkPermissions();

        // Display bottom navigation bar.
        bottom_Nav = findViewById(R.id.bottom_nav_group);
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
                    intent = new Intent(InviteSMS.this, MainMenu.class);
                    startActivity(intent);
                    break;
                case R.id.nav_calender:
                    intent = new Intent(InviteSMS.this, CalendarActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_search:
                    intent = new Intent(InviteSMS.this, SearchEventActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

    /**
     * Formats event data into a string that can be delivered to a recipient via SMS.
     *
     * @return String
     */
    private String buildEventData() {
        return "Date: " + date + "\n" +
                "Title: " + title + "\n" +
                "Start Time: " + startHour + ":" + startMin + "\n" +
                "End Time: " + endHour + "\n" +
                "Category: " + tag + "\n" +
                "Description: " + description + "\n" +
                "Location: " + location;

    }

    /**
     * Pulls event data from passed package and stores into local variables.
     */
    public void pullEventData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        try {
            date = bundle.getString("eventDate");
            title = bundle.getString("eventTitle");
            startHour = bundle.getString("eventStartHour");
            startMin = bundle.getString("eventStartMin");
            endHour = bundle.getString("eventEndHour");
            tag = bundle.getString("eventTag");
            description = bundle.getString("eventDescription");
            location = bundle.getString("eventLocation");
        } catch (Exception e) {
            Log.d("error", "parse error");
        }
    }


    /**
     * Sends an SMS containing formatted event data. A phone number must be entered, but will not
     * be checked for its validity.
     *
     * @param phoneNumber Phone number for intended recipient.
     * @param textData    String to be delivered to inputted phone number.
     */
    public void sendSMS(String phoneNumber, String textData) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textData, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "You must provide a recipient phone number.", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    /**
     * Listener for sens SMS button.
     *
     * @param view Current view.
     */
    public void buttonSendSMS(View view) {
        checkPermissions();
        EditText phoneNumberET = findViewById(R.id.editText_recipientPhoneNumber);
        TextView importTextData = findViewById(R.id.textView_eventSMSDetails);

        String phoneNumber = phoneNumberET.getText().toString();
        String textData = importTextData.getText().toString();

        sendSMS(phoneNumber, "Event Reminder from Calendar341:\n" + textData);
    }

    /**
     * Check to see if the required permissions are enabled for intended functionality to work (sending an SMS).
     */
    public void checkPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.SEND_SMS
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    /**
     * Make sure the user's device has the appropriate permissions for the application send an SMS.
     * Also make sure that the user's device allows the application to check if specific permissions
     * are met.
     *
     * @param context     Current context.
     * @param permissions Permissions that need to be checked (SMS).
     * @return boolean
     */
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