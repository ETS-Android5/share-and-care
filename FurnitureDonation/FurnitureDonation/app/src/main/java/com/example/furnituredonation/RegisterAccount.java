package com.example.furnituredonation;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterAccount extends AppCompatActivity {
    LoginDatabaseHelper db;
    EditText userName, password, password2;
    Button createAccButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        db = new LoginDatabaseHelper(this);


        userName = (EditText)findViewById(R.id.register_username);
        password = (EditText)findViewById(R.id.register_password);
        password2 = (EditText)findViewById(R.id.register_password2);
        createAccButton = (Button)findViewById(R.id.create_account);
        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_Name = userName.getText().toString();
                String password_1 = password.getText().toString();
                String password_2 = password2.getText().toString();

                if (user_Name.equals("")|| password_1.equals("")||password_2.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                } else {
                    if (password_1.equals(password_2)){
                        Boolean checkemail = db.checkEmail(user_Name);
                        if(checkemail==true){
                            Boolean insert = db.insert(user_Name,password_1);
                            if(insert==true){
                                Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();

                                //Intent i = new Intent(RegisterAccount.this,MainActivity.class);
                                //startActivity(i);
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "User Name already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                        //Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();


                }

            }
        });


    }
}
