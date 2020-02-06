package com.example.furnituredonation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DonatingOrFinding extends Activity {

    private Button findingFurniture;
    private Button donatingFurniture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donating_finding);

        findingFurniture = (Button) findViewById(R.id.finding_furniture);
        donatingFurniture = (Button) findViewById(R.id.donating_furniture);

        findingFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(DonatingOrFinding.this, FurnitureList.class);
                startActivity(intent);
            }
        });

        donatingFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(DonatingOrFinding.this, FurnitureDetails.class);
                startActivity(intent);
            }
        });

    }
}
