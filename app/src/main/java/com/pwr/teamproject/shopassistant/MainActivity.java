package com.pwr.teamproject.shopassistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final android.widget.SearchView searchBox = (android.widget.SearchView) findViewById(R.id.SearchID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                String apiQuery = searchBox.getQuery().toString();
                intent.putExtra("searchString", apiQuery);
                startActivity(intent);

               // final JSONResponse jrespons = new JSONResponse(((android.widget.SearchView)findViewById(R.id.SearchID)).getQuery().toString());





            }
        });
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

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

}
