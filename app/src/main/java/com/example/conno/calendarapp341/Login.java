package com.example.conno.calendarapp341;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView signUp = (TextView) findViewById(R.id.textView_signUp);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        TextView signUp = (TextView) findViewById(R.id.textView_signUp);

        MakeLinks.makeLinks(signUp, new String[] {
                "Sign up!"
        }, new ClickableSpan[] {
                normalLinkClickSpan
        });
    }

    //    signUp.setLinkTextColor(Color.BLUE); // default link color for clickable span, we can also set it in xml by android:textColorLink=""
    ClickableSpan normalLinkClickSpan = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Normal Link", Toast.LENGTH_SHORT).show();
        }

    };

    public void buttonLogin(View view) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }
}
