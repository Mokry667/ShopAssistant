package com.pwr.teamproject.shopassistant;

import android.app.Activity;
import android.util.Log;
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

    public StoreAdapter(Activity context, ArrayList<Store> stores) {
        super(context, R.layout.product, stores);
        this.context = context;
        this.stores = stores;
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
                Log.d("CLICKED", String.valueOf(position));
                //Toast.makeText(context, "Store " + position + " CLICKED", Toast.LENGTH_SHORT);

            }
        });

        return rowView;
    }

}
