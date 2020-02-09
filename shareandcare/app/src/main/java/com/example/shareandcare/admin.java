package com.example.shareandcare;

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
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class admin extends Activity {

    private Cursor c = null;
    private listingHelperAdminAdapter adapter = new listingHelperAdminAdapter(c);
    private listingHelper helper = new listingHelper(this);
    private ListView adminList;
    private TextView adminEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        adminList = (ListView)findViewById(R.id.admin_List);
        adminEmpty = (TextView)findViewById(R.id.admin_Empty);
        c = helper.getAllConfirmed();
        adminList.setAdapter(adapter);

        adminList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                c.moveToPosition(i);
                String id = helper.getID(c);
                String title = helper.getListingTitle(c);
                Intent intent = new Intent(admin.this, adminListing.class);
                intent.putExtra("id",id);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(c != null) {
            c.close();
        }
        c = helper.getAllConfirmed();

        if(c.getCount() > 0) {
            adminEmpty.setVisibility(View.INVISIBLE);
        }
        adapter.swapCursor(c);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            Intent i = new Intent(admin.this, mainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);

    }

    class listingHelperAdminAdapter extends CursorAdapter {
        listingHelperAdminAdapter(Cursor c) {
            super(admin.this, c);
        }

        @Override
        public void bindView(View row, Context ctxt, Cursor c) {
            listingHolderAdmin holder = (listingHolderAdmin) row.getTag();

            holder.populate(c, helper);
        }

        @Override
        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.listingrow, parent, false);
            listingHolderAdmin holder = new listingHolderAdmin(row);

            row.setTag(holder);
            return (row);
        }
    }

    static class listingHolderAdmin {
        private TextView listingTitle, listingTag, listingDescription;
        //private ImageView furnitureImage = null;

        listingHolderAdmin(View row){
            listingTitle = (TextView) row.findViewById(R.id.listing_Title);
            listingTag = (TextView) row.findViewById(R.id.listing_Tag);
            listingDescription = (TextView) row.findViewById(R.id.listing_Description);
            //furnitureImage =(ImageView) row.findViewById(R.id.icon);
        }

        void populate(Cursor c,listingHelper helper){
            listingTitle.setText(helper.getListingTitle(c));
            listingTag.setText(helper.getListingTag(c));
            listingDescription.setText(helper.getListingDesciption(c));
            //furnitureImage.setImageURI(Uri.parse(helper.getfurnitureImage(c)));
        }
    }
}
