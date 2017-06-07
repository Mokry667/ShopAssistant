package com.pwr.teamproject.shopassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final android.widget.SearchView searchBox = (android.widget.SearchView) findViewById(R.id.SearchID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        final GPSManager gpsManager = new GPSManager(this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermission();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                String apiQuery = searchBox.getQuery().toString();
                intent.putExtra("searchString", apiQuery);

                // for cheapest
                gpsManager.updateLocation();
                double currentLatitude = gpsManager.getLatitude();
                double currentLongitude = gpsManager.getLongitude();

                Log.i("LAT", String.valueOf(currentLatitude));
                Log.i("LNG", String.valueOf(currentLongitude));


                intent.putExtra("lat", String.valueOf(currentLatitude));
                intent.putExtra("lng", String.valueOf(currentLongitude));

                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        recreateDrawer();
        recreateHeader();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int SIGN_IN_CODE = 1;

        if (id == R.id.nav_search_product) {
            // Handle the camera action
        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.nav_stores) {
            Intent intent = new Intent(MainActivity.this, StoreListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_shopping_list) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_sign_in) {
            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), SIGN_IN_CODE);

        } else if (id == R.id.nav_sign_out) {
            SharedPreferences prefs = getSharedPreferences("ShopAssistant", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("signedIn", false);
            editor.putString("username", null);
            editor.commit();
            recreateDrawer();
            recreateHeader();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 1:
                recreateDrawer();
                recreateHeader();
                break;
        }
    }

    // grettings if user is logged in
    public void recreateHeader(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        SharedPreferences prefs = getSharedPreferences("ShopAssistant", MODE_PRIVATE);
        View hView =  navigationView.getHeaderView(0);
        TextView navUsername = (TextView)hView.findViewById(R.id.username);
        boolean isSignedIn = prefs.getBoolean("signedIn", false);
        if(isSignedIn)
        {
            String username = prefs.getString("username", null);
            navUsername.setText("Hello " + username);
        } else
        {
            navUsername.setText(null);
        }
    }

    // separate drawer options if user is logged in
    public void recreateDrawer(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        SharedPreferences prefs = getSharedPreferences("ShopAssistant", MODE_PRIVATE);
        boolean isSignedIn = prefs.getBoolean("signedIn", false);

        if(isSignedIn)
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer_signed_in);
        } else
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

}
