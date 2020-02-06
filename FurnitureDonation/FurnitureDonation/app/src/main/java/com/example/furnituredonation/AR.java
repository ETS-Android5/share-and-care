package com.example.furnituredonation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Color;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
//import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;


public class AR extends AppCompatActivity implements View.OnClickListener {
    ArFragment arFragment;
    private ModelRenderable sofaRenderable;
    private ModelRenderable sofa2Renderable;
    private ModelRenderable tableRenderable;
    private ModelRenderable chairRenderable;
    private ModelRenderable storageRenderable;
    private ModelRenderable bedRenderable;
    private FurnitureHelper helper = null;
    private String furnitureID = "";
    private String furnitureType ="";





    ImageView sofa,sofa2,bed,chair,storage,table;

    View arrayView[];
    ViewRenderable name_item;

    int selected = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ar);

        arFragment = (ArFragment)getSupportFragmentManager()
                .findFragmentById(R.id.sceneform_ux_fragment);

        //View
        sofa = (ImageView)findViewById(R.id.sofa1);
        sofa2 = (ImageView)findViewById(R.id.sofa2);
        bed = (ImageView)findViewById(R.id.bedImage);
        chair = (ImageView)findViewById(R.id.chairImage);
        storage = (ImageView)findViewById(R.id.storageImage);
        table = (ImageView)findViewById(R.id.tableImage);







        setArrayView();
        setClickListener();

        setupModel();
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                // when user tap on plane, we will add model

                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                createModel(anchorNode,selected);

            }
        });
    }
    private void setupModel() {
        ModelRenderable.builder()
                .setSource(this, R.raw.sofa)
                .build().thenAccept(renderable-> sofaRenderable = renderable)
                .exceptionally(
                        throwable ->{
                            Toast.makeText(this,"Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder()
                .setSource(this, R.raw.sofa2)
                .build().thenAccept(renderable-> sofa2Renderable = renderable)
                .exceptionally(
                        throwable ->{
                            Toast.makeText(this,"Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder()
                .setSource(this, R.raw.bed)
                .build().thenAccept(renderable-> bedRenderable = renderable)
                .exceptionally(
                        throwable ->{
                            Toast.makeText(this,"Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder()
                .setSource(this, R.raw.chair)
                .build().thenAccept(renderable-> chairRenderable = renderable)
                .exceptionally(
                        throwable ->{
                            Toast.makeText(this,"Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder()
                .setSource(this, R.raw.storage)
                .build().thenAccept(renderable-> storageRenderable = renderable)
                .exceptionally(
                        throwable ->{
                            Toast.makeText(this,"Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ModelRenderable.builder()
                .setSource(this, R.raw.table)
                .build().thenAccept(renderable-> tableRenderable = renderable)
                .exceptionally(
                        throwable ->{
                            Toast.makeText(this,"Unable to load model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

    }

    private void createModel(AnchorNode anchorNode, int selected) {
        helper = new FurnitureHelper(this);
        furnitureID = getIntent().getStringExtra("ID");
        if(furnitureID != null){
            Cursor c = helper.getById(furnitureID);
            c.moveToFirst();
            furnitureType = String.valueOf(helper.getfurnitureType(c));
        }
        if(selected ==1 || furnitureType.equals("Sofa")){
            TransformableNode sofa = new TransformableNode(arFragment.getTransformationSystem());
            sofa.setParent(anchorNode);
            sofa.setRenderable(sofaRenderable);
            sofa.select();
        }
        if(selected ==2 ){
            TransformableNode sofa2 = new TransformableNode(arFragment.getTransformationSystem());
            sofa2.setParent(anchorNode);
            sofa2.setRenderable(sofa2Renderable);
            sofa2.select();
        }
        if(selected ==3 || furnitureType.equals("Bed")){
            TransformableNode bed = new TransformableNode(arFragment.getTransformationSystem());
            bed.setParent(anchorNode);
            bed.setRenderable(bedRenderable);
            bed.select();
        }
        if(selected ==4 || furnitureType.equals("Chair")){
            TransformableNode chair = new TransformableNode(arFragment.getTransformationSystem());
            chair.setParent(anchorNode);
            chair.setRenderable(chairRenderable);
            chair.select();
        }
        if(selected ==5 || furnitureType.equals("Storage")){
            TransformableNode storage = new TransformableNode(arFragment.getTransformationSystem());
            storage.setParent(anchorNode);
            storage.setRenderable(storageRenderable);
            storage.select();
        }
        if(selected ==6 || furnitureType.equals("Table")){
            TransformableNode table = new TransformableNode(arFragment.getTransformationSystem());
            table.setParent(anchorNode);
            table.setRenderable(tableRenderable);
            table.select();
        }
    }

    private void setClickListener(){
        for (int i=0; i<arrayView.length;i++){
            arrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView(){
        arrayView = new View[]{
                sofa,sofa2,bed,chair,storage,table
        };
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.sofa1){
            selected = 1;
            setBackground(view.getId());
        }
        if(view.getId() == R.id.sofa2){
            selected = 2;
            setBackground(view.getId());
        }
        if(view.getId() == R.id.bedImage){
            selected = 3;
            setBackground(view.getId());
        }
        if(view.getId() == R.id.chairImage){
            selected = 4;
            setBackground(view.getId());
        }
        if(view.getId() == R.id.storageImage){
            selected = 5;
            setBackground(view.getId());
        }
        if(view.getId() == R.id.tableImage){
            selected = 6;
            setBackground(view.getId());
        }
    }

    private void setBackground(int id){
        for(int i=0; i<arrayView.length; i++){
            if(arrayView[i].getId() == id)
                arrayView[i].setBackgroundColor(Color.parseColor("#80333639"));
            else
                arrayView[i].setBackgroundColor(Color.TRANSPARENT);
        }
    }
}

