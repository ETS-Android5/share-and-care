package com.example.furnituredonation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class FurnitureList extends AppCompatActivity {

    private Cursor model = null;
    private FurnitureHelperAdapter adapter = null;
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
        adapter = new FurnitureHelperAdapter(model);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                helper.delete(String.valueOf(l));
                Toast.makeText(FurnitureList.this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model.moveToPosition(i);
                String recordID = helper.getID(model);
                Intent intent;
                intent = new Intent(FurnitureList.this, FurnitureShow.class);
                intent.putExtra("ID",recordID);
                startActivity(intent);
            }
        });



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
            intent = new Intent(FurnitureList.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    class FurnitureHelperAdapter extends CursorAdapter {
        FurnitureHelperAdapter(Cursor c) {
            super(FurnitureList.this, c);
        }

        @Override
        public void bindView(View row, Context ctxt, Cursor c) {
            FurnitureHolder holder = (FurnitureHolder) row.getTag();

            holder.populateFrom(c, helper);
        }

        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            FurnitureHolder holder = new FurnitureHolder(row);

            row.setTag(holder);
            return (row);
        }
    }

    static class FurnitureHolder {
        private TextView furnitureDetails = null;
        private TextView furnitureMeasurement = null;
        private TextView furnitureType = null;
        //private ImageView furnitureImage = null;

        FurnitureHolder (View row){
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
