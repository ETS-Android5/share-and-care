package com.example.shareandcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class confirmed extends Activity {
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmed);

        returnButton = (Button)findViewById(R.id.confirmed_Return_Button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(confirmed.this, listings.class);
                startActivity(i);
            }
        });
    }
}
