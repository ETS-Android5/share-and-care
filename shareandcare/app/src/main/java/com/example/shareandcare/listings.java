package com.example.shareandcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class listings extends Activity {

    private Cursor c = null;
    private listingHelperAdapter adapter = new listingHelperAdapter(c);
    private listingHelper helper = new listingHelper(this);
    private ListView list;
    private Button listingsAddListingButton;
    private TextView empty = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listings);

        empty = (TextView)findViewById(R.id.listings_Empty);
        list = (ListView)findViewById(R.id.listings_List);
        listingsAddListingButton = (Button)findViewById(R.id.listings_Add_Listing_Button);
        c = helper.getAll();
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                helper.delete(String.valueOf(l));
                //helper.resetDatabase();
                Toast.makeText(listings.this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c.moveToPosition(i);
                String title = helper.getListingTitle(c);
                Intent intent = new Intent(listings.this, listing.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });

        listingsAddListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(listings.this, addListing.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();

        if (c != null) {
            c.close();
        }
        c = helper.getAll();

        if (c.getCount() > 0) {
            empty.setVisibility(View.INVISIBLE);
        }
        adapter.swapCursor(c);

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
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        if (item.getItemId()== R.id.log_out) {
            Toast.makeText(this, "Logged out...", Toast.LENGTH_SHORT).show();
            Intent intent;
            intent = new Intent(listings.this, mainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    class listingHelperAdapter extends CursorAdapter {
        listingHelperAdapter(Cursor c) {
            super(listings.this, c);
        }

        @Override
        public void bindView(View row, Context ctxt, Cursor c) {
            listingHolder holder = (listingHolder) row.getTag();

            holder.populate(c, helper);
        }

        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.listingrow, parent, false);
            listingHolder holder = new listingHolder(row);

            row.setTag(holder);
            return (row);
        }
    }

    static class listingHolder {
        private TextView listingTitle, listingTag, listingDescription;
        //private ImageView furnitureImage = null;

        listingHolder (View row){
            listingTitle = (TextView) row.findViewById(R.id.listing_Title);
            listingTag = (TextView) row.findViewById(R.id.listing_Tag);
            listingDescription = (TextView) row.findViewById(R.id.listing_Description);
        }

        void populate (Cursor c,listingHelper helper){
            listingTitle.setText(helper.getListingTitle(c));
            listingTag.setText(helper.getListingTag(c));
            listingDescription.setText(helper.getListingDesciption(c));
        }
    }
}
