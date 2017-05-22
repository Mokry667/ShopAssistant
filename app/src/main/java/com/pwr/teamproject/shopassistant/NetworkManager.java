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

    protected String getProducts(String productName) {
        // Do some validation here

        try {
            URL url = new URL( API_URL + "products?name=" + productName);
            //URL url = new URL("http://shopassistantapi.azurewebsites.net/api/storeproducts?name=" + apiQuery);
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

    public String getJSON(){
        return JSON;
    }
}
