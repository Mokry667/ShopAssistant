package com.pwr.teamproject.shopassistant;

import android.app.Activity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.R.attr.id;

/**
 * Created by mokry on 08-May-17.
 */

public class ProductAdapter extends ArrayAdapter<String> {

    private static String DEFAULT_CURRENCY = "zł";
    private static String DEFAULT_DISTANCE = "km";

    private Activity context;
    private Integer[] images;
    private String[] productNames;
    private Double[] productPrices;
    private Double[] closestShopDistance;

    public ProductAdapter(Activity context, Integer[] images, String[] productNames, Double[] productPrices, Double[] closestShopDistance) {
        super(context, R.layout.product, productNames);
        this.context = context;
        this.images = images;
        this.productNames = productNames;
        this.productPrices = productPrices;
        this.closestShopDistance = closestShopDistance;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.product, null, true);

        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        TextView productName = (TextView) rowView.findViewById(R.id.productName);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        TextView closestShop = (TextView) rowView.findViewById(R.id.closestShop);

        image.setImageResource(images[position]);
        productName.setText(productNames[position]);
        price.setText(Double.toString(productPrices[position]) + " " + DEFAULT_CURRENCY);
        closestShop.setText(Double.toString(closestShopDistance[position]) + " " + DEFAULT_DISTANCE);


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
