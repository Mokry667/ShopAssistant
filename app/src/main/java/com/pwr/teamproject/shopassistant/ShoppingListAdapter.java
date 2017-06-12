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
 * Created by mokry on 12-Jun-17.
 */

public class ShoppingListAdapter extends ArrayAdapter<DBProduct> {

    private static String DEFAULT_CURRENCY = "zł";
    private static String DEFAULT_DISTANCE_MEASURE = "km";

    private ArrayList<DBProduct> dbProducts;

    private Activity context;

    private Double srcLat;
    private Double srcLng;

    ShoppingListAdapter(Activity context, ArrayList<DBProduct> dbProducts, String lat, String lng){
        super(context, R.layout.db_product, dbProducts);
        this.context = context;
        this.dbProducts = dbProducts;
        this.srcLat = Double.valueOf(lat);
        this.srcLng = Double.valueOf(lng);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.db_product, null, true);

        TextView productName = (TextView) rowView.findViewById(R.id.productName);
        TextView storeAddress = (TextView) rowView.findViewById(R.id.storeAddress);
        TextView storeDistance = (TextView) rowView.findViewById(R.id.storeDistance);
        TextView price = (TextView) rowView.findViewById(R.id.price);

        DBProduct dbProduct = dbProducts.get(position);

        productName.setText(dbProduct.getName());
        storeAddress.setText(dbProduct.getStoreAddress());

        // could change to google maps api requests
        DecimalFormat df = new DecimalFormat("#.##");
        float[] results = new float[1];
        Location.distanceBetween(srcLat, srcLng, dbProduct.getLat(), dbProduct.getLng(), results);

        Double dbResult = Double.valueOf(results[0] / 1000);
        storeDistance.setText((String.valueOf(df.format(dbResult))) + DEFAULT_DISTANCE_MEASURE);

        price.setText(String.valueOf(dbProduct.getPrice()) + DEFAULT_CURRENCY);

        Button mapBtn = (Button)rowView.findViewById(R.id.showOnMap);
        Button rmvBtn = (Button)rowView.findViewById(R.id.removeFromList);

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
                                + String.valueOf(dbProducts.get(position).getLat()) + "," + String.valueOf(dbProducts.get(position).getLng())));

                Log.d("POSITION", String.valueOf(position));
                Log.d("LAT:", String.valueOf(dbProducts.get(position).getLat()));
                Log.d("LNG", String.valueOf(dbProducts.get(position).getLng()));
                navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(navigation);

            }
        });

        rmvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calculate position of the item

                View parentRow = (View) v.getParent().getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);

                DBManager dbManager = new DBManager(context);
                dbManager.deleteDBProduct(dbProducts.get(position));
                ((ShoppingListActivity)context).refreshAdapter();

                Log.d("Product deleted : ", String.valueOf(position));



            }
        });

        return rowView;
    }

}
