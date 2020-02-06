package com.example.furnituredonation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;

public class ChoosePic extends Activity {

    ImageView imageView;
    Button choosepicButton, saveButton;

    String uriStr = "", furnitureID, detailsStr, measurementStr, furnitype;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private  FurnitureHelper helper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_picture);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChoosePic.this);
        detailsStr= sharedPreferences.getString("detailsStr", " ");
        measurementStr= sharedPreferences.getString("measurementStr", " ");
        furnitype= sharedPreferences.getString("furnitype", " ");


        imageView = findViewById(R.id.image_view);
        choosepicButton =findViewById(R.id.choose_pic_button);
        saveButton = findViewById(R.id.save_button);
        helper = new FurnitureHelper(this);


        choosepicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check runtime permission
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED){
                        //permission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        // permission already granted
                            pickImageFromGallery();
                    }

                }
                else {
                    // system os is less than marshmallow
                    pickImageFromGallery();
                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (furnitureID == null){
                    helper.insert(detailsStr,measurementStr,furnitype, uriStr);

                }
                finish();


                Intent i ;
                i = new Intent(ChoosePic.this, FurnitureList.class);
                startActivity(i);
            }
        });
    }
    private void pickImageFromGallery() {
            // intent to pick image
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==
                PackageManager.PERMISSION_GRANTED){
                    // permission granted
                    pickImageFromGallery();
                }
                else {
                    // permission denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    // handle result of picked image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            // set image to image view
            imageView.setImageURI(data.getData());
            uriStr = String.valueOf(data.getData());
        }
    }
}
