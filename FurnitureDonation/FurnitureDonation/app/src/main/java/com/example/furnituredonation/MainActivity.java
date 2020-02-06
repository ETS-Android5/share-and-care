package com.example.furnituredonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton, registerButton;
    LoginDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new LoginDatabaseHelper(this);
        loginUsername = (EditText)findViewById(R.id.Login_User_name);
        loginPassword = (EditText)findViewById(R.id.Login_Password);
        loginButton = (Button)findViewById(R.id.Login_button);
        registerButton = (Button)findViewById(R.id.CreateAcc_Button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = loginUsername.getText().toString();
                String Password = loginPassword.getText().toString();
                Boolean Checkusernamepass = db.usernamepassword(Username,Password);
                if(Checkusernamepass == true){
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this,DonatingOrFinding.class);
                startActivity(i);
                } else if (loginUsername.getText().toString().equals("admin")&& loginPassword.getText().toString().equals("admin")){

                    Intent i = new Intent(MainActivity.this,Admin.class);
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), "Login as admin", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(MainActivity.this, RegisterAccount.class);
                startActivity(intent);
            }
        });

    }
}
