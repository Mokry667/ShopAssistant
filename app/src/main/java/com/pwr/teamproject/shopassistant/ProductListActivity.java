package com.pwr.teamproject.shopassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    // sample data
    ListView myListView;

    String[] productNames ={
            "Product1",
            "Product2",
            "Product3",
            "Product4",
            "Product5",
            "Product6",
            "Product7"
    };

    Double[] productPrices={
            12.13,
            15.11,
            10.99,
            12.13,
            15.11,
            10.99,
            12.13,
    };

    Double[] closestShops={
            7.3,
            9.2,
            7.3,
            9.2,
            7.3,
            9.2,
            7.3,
    };

    Integer[] images={
            R.drawable.cebula,
            R.drawable.tatra,
            R.drawable.woda,
            R.drawable.cebula,
            R.drawable.tatra,
            R.drawable.woda,
            R.drawable.tatra,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String intentString = getIntent().getStringExtra("searchString");
        Log.d("IntentString", intentString);

        final JSONResponse jrespons = new JSONResponse(intentString);

       Thread thread =  new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                jrespons.doInBackground();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String JSON = jrespons.getJSON();

        List<String> Jimages = new ArrayList<>();
        List<String> JproductNames = new ArrayList<>();
        List<String> JproductPrices = new ArrayList<>();
        List<String> JclosestShops = new ArrayList<>();

        try {
            JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
            Log.d("objectPOTATO", array.toString());
            for (int i = 0; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                JproductNames.add(object.getString("Name"));

            }
        } catch (JSONException e) {
            // Appropriate error handling code
            Log.d("objectPOTATO", "AdrianPotato");
        }



        ProductAdapter productAdapter = new ProductAdapter(this, images, productNames, productPrices, closestShops);
        myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(productAdapter);

        // on product click
        myListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Toast toast = Toast.makeText(getBaseContext(),  "PRODUCT " + id + " CLICKED", Toast.LENGTH_SHORT);
                toast.show();
                openOptionsMenu();

            }
        });




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


}
