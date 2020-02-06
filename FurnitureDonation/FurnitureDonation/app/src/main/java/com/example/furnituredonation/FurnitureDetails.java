package com.example.furnituredonation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class FurnitureDetails extends Activity {
    private EditText furnitureDetails;
    private EditText furnitureMeasurement;
    private RadioGroup furnitureType;
    private Button buttonNext;
    private FurnitureHelper helper = null;
    public String furnitureID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donating_furniture);

        furnitureDetails = (EditText) findViewById(R.id.furniture_details);
        furnitureMeasurement = (EditText) findViewById(R.id.furniture_measurement);
        furnitureType = (RadioGroup) findViewById(R.id.furniture_type);

        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(onSave);

        helper = new FurnitureHelper(this);

        furnitureID = getIntent().getStringExtra("ID");
        if (furnitureID != null){
            load();
        }



    }

    private void load() {
        Cursor c = helper.getById(furnitureID);
        c.moveToFirst();
        furnitureDetails.setText(helper.getfurnitureDetails(c));
        furnitureMeasurement.setText(helper.getfurnitureMeasurement(c));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String idStr = furnitureID;
            String detailsStr = furnitureDetails.getText().toString();
            String measurementStr = furnitureMeasurement.getText().toString();
            String furnitype = "";
            switch (furnitureType.getCheckedRadioButtonId()){
                case R.id.sofas:
                    furnitype ="Sofa";
                    break;
                case R.id.table:
                    furnitype ="Table";
                    break;
                case R.id.chair:
                    furnitype ="Chair";
                    break;
                case R.id.bed:
                    furnitype ="Bed";
                    break;
                case R.id.storage:
                    furnitype ="Storage";
                    break;

            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FurnitureDetails.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("detailsStr", detailsStr);
            editor.putString("measurementStr", measurementStr);
            editor.putString("furnitype", furnitype);
            editor.apply();



            Intent intent;
            intent = new Intent(FurnitureDetails.this, ChoosePic.class);
            startActivity(intent);

            finish();



        }

    };
}
