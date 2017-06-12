package com.pwr.teamproject.shopassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {


    ListView myListView;

    private String lat;
    private String lng;
    private ArrayList<DBProduct> dbProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBManager dbManager = new DBManager(this);
        dbProducts = dbManager.getAllProducts();

        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");


        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(ShoppingListActivity.this, dbProducts, lat, lng);
        myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(shoppingListAdapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshAdapter(){
        DBManager dbManager = new DBManager(this);
        dbProducts = dbManager.getAllProducts();
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(ShoppingListActivity.this, dbProducts, lat, lng);
        myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(shoppingListAdapter);
    }

}
