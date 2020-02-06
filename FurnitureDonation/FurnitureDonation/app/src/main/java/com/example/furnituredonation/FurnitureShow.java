package com.example.furnituredonation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FurnitureShow extends Activity {
    private TextView showFurnitureDetails;
    private TextView showFurnitureMeasurement;
    private TextView showFurnitureType;
    private Button buttonAR;
    private Button buttonSelect;
    private FurnitureHelper helper = null;
    private String furnitureID = "";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.furniture_show);

        showFurnitureDetails = (TextView) findViewById(R.id.show_furniture_details);
        showFurnitureMeasurement = (TextView) findViewById(R.id.show_furniture_measurement);
        showFurnitureType = (TextView)findViewById(R.id.show_furniture_type);
        imageView = (ImageView) findViewById(R.id.show_furniture_image);
        buttonAR = (Button) findViewById(R.id.AR_preview);
        buttonSelect = (Button) findViewById(R.id.select_furniture);
        buttonAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(FurnitureShow.this, AR.class);
                startActivity(intent);
            }
        });

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(FurnitureShow.this, Selected.class);
                startActivity(intent);
            }
        });


        helper = new FurnitureHelper(this);

        furnitureID = getIntent().getStringExtra("ID");
        if (furnitureID != null){
            load();
        }



    }

    private void load() {
        Cursor c = helper.getById(furnitureID);
        c.moveToFirst();
        showFurnitureDetails.setText(helper.getfurnitureDetails(c));
        showFurnitureMeasurement.setText(helper.getfurnitureMeasurement(c));
        showFurnitureType.setText(helper.getfurnitureType(c));
        imageView.setImageURI(Uri.parse(helper.getfurnitureImage(c)));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }




}
