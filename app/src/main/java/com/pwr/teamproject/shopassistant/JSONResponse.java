package com.pwr.teamproject.shopassistant;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Schoe on 5/21/2017.
 */

public class JSONResponse extends AsyncTask<Void, Void, String> {

    private String JSON;
    private String apiQuery;
    public JSONResponse(String query)
    {
            apiQuery = query;
    }
    protected String doInBackground(Void... urls) {
        // Do some validation here

        try {
            URL url = new URL("http://shopassistantapi.azurewebsites.net/api/products?name=" + apiQuery);
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
                JSON = stringBuilder.toString();
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
