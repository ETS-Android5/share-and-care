package com.example.shareandcare;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class listing extends Activity {
    private TextView listingTitle, listingTag, listingDescription, listingDimensionX, listingDimensionY, listingDimensionZ;
    private Button  listingArButton, listingConfirmButton;
    private ImageView listingImage;

    private listingHelper helper = new listingHelper(this);
    private String listingTitleStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listing);

        listingTitle = (TextView)findViewById(R.id.listing_Title);
        listingTag = (TextView)findViewById(R.id.listing_Tag);
        listingDescription = (TextView)findViewById(R.id.listing_Description);
        listingDimensionX = (TextView)findViewById(R.id.listing_Dimension_X);
        listingDimensionY = (TextView)findViewById(R.id.listing_Dimension_Y);
        listingDimensionZ = (TextView)findViewById(R.id.listing_Dimension_Z);
        listingArButton = (Button)findViewById(R.id.listing_Ar_Button);
        listingConfirmButton = (Button)findViewById(R.id.listing_Confirm_Button);
        listingImage = (ImageView)findViewById(R.id.listing_Image);

        listingArButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(listing.this, ar.class);
                startActivity(i);
            }
        });

        listingConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.updateListingStatus(listingTitle.getText().toString());
                Intent i = new Intent(listing.this, confirmed.class);
                startActivity(i);
            }
        });

        listingTitleStr = getIntent().getStringExtra("title");
        if (listingTitleStr != null){
            load();
        }
    }

    private void load() {
        Cursor c = helper.getByTitle(listingTitleStr);
        c.moveToFirst();
        listingTitle.setText(helper.getListingTitle(c));
        listingTag.setText(helper.getListingTag(c));
        listingDescription.setText(helper.getListingDesciption(c));
        listingDimensionX.setText(helper.getListingDimensionX(c));
        listingDimensionY.setText(helper.getListingDimensionY(c));
        listingDimensionZ.setText(helper.getListingDimensionZ(c));
        //listingImage.setImageURI(Uri.parse(helper.getListingImage(c)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
