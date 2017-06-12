package com.pwr.teamproject.shopassistant;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    // sample data
    ListView myListView;

    ArrayList<Product> productList;
    ArrayList<ProductInfo> productInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String searchString = getIntent().getStringExtra("searchString");
        String lat = getIntent().getStringExtra("lat");
        String lng = getIntent().getStringExtra("lng");

        setTitle(searchString);

        ProductFetchTask fetchTask = new ProductListActivity.ProductFetchTask(searchString, lat, lng);
        fetchTask.execute((Void) null);

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
        String lat;
        String lng;

        ProductFetchTask(String productName, String lat, String lng) {
            this.productName = productName;
            this.lat = lat;
            this.lng = lng;
        }

        protected String doInBackground(Void... params) {

            NetworkManager networkManager = new NetworkManager();
            return networkManager.getProductsCheapest(productName, lat, lng);
            //return networkManager.getProducts(productName);

        }

        protected void onPostExecute(final String JSON) {
            productList = new ArrayList<>();
            productInfoList = new ArrayList<>();

            // temporary solution
            //productNames = new ArrayList<>();

            try {
                if(JSON != null){
                    JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
                    Log.d("objectPOTATO", array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        productList.add(new Product(object.getJSONObject("Data").getInt("Id"), object.getJSONObject("Data").getString("Name"), object.getJSONObject("Data").getString("Desc"),
                                new ProdCategory(object.getJSONObject("Data").getJSONObject("ProdCategory").getInt("Id"), object.getJSONObject("Data").getJSONObject("ProdCategory").getString("Name")), object.getJSONObject("Data").getString("Photo")));
                        Log.d("TEST", "It's TEST");
                        productInfoList.add(new ProductInfo(object.getDouble("Price"), object.getString("Distance"), object.getString("Time")));

                    }
                }
            } catch (JSONException e) {
                // Appropriate error handling code
                Log.d("objectPOTATO", "AdrianPotato");
            }

            ProductAdapter productAdapter = new ProductAdapter(ProductListActivity.this, productList, productInfoList, lat, lng);
            myListView = (ListView) findViewById(R.id.myListView);
            myListView.setAdapter(productAdapter);


            // on product click
            myListView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    openOptionsMenu();

                    Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                    intent.putExtra("productName", productList.get((int)id).getName());
                    intent.putExtra("description", productList.get((int)id).getDesc());
                    intent.putExtra("photo", productList.get((int)id).getPhoto());

                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    startActivity(intent);

                }
            });
        }
    }


}
