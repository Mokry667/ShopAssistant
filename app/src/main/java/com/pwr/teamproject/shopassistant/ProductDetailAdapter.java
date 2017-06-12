package com.pwr.teamproject.shopassistant;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by mokry on 22-May-17.
 */

public class ProductDetailAdapter extends ArrayAdapter<StoreProduct> {

    private static Double DEFAULT_DISTANCE = 10.0;

    private static String DEFAULT_CURRENCY = "zł";
    private static String DEFAULT_DISTANCE_MEASURE = "km";

    private Activity context;

    //private ArrayList<StoreProduct> storeProducts;

    private ArrayList<String> storeNames;
    private ArrayList<String> storeAddresses;
    private ArrayList<String> storeDistances;
    private ArrayList<Double> prices;

    private ArrayList<Double> storeLat;
    private ArrayList<Double> storeLng;

    private Double srcLat;
    private Double srcLng;

    ProductDetailAdapter(Activity context, ArrayList<StoreProduct> storeProducts, String lat, String lng){
        super(context, R.layout.store_product, storeProducts);
        this.context = context;

        // used for adding to database
        // could use some optimization
        //storeProducts = new ArrayList<>();


        storeNames = new ArrayList<>();
        storeAddresses = new ArrayList<>();
        storeDistances = new ArrayList<>();
        prices = new ArrayList<>();

        storeLat = new ArrayList<>();
        storeLng = new ArrayList<>();

        this.srcLat = Double.valueOf(lat);
        this.srcLng = Double.valueOf(lng);

        // temporary solution
        DecimalFormat df = new DecimalFormat("#.##");

        for(StoreProduct s : storeProducts){
            //storeProducts.add(s);

            this.storeNames.add(s.getStore().getName());
            this.storeAddresses.add(s.getStore().getAddress());

            // could change to google maps api requests
            float[] results = new float[1];
            Location.distanceBetween(srcLat, srcLng, s.getStore().getLat(), s.getStore().getLng(), results);

            this.storeLat.add(s.getStore().getLat());
            this.storeLng.add(s.getStore().getLng());

            Double dbResult = Double.valueOf(results[0] / 1000);

            this.storeDistances.add(String.valueOf(df.format(dbResult)));
            this.prices.add(s.getPrice());
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.store_product, null, true);

        TextView storeName = (TextView) rowView.findViewById(R.id.storeName);
        TextView storeAddress = (TextView) rowView.findViewById(R.id.storeAddress);
        TextView storeDistance = (TextView) rowView.findViewById(R.id.storeDistance);
        TextView price = (TextView) rowView.findViewById(R.id.price);

        storeName.setText(storeNames.get(position));
        storeAddress.setText(storeAddresses.get(position));
        storeDistance.setText(storeDistances.get(position).toString() + DEFAULT_DISTANCE_MEASURE);
        price.setText(prices.get(position).toString() + DEFAULT_CURRENCY);

        Button mapBtn = (Button)rowView.findViewById(R.id.showOnMap);
        Button addBtn = (Button)rowView.findViewById(R.id.addToList);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calculate position of the item

                View parentRow = (View) v.getParent().getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);


                // - 1 for header
                Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("http://maps.google.com/maps?saddr="
                                + srcLat + ","
                                + srcLng + "&daddr="
                                + String.valueOf(storeLat.get(position - 1)) + "," + String.valueOf(storeLng.get(position - 1))));

                Log.d("POSITION", String.valueOf(position - 1));
                Log.d("LAT:", String.valueOf(storeLat.get(position - 1)));
                Log.d("LNG", String.valueOf(storeLng.get(position - 1)));
                navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(navigation);

                }
        });

        /*
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calculate position of the item

                View parentRow = (View) v.getParent().getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);


                // - 1 for header
                DBManager dbManager = new DBManager(context);
                dbManager.addDBProduct(storeProducts.get(position - 1), storeProducts.get(position - 1).getStore());
                Log.d("SUCCESS", "PRODUCT ADDED TO DB");

            }
        });
        */

        return rowView;
    }
}
