package com.pwr.teamproject.shopassistant;

import android.os.AsyncTask;
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

public class ProductListActivity extends AppCompatActivity {

    // sample data
    ListView myListView;

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

        ProductFetchTask fetchTask = new ProductListActivity.ProductFetchTask(intentString);
        fetchTask.execute((Void) null);

        /*
        String JSON = jrespons.getJSON();
        ArrayList<Product> productList = new ArrayList<>();

        try {
            JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
            Log.d("objectPOTATO", array.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                productList.add(new Product(object.getInt("Id"), object.getString("Name"), object.getString("Desc"), object.getJSONObject("ProdCategory").getInt("Id"), object.getJSONObject("ProdCategory").getString("Name"), object.getString("Photo")));
                //Products.add(new Product(object.getInt("Id"), object.getString("Name"), object.getString("Desc"), object.getInt("ProdCategoryID"), object.getString("ProdCategory"), object.getString("Photo")));
                Log.d("Product", productList.get(i).getName());
                Log.d("Description", productList.get(i).getDesc());
                Log.d("Category", productList.get(i).getProdCategory());

            }
        } catch (JSONException e) {
            // Appropriate error handling code
            Log.d("objectPOTATO", "AdrianPotato");
        }
        */


        /*
        final JSONResponse jrespons = new JSONResponse(intentString);

        Thread thread = new Thread(new Runnable() {
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
        ArrayList<Product> productList = new ArrayList<>();

        try {
            JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
            Log.d("objectPOTATO", array.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                productList.add(new Product(object.getInt("Id"), object.getString("Name"), object.getString("Desc"), object.getJSONObject("ProdCategory").getInt("Id"), object.getJSONObject("ProdCategory").getString("Name"), object.getString("Photo")));
                //Products.add(new Product(object.getInt("Id"), object.getString("Name"), object.getString("Desc"), object.getInt("ProdCategoryID"), object.getString("ProdCategory"), object.getString("Photo")));
                Log.d("Product", productList.get(i).getName());
                Log.d("Description", productList.get(i).getDesc());
                Log.d("Category", productList.get(i).getProdCategory());

            }
        } catch (JSONException e) {
            // Appropriate error handling code
            Log.d("objectPOTATO", "AdrianPotato");
        }
        */


        /*
        ProductAdapter productAdapter = new ProductAdapter(this, productList);
        myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(productAdapter);

        // on product click
        myListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Toast toast = Toast.makeText(getBaseContext(), "PRODUCT " + id + " CLICKED", Toast.LENGTH_SHORT);
                toast.show();
                openOptionsMenu();

            }
        });
        */


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

    public class ProductFetchTask extends AsyncTask<Void, Void, String> {

        String productName;

        ProductFetchTask(String productName) {
            this.productName = productName;
        }

        protected String doInBackground(Void... params) {

            NetworkManager networkManager = new NetworkManager();
            return networkManager.getProducts(productName);

        }

        protected void onPostExecute(final String JSON) {
            ArrayList<Product> productList = new ArrayList<>();

            try {
                if(JSON != null){
                    JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
                    Log.d("objectPOTATO", array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        productList.add(new Product(object.getInt("Id"), object.getString("Name"), object.getString("Desc"), object.getJSONObject("ProdCategory").getInt("Id"), object.getJSONObject("ProdCategory").getString("Name"), object.getString("Photo")));
                        //Products.add(new Product(object.getInt("Id"), object.getString("Name"), object.getString("Desc"), object.getInt("ProdCategoryID"), object.getString("ProdCategory"), object.getString("Photo")));
                        Log.d("Product", productList.get(i).getName());
                        Log.d("Description", productList.get(i).getDesc());
                        Log.d("Category", productList.get(i).getProdCategory());

                    }
                }
            } catch (JSONException e) {
                // Appropriate error handling code
                Log.d("objectPOTATO", "AdrianPotato");
            }

            ProductAdapter productAdapter = new ProductAdapter(ProductListActivity.this, productList);
            myListView = (ListView) findViewById(R.id.myListView);
            myListView.setAdapter(productAdapter);

            // on product click
            myListView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Toast toast = Toast.makeText(getBaseContext(), "PRODUCT " + id + " CLICKED", Toast.LENGTH_SHORT);
                    toast.show();
                    openOptionsMenu();

                }
            });
        }
    }


}
