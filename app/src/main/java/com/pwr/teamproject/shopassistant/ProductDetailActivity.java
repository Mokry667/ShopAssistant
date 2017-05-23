package com.pwr.teamproject.shopassistant;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    ListView myListView;

    private static int DEFAULT_WIDTH = 500;
    private static int DEFAULT_HEIGHT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        String intentProductName = getIntent().getStringExtra("productName");
        setTitle(intentProductName);

        /*
        String intentDescription = getIntent().getStringExtra("description");
        String intentPhoto = getIntent().getStringExtra("photo");

        TextView productName = (TextView) findViewById(R.id.productName);
        TextView description = (TextView) findViewById(R.id.productDesc);
        ImageView image = (ImageView) findViewById(R.id.image);

        productName.setText(intentProductName);
        description.setText(intentDescription);

        Picasso.with(this)
                .load(intentPhoto)
                .resize(DEFAULT_WIDTH, DEFAULT_HEIGHT)
                //.centerCrop()
                .into(image);

        */

        //Log.d("Product Name", intentString);

        ProductDetailActivity.StoreProductFetchTask fetchTask = new ProductDetailActivity.StoreProductFetchTask(intentProductName);
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


    public class StoreProductFetchTask extends AsyncTask<Void, Void, String> {

        String productName;

        StoreProductFetchTask(String productName) {
            this.productName = productName;
        }

        protected String doInBackground(Void... params) {

            NetworkManager networkManager = new NetworkManager();
            return networkManager.getStoreProducts(productName);

        }

        protected void onPostExecute(final String JSON) {
            ArrayList<StoreProduct> storeProductList = new ArrayList<>();

            try {
                if(JSON != null){
                    JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
                    Log.d("objectPOTATO", array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        JSONObject storeObject = object.getJSONObject("Store");
                        JSONObject productObject = object.getJSONObject("Product");


                        // WTF IS THIS SHIT
                        storeProductList.add(new StoreProduct(object.getInt("Id"), object.getInt("StoreId"),
                                new Store(storeObject.getInt("Id"), storeObject.getString("Name"), storeObject.getString("Address"), storeObject.getDouble("Lat"), storeObject.getDouble("Lng")),
                                object.getInt("Id"), new Product(productObject.getInt("Id"), productObject.getString("Name"), productObject.getString("Desc"),
                                new ProdCategory(productObject.getJSONObject("ProdCategory").getInt("Id"), productObject.getJSONObject("ProdCategory").getString("Name")), productObject.getString("Photo")),
                                object.getInt("Amount"), object.getDouble("Price"), object.getDouble("Weigh"), object.getDouble("PPQ"), object.getDouble("PPW") ));


                        Log.d("Product", storeProductList.get(i).getProduct().getName());
                        Log.d("Description", storeProductList.get(i).getProduct().getDesc());
                        Log.d("Category", storeProductList.get(i).getProduct().getProdCategory().getName());

                    }
                }
            } catch (JSONException e) {
                // Appropriate error handling code
                Log.d("objectPOTATO", "AdrianPotato");
            }

            ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(ProductDetailActivity.this, storeProductList);
            myListView = (ListView) findViewById(R.id.myListView);
            myListView.setAdapter(productDetailAdapter);


            // header set up
            View header = getLayoutInflater().inflate(R.layout.product_detail_header, null);

            String intentProductName = getIntent().getStringExtra("productName");
            String intentDescription = getIntent().getStringExtra("description");
            String intentPhoto = getIntent().getStringExtra("photo");

            TextView productName = (TextView) header.findViewById(R.id.productName);
            TextView description = (TextView) header.findViewById(R.id.productDesc);
            ImageView image = (ImageView) header.findViewById(R.id.image);

            productName.setText(intentProductName);
            description.setText(intentDescription);

            Picasso.with(ProductDetailActivity.this)
                    .load(intentPhoto)
                    .resize(DEFAULT_WIDTH, DEFAULT_HEIGHT)
                    //.centerCrop()
                    .into(image);

            // header addition
            myListView.addHeaderView(header);

        }
    }

}
