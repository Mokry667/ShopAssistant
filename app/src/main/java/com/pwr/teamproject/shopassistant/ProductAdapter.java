package com.pwr.teamproject.shopassistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by mokry on 08-May-17.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    private static String DEFAULT_CURRENCY = "zł";
    private static String DEFAULT_DISTANCE = "km";

    private static Double DEFAULT_PRICE = 1.0;
    private static Double DEFAULT_CLOSEST_SHOP = 2.0;

    private static int DEFAULT_WIDTH = 225;
    private static int  DEFAULT_HEIGHT = 200;

    private Activity context;

    private ArrayList<Product> products;
    private ArrayList<ProductInfo> productsInfo;

    private String sourceLat;
    private String sourceLng;

    public ProductAdapter(Activity context, ArrayList<Product> products, ArrayList<ProductInfo> productsInfo, String lat, String lng) {
        super(context, R.layout.product, products);
        this.context = context;
        this.products = products;
        this.productsInfo = productsInfo;

        this.sourceLat = lat;
        this.sourceLng = lng;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.product, null, true);

        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        TextView productName = (TextView) rowView.findViewById(R.id.productName);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        TextView shopDistance = (TextView) rowView.findViewById(R.id.shopDistance);
        TextView time = (TextView) rowView.findViewById(R.id.time);


        Product product = products.get(position);
        ProductInfo productInfo = productsInfo.get(position);

        Picasso.with(context)
                .load(product.getPhoto())
                .resize(DEFAULT_WIDTH, DEFAULT_HEIGHT)
                //.centerCrop()
                .into(image);

        productName.setText(product.getName());

        // TO DO
        price.setText(String.valueOf(productInfo.getPrice() + DEFAULT_CURRENCY));
        shopDistance.setText(productInfo.getDistance());
        time.setText(productInfo.getTime());


        // productButton listener
        ImageButton btn = (ImageButton)rowView.findViewById(R.id.productButton);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.product_button_menu);

                //calculate position of the item
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);


                // popup menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.showOnMap:

                                // google maps
                                // TODO test this
                                Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                                        .parse("http://maps.google.com/maps?saddr="
                                                + sourceLat + ","
                                                + sourceLng + "&daddr="
                                                + 51.1211939 + "," + 16.986153));
                                navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                context.startActivity(navigation);

                                Toast.makeText(context, "Show on map clicked for product " + position, Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.addToList:
                                Toast.makeText(context, "add to list clicked for product " + position, Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return true;
                    }
                });


                // some hack to show icons on popup
                try {
                    Field[] fields = popupMenu.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(popupMenu);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper
                                    .getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod(
                                    "setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                popupMenu.show();

            }
        });



        return rowView;
    }


}
