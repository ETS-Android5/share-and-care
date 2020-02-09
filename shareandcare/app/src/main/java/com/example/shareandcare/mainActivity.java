package com.example.shareandcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class mainActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView loginCreateButton;
    loginDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = new loginDatabaseHelper(this);
        loginUsername = (EditText)findViewById(R.id.login_Username);
        loginPassword = (EditText)findViewById(R.id.login_Password);
        loginButton = (Button)findViewById(R.id.login_Button);
        loginCreateButton = (TextView)findViewById(R.id.login_Create);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                Boolean checkAccount = db.checkAccount(username, password);
                if(checkAccount == true) {
                    Toast.makeText(getApplicationContext(), "Logged in...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(mainActivity.this,listings.class);
                    startActivity(i);
                } else if (loginUsername.getText().toString().equals("admin") && loginPassword.getText().toString().equals("admin")) {
                    Intent i = new Intent(mainActivity.this, admin.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Logged in...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mainActivity.this, registerAccount.class);
                startActivity(i);
            }
        });
    }
}
