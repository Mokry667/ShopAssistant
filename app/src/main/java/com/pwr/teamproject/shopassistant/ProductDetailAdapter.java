package com.pwr.teamproject.shopassistant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mokry on 22-May-17.
 */

public class ProductDetailAdapter extends ArrayAdapter<StoreProduct> {

    private static Double DEFAULT_DISTANCE = 10.0;

    private static String DEFAULT_CURRENCY = "zł";
    private static String DEFAULT_DISTANCE_MEASURE = "km";

    private Activity context;

    private ArrayList<String> storeNames;
    private ArrayList<String> storeAddresses;
    private ArrayList<Double> storeDistances;
    private ArrayList<Double> prices;

    ProductDetailAdapter(Activity context, ArrayList<StoreProduct> storeProducts){
        super(context, R.layout.store_product, storeProducts);
        this.context = context;

        storeNames = new ArrayList<>();
        storeAddresses = new ArrayList<>();
        storeDistances = new ArrayList<>();
        prices = new ArrayList<>();


        for(StoreProduct s : storeProducts){
            /*
            this.storeNames.add("STORE");
            this.storeAddresses.add("ADDRESS");
            this.storeDistances.add(10.0);
            this.prices.add(10.0);
            */
            this.storeNames.add(s.getStore().getName());
            this.storeAddresses.add(s.getStore().getAddress());
            this.storeDistances.add(DEFAULT_DISTANCE);
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


        return rowView;
    }
}
