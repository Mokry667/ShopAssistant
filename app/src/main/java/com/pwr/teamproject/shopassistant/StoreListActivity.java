package com.pwr.teamproject.shopassistant;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class StoreListActivity extends AppCompatActivity {

    ListView myListView;

    ArrayList<Store> storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StoreFetchTask fetchTask = new StoreListActivity.StoreFetchTask();
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

    public class StoreFetchTask extends AsyncTask<Void, Void, String> {

        StoreFetchTask() {}

        protected String doInBackground(Void... params) {

            NetworkManager networkManager = new NetworkManager();
            return networkManager.getStores();

        }

        protected void onPostExecute(final String JSON) {

            storeList = new ArrayList<>();

            try {
                if(JSON != null){
                    JSONArray array = (JSONArray) new JSONTokener(JSON).nextValue();
                    Log.d("objectPOTATO", array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        storeList.add(new Store(object.getInt("Id"), object.getString("Name"), object.getString("Address"), object.getDouble("Lat"), object.getDouble("Lng")));
                    }
                }
            } catch (JSONException e) {
                // Appropriate error handling code
                Log.d("objectPOTATO", "AdrianPotato");
            }

            StoreAdapter storeAdapter = new StoreAdapter(StoreListActivity.this, storeList);
            myListView = (ListView) findViewById(R.id.myListView);
            myListView.setAdapter(storeAdapter);

        }



    }

}
