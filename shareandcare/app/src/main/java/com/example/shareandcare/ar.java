package com.example.shareandcare;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ar extends AppCompatActivity {

    ArFragment arFragment;
    private float arDimensionX, arDimensionY, arDimensionZ;
    private listingHelper helper = new listingHelper(this);
    private Button arBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ar);

        arBackButton = (Button)findViewById(R.id.ar_Back_Button);
        arFragment = (ArFragment)getSupportFragmentManager()
                .findFragmentById(R.id.sceneform_ux_fragment);

        arBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                // when user tap on plane, we will add model

                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                showModel(anchorNode);
            }
        });
    }

    private void showModel(AnchorNode anchorNode) {
        Cursor c = helper.getByTitle(getIntent().getStringExtra("title"));
        c.moveToFirst();
        arDimensionX = Float.parseFloat(helper.getListingDimensionX(c));
        arDimensionX = arDimensionX/100.0f;
        arDimensionY = Float.parseFloat(helper.getListingDimensionY(c));
        arDimensionY = arDimensionY/100.0f;
        arDimensionZ = Float.parseFloat(helper.getListingDimensionZ(c));
        arDimensionZ = arDimensionZ/100.0f;

        MaterialFactory.makeTransparentWithColor(this, new Color(android.graphics.Color.WHITE))
                .thenAccept(
                        material -> {
                            ModelRenderable model = ShapeFactory.makeCube(new Vector3(arDimensionX, arDimensionY, arDimensionZ), Vector3.zero(), material);
                            model.setShadowCaster(false);
                            model.setShadowReceiver(false);

                            TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                            transformableNode.getScaleController().setSensitivity(0);
                            transformableNode.setParent(anchorNode);
                            transformableNode.setRenderable(model);
                            transformableNode.select();
                        }
                );
    }
}

