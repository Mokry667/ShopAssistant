package com.pwr.teamproject.shopassistant;

import android.content.Intent;
import android.location.Location;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ProductListActivity extends AppCompatActivity {

    // sample data
    ListView myListView;

    ArrayList<Product> productList;
    ArrayList<ProductInfo> productInfoList;

    ArrayList<StoreProduct> storeProductList;

    ArrayList<Float> distanceList;

    ArrayList<Double> latList;
    ArrayList<Double> lngList;

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
        String shoppingMode = getIntent().getStringExtra("shoppingMode");

        setTitle(searchString);

        ProductFetchTask fetchTask = new ProductListActivity.ProductFetchTask(searchString, lat, lng, shoppingMode);
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
        String shoppingMode;

        ProductFetchTask(String productName, String lat, String lng, String shoppingMode) {
            this.productName = productName;
            this.lat = lat;
            this.lng = lng;
            this.shoppingMode = shoppingMode;
        }

        protected String doInBackground(Void... params) {

            NetworkManager networkManager = new NetworkManager();
            return networkManager.getStoreProducts(productName);
            //return networkManager.getProductsCheapest(productName, lat, lng);

        }

        protected void onPostExecute(final String JSON) {
            productList = new ArrayList<>();
            productInfoList = new ArrayList<>();
            distanceList = new ArrayList<>();

            storeProductList = new ArrayList<>();

            latList = new ArrayList<>();
            lngList = new ArrayList<>();

            // temporary solution
            //productNames = new ArrayList<>();

            try {
                if(JSON != null){
                    JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
                    Log.d("objectPOTATO", array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        /*
                        JSONObject object = array.getJSONObject(i);



                        productList.add(new Product(object.getJSONObject("Data").getInt("Id"), object.getJSONObject("Data").getString("Name"), object.getJSONObject("Data").getString("Desc"),
                                new ProdCategory(object.getJSONObject("Data").getJSONObject("ProdCategory").getInt("Id"), object.getJSONObject("Data").getJSONObject("ProdCategory").getString("Name")), object.getJSONObject("Data").getString("Photo")));
                        Log.d("TEST", "It's TEST");
                        productInfoList.add(new ProductInfo(object.getDouble("Price"), object.getString("Distance"), object.getString("Time")));
                        */

                        JSONObject object = array.getJSONObject(i);
                        JSONObject storeObject = object.getJSONObject("Store");
                        JSONObject productObject = object.getJSONObject("Product");


                        // WTF IS THIS SHIT
                        storeProductList.add(new StoreProduct(object.getInt("Id"), object.getInt("StoreId"),
                                new Store(storeObject.getInt("Id"), storeObject.getString("Name"), storeObject.getString("Address"), storeObject.getDouble("Lat"), storeObject.getDouble("Lng")),
                                object.getInt("ProdId"), new Product(productObject.getInt("Id"), productObject.getString("Name"), productObject.getString("Desc"),
                                new ProdCategory(productObject.getJSONObject("ProdCategory").getInt("Id"), productObject.getJSONObject("ProdCategory").getString("Name")), productObject.getString("Photo")),
                                object.getInt("Amount"), object.getDouble("Price"), object.getDouble("Weigh"), object.getDouble("PPQ"), object.getDouble("PPW") ));

                        float[] results = new float[1];
                        Location.distanceBetween(Double.valueOf(lat), Double.valueOf(lng), storeProductList.get(i).getStore().getLat(), storeProductList.get(i).getStore().getLng(), results);
                        distanceList.add(results[0]);


                    }
                }
            } catch (JSONException e) {
                // Appropriate error handling code
                Log.d("objectPOTATO", "AdrianPotato");
            }

            ArrayList<StoreProduct> uniqueProducts;

            if(shoppingMode.equals("Fast")){
                sortClosest();
            }
            else if(shoppingMode.equals("Cheap")){
                sortCheapest();
            }


            uniqueProducts = getUniques();





            ProductAdapter productAdapter = new ProductAdapter(ProductListActivity.this, productList, productInfoList, latList, lngList, lat, lng, uniqueProducts);
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


    /*
    private void sortCheapest(){
        Collections.sort(storeProductList, new Comparator<StoreProduct>() {
            public int compare(StoreProduct o1, StoreProduct o2) {
                return Double.compare(o1.price, o2.price);
            }
        });
    }
    */


    // TODO optimize this
    private void sortCheapest(){
        for(int i=0;i<storeProductList.size();i++){
            for(int j=i+1;j<storeProductList.size();j++){
                if(storeProductList.get(i).getPrice()>storeProductList.get(j).getPrice()){
                    Collections.swap(distanceList, i, j);
                    Collections.swap(storeProductList, i, j);
                }
            }
        }
    }

    // TODO optimize this
    private void sortClosest() {
        for(int i=0;i<storeProductList.size();i++){
            for(int j=i+1;j<storeProductList.size();j++){
                if(distanceList.get(i)>distanceList.get(j)){
                    Collections.swap(distanceList, i, j);
                    Collections.swap(storeProductList, i, j);
                }
            }
        }
    }

    private ArrayList<StoreProduct> getUniques(){
        ArrayList<StoreProduct> uniqueList = new ArrayList<StoreProduct>();
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        DecimalFormat df = new DecimalFormat("#.##");

        for(StoreProduct s : storeProductList){
            if(!indexes.contains(s.getProdID())){
                Log.d("INDEX", String.valueOf(s.getProdID()));
                productList.add(s.getProduct());

                String distance = df.format(distanceList.get(storeProductList.indexOf(s)) / 1000);
                productInfoList.add(new ProductInfo(s.getPrice(), distance, "10"));

                latList.add(storeProductList.get(storeProductList.indexOf(s)).getStore().getLat());
                lngList.add(storeProductList.get(storeProductList.indexOf(s)).getStore().getLng());


                uniqueList.add(s);
                indexes.add(s.getProdID());
            }
        }
        return uniqueList;
    }


}
