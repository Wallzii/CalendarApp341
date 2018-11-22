package com.example.conno.calendarapp341;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InviteSMS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_sms);
    }

    public void sendSMS(String phoneNumber, String textData) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textData, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void buttonSensSMS(View view) {
//        EditText phoneNumber = (EditText)findViewById(R.id.editText_recipientPhoneNumber);
//        EditText textData = (EditText)findViewById(R.id.editText_testData);
        String phoneNumber = findViewById(R.id.editText_recipientPhoneNumber).toString();
        String textData = findViewById(R.id.editText_recipientPhoneNumber).toString();
        System.out.println("*********** phoneNumber: " + phoneNumber);
        System.out.println("*********** textData: " + textData);

        sendSMS(phoneNumber, textData);
    }
}
