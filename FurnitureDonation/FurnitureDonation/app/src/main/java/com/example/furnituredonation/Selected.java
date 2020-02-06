package com.example.furnituredonation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Selected extends Activity {

    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected);

        buttonReturn = (Button) findViewById(R.id.returnButton);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(Selected.this, FurnitureList.class);
                startActivity(intent);
            }
        });

    }

}
