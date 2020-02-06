package com.example.furnituredonation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Admin extends Activity {
    private Cursor model = null;
    private FurnitureHelperAdapter1 adapter = null;
    private FurnitureHelper helper = null;
    private ListView list;
    private TextView empty = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.furniture_list);

        empty = (TextView) findViewById(R.id.empty);
        helper = new FurnitureHelper(this);
        list = (ListView)  findViewById(R.id.list);
        model = helper.getAll();
        adapter = new FurnitureHelperAdapter1(model);
        list.setAdapter(adapter);

    }

    @Override
    protected void onResume(){
        super.onResume();

        if (model != null) {
            model.close();
        }
        model = helper.getAll();

        if (model.getCount() > 0) {
            empty.setVisibility(View.INVISIBLE);
        }
        adapter.swapCursor(model);

        //_music.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        // _music.release();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        if (item.getItemId()== R.id.log_out) {
            Toast.makeText(this, "Account is Log Out", Toast.LENGTH_SHORT).show();
            Intent intent;
            intent = new Intent(Admin.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    class FurnitureHelperAdapter1 extends CursorAdapter {
        FurnitureHelperAdapter1(Cursor c) {
            super(Admin.this, c);
        }

        @Override
        public void bindView(View row, Context ctxt, Cursor c) {
            FurnitureHolder1 holder = (FurnitureHolder1) row.getTag();

            holder.populateFrom(c, helper);
        }

        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            FurnitureHolder1 holder = new FurnitureHolder1(row);

            row.setTag(holder);
            return (row);
        }
    }
        static class FurnitureHolder1 {
            private TextView furnitureDetails = null;
            private TextView furnitureMeasurement = null;
            private TextView furnitureType = null;
            //private ImageView furnitureImage = null;

            FurnitureHolder1 (View row){
                furnitureDetails = (TextView) row.findViewById(R.id.furniDetails);
                furnitureMeasurement = (TextView) row.findViewById(R.id.furniMeasurement);
                furnitureType = (TextView) row.findViewById(R.id.furniType);
                //furnitureImage =(ImageView) row.findViewById(R.id.icon);
            }

            void populateFrom (Cursor c,FurnitureHelper helper){
                furnitureDetails.setText(helper.getfurnitureDetails(c));
                furnitureMeasurement.setText(helper.getfurnitureMeasurement(c));
                furnitureType.setText(helper.getfurnitureType(c));
                //furnitureImage.setImageURI(Uri.parse(helper.getfurnitureImage(c)));
            }
        }
    }

