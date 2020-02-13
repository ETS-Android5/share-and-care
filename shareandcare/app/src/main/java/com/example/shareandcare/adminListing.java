package com.example.shareandcare;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class adminListing extends Activity {

    private TextView adminTitle, adminTag, adminDescription, adminDimensionX, adminDimensionY, adminDimensionZ;
    private Button adminConfirm;
    private ImageView adminImage;
    private String adminTitleStr;

    private listingHelper helper = new listingHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlisting);

        adminTitle = (TextView)findViewById(R.id.admin_Title);
        adminTag = (TextView)findViewById(R.id.admin_Tag);
        adminDescription = (TextView)findViewById(R.id.admin_Description);
        adminDimensionX = (TextView)findViewById(R.id.admin_Dimension_X);
        adminDimensionY = (TextView)findViewById(R.id.admin_Dimension_Y);
        adminDimensionZ = (TextView)findViewById(R.id.admin_Dimension_Z);
        adminImage = (ImageView)findViewById(R.id.admin_Image);
        adminConfirm = (Button)findViewById(R.id.admin_Confirm_Button);

        adminConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.delete(getIntent().getStringExtra("id"));
                Toast.makeText(getApplicationContext(), "Delivery Confirmed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        adminTitleStr = getIntent().getStringExtra("title");
        if (adminTitleStr != null){
            load();
        }
    }

    private void load() {
        Cursor c = helper.getByTitle(adminTitleStr);
        c.moveToFirst();
        adminTitle.setText(helper.getListingTitle(c));
        adminTag.setText(helper.getListingTag(c));
        adminDescription.setText(helper.getListingDesciption(c));
        adminDimensionX.setText(helper.getListingDimensionX(c));
        adminDimensionY.setText(helper.getListingDimensionY(c));
        adminDimensionZ.setText(helper.getListingDimensionZ(c));
        //listingImage.setImageURI(Uri.parse(helper.getListingImage(c)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
