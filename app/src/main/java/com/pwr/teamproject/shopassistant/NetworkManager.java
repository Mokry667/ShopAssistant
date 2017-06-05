package com.pwr.teamproject.shopassistant;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mokry on 21-May-17.
 */

public class NetworkManager {

    private String API_URL = null;
    private String JSON;

    public NetworkManager()
    {
        API_URL = "http://shopassistantapi.azurewebsites.net/api/";
    }

    public boolean isAccountValid(String username, String password){
        try {
            URL url = new URL(API_URL + "users?login=" + username + "&pass=" + password);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            // if response is 200 account exist
            if(responseCode == 200){
                return true;
            }
            else return false;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return false;
        }
    }

    protected String getFromAPI(String request) {
        try {
            URL url = new URL( API_URL + request);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.d("JSON", stringBuilder.toString());
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected String getProducts(String productName) {

        String request = "products?name=";
        productName = productName.replace(" ", "%20");

        return getFromAPI(request + productName);
    }

    protected String getProductsCheapest(String productName, String lat, String lng) {

        String request = "products?name=;";
        productName = productName.replace(" ", "%20");
        String locationRequest = "&action=cheapest";
        String latRequest = "&lat=";
        String lngRequest = "&lng=";

        return getFromAPI(request + productName + locationRequest + latRequest + lat + lngRequest + lng);
    }

    protected String getStoreProducts(String productName) {

        String request = "storeproducts?name=";
        productName = productName.replace(" ", "%20");

        return getFromAPI(request + productName);
    }

    protected String getStores() {

        String request = "stores";
        return getFromAPI(request);

    }

    public String getJSON(){
        return JSON;
    }
}
