package com.example.conno.calendarapp341;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView signUp, policyAgreements;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.textView_signUp);
        policyAgreements = findViewById(R.id.textView_policies);

        // Populate application with default dataset, if it doesn't exist. For testing purposes.
        data = new Data(this);
        data.writeData();

        MakeLinks.makeLinks(signUp, new String[]{
                "Sign up!"
        }, new ClickableSpan[]{
                signUpLink
        });
        MakeLinks.makeLinks(policyAgreements, new String[]{
                "privacy policy"
        }, new ClickableSpan[]{
                privacyPolicyLink
        });
        MakeLinks.makeLinks(policyAgreements, new String[]{
                "user agreement"
        }, new ClickableSpan[]{
                userAgreementLink
        });
    }

    ClickableSpan signUpLink = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Account sign-up placeholder.", Toast.LENGTH_SHORT).show();
        }

    };
    ClickableSpan privacyPolicyLink = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Application privacy policy placeholder.", Toast.LENGTH_SHORT).show();
        }

    };
    ClickableSpan userAgreementLink = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Application user agreement placeholder.", Toast.LENGTH_SHORT).show();
        }

    };

    public void buttonLogin(View view) {
        Intent intent = new Intent(Login.this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
