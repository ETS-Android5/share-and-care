package com.example.shareandcare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addListing extends Activity {
    private EditText addTitle, addTag, addDimension_X, addDimension_Y, addDimension_Z;
    private Button  addImageButton, addConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlisting);

        addTitle = (EditText)findViewById(R.id.add_Title);
        addTag = (EditText)findViewById(R.id.add_Tag);
        addDimension_X = (EditText)findViewById(R.id.add_Dimension_X);
        addDimension_Y = (EditText)findViewById(R.id.add_Dimension_Y);
        addDimension_Z = (EditText)findViewById(R.id.add_Dimension_Z);
        addImageButton = (Button)findViewById(R.id.add_Image_Button);
        addConfirmButton = (Button)findViewById(R.id.add_Confirm_Button);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addConfirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
    }

}
