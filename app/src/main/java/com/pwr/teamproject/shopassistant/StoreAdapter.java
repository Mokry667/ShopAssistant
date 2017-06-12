package com.pwr.teamproject.shopassistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mokry on 04-Jun-17.
 */

public class StoreAdapter extends ArrayAdapter<Store> {

    private Activity context;
    private ArrayList<Store> stores;

    private Double srcLat;
    private Double srcLng;

    public StoreAdapter(Activity context, ArrayList<Store> stores, String lat, String lng) {
        super(context, R.layout.product, stores);
        this.context = context;
        this.stores = stores;

        this.srcLat = Double.valueOf(lat);
        this.srcLng = Double.valueOf(lng);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.store, null, true);

        TextView storeName = (TextView) rowView.findViewById(R.id.storeName);
        TextView storeAddress = (TextView) rowView.findViewById(R.id.storeAddress);
        Button showOnMap = (Button) rowView.findViewById(R.id.showOnMap);


        Store store = stores.get(position);

        storeName.setText(store.getName());
        storeAddress.setText(store.getAddress());


        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calculate position of the item
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);

                Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("http://maps.google.com/maps?saddr="
                                + srcLat + ","
                                + srcLng + "&daddr="
                                + String.valueOf(stores.get(position).getLat()) + "," + String.valueOf(stores.get(position).getLng())));
                navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(navigation);

            }
        });

        return rowView;
    }

}
