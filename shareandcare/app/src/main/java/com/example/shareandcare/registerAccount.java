package com.example.shareandcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registerAccount extends AppCompatActivity {
    EditText registerUsername, registerPassword, registerPasswordConfirm, registerEmail, registerContactNumber;
    Button registerButton;
    loginDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        db = new loginDatabaseHelper(this);

        registerUsername = (EditText)findViewById(R.id.register_Username);
        registerPassword = (EditText)findViewById(R.id.register_Password);
        registerPasswordConfirm = (EditText)findViewById(R.id.register_Password_Confirm);
        registerEmail = (EditText)findViewById(R.id.register_Email);
        registerContactNumber = (EditText)findViewById(R.id.register_Contact);
        registerButton = (Button)findViewById(R.id.register_Button);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                String passwordConfirm = registerPasswordConfirm.getText().toString();
                String email = registerEmail.getText().toString();
                String contactNumber = registerContactNumber.getText().toString();

                if(username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || email.isEmpty() || contactNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (password.equals(passwordConfirm)){
                    Boolean checkEmail = db.checkUsername(username);
                    if(checkEmail == true) {
                        Boolean insert = db.addAccount(username, password, email, contactNumber);
                        if(insert == true){
                            Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(registerAccount.this, mainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error creating account", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}
