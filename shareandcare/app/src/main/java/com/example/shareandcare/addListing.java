package com.example.shareandcare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class addListing extends Activity {
    private EditText addTitle, addTag, addDimension_X, addDimension_Y, addDimension_Z, addDescription;
    private ImageView addAddedImage;
    private Button  addImageButton, addConfirmButton;
    private listingHelper helper = new listingHelper(this);
    private String addTitleStr, addTagStr, addDescriptionStr, addDimensionXStr, addDimensionYStr, addDimensionZStr;

    private String uriStr = "";

    private static final int IMAGE_PICK_CODE = 1;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlisting);

        addTitle = (EditText)findViewById(R.id.add_Title);
        addTag = (EditText)findViewById(R.id.add_Tag);
        addDescription = (EditText)findViewById(R.id.add_Description);
        addDimension_X = (EditText)findViewById(R.id.add_Dimension_X);
        addDimension_Y = (EditText)findViewById(R.id.add_Dimension_Y);
        addDimension_Z = (EditText)findViewById(R.id.add_Dimension_Z);
        addAddedImage = (ImageView)findViewById(R.id.add_Added_Image);
        addImageButton = (Button)findViewById(R.id.add_Image_Button);
        addConfirmButton = (Button)findViewById(R.id.add_Confirm_Button);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        pickImage();
                    }
                }
            }
        });

        addConfirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addTitleStr = addTitle.getText().toString();
                addTagStr = addTag.getText().toString();
                addDescriptionStr = addDescription.getText().toString();
                addDimensionXStr = addDimension_X.getText().toString();
                addDimensionYStr = addDimension_Y.getText().toString();
                addDimensionZStr = addDimension_Z.getText().toString();

                helper.addListing(addTitleStr, addTagStr, addDescriptionStr, addDimensionXStr, addDimensionYStr, addDimensionZStr, uriStr);
                finish();

                Intent i;
                i = new Intent(addListing.this, listings.class);
                startActivity(i);
            }
        });
    }

    /*private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }*/

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == PERMISSION_CODE) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(getApplicationContext(), "Permissions Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            addAddedImage.setImageURI(data.getData());
            uriStr = String.valueOf(data.getData());
        }
    }

}
