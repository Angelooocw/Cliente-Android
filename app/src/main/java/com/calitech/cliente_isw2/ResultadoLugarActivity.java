package com.calitech.cliente_isw2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultadoLugarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView listView;
    private TextView dispSearchingBy;

    // URL to get contacts JSON
    //private static String url = "http://api.androidhive.info/contacts/";
    private static String url = "http://10.0.2.2/px/isw2Api/v1/lugares_turisticos";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_lugar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        dispSearchingBy = (TextView) findViewById(R.id.dispSearchingBy);

        setSupportActionBar(toolbar);
        contactList = new ArrayList<>();

        // get intent content
        Intent in = getIntent();
        String categoria = in.getStringExtra("Categoria");
        ///////////////////////////////

        dispSearchingBy.setText("Buscando por: " + categoria);

        listView = (ListView) findViewById(R.id.list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Añadir al single_itinerario", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //new GetContacts().execute();
        new GetLugaresByCategoria().execute(categoria);
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
        getMenuInflater().inflate(R.menu.lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
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

        if (id == R.id.perfil) {


        } else if (id == R.id.ajustes) {

        }
        else if (id == R.id.Cabaña_lugar) {
            startActivity(new Intent(this, ResultadoLugarActivity.class));

        }
        else if (id == R.id.Hotel_lugar) {
            startActivity(new Intent(this, ResultadoLugarActivity.class));

        }
        else if (id == R.id.Camping_lugar) {
            startActivity(new Intent(this, ResultadoLugarActivity.class));

        }
        else if (id == R.id.Piscina_Lugar) {
            startActivity(new Intent(this, HomeActivity.class));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ResultadoLugarActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //HttpHandler sh = new HttpHandler();
            HttpLugarTuristicoGetByCategory sh = new HttpLugarTuristicoGetByCategory();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(url);
           if (jsonStr != null) {

               try {
                  JSONObject jsonObj = new JSONObject(jsonStr);
                  // Getting JSON Array node
                  JSONArray contacts = jsonObj.getJSONArray("tasks");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String lugar_nombre = c.getString("nombre");
                        String lugar_comuna = c.getString("comuna");
                        String lugar_descripcion = c.getString("descripcion");
                        String lugar_empresario = c.getString("rut_empresario");
                        String lugar_latitud = c.getString("lat");
                        String lugar_sello = c.getString("selloQ");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("lugar_nombre", lugar_nombre);
                        contact.put("lugar_comuna", lugar_comuna);
                        contact.put("lugar_descripcion", lugar_descripcion);
                        contact.put("lugar_empresario", lugar_empresario);
                        contact.put("lugar_latitud", lugar_latitud);
                        contact.put("lugar_sello", lugar_sello);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ResultadoLugarActivity.this, contactList,
                    R.layout.lugar, new String[]{
                        "lugar_nombre",
                        "lugar_comuna",
                        "lugar_empresario",
                        "lugar_descripcion",
                        "lugar_latitud"}, new int[]{
                            R.id.txt_iti_nombre,
                            R.id.txt_lugar_comuna,
                            R.id.txt_lugar_empresario,
                            R.id.txt_lugar_descripcion,
                            //R.id.txt_lugar_ubicacion
                     });

            listView.setAdapter(adapter);
        }

    }

    private class GetLugaresByCategoria extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ResultadoLugarActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg0) {
            //HttpHandler sh = new HttpHandler();
            HttpLugarTuristicoGetByCategory sh = new HttpLugarTuristicoGetByCategory();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(url, arg0);
            Log.e("SERVER_RESPONSE",jsonStr);

           if (jsonStr != null) {

               try {
                  JSONObject jsonObj = new JSONObject(jsonStr);
                  // Getting JSON Array node
                  JSONArray contacts = jsonObj.getJSONArray("lugares_turisticos");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String lugar_nombre = c.getString("nombre");
                        String lugar_comuna = c.getString("comuna");
                        String lugar_descripcion = c.getString("descripcion");
                        String lugar_empresario = c.getString("rut_empresario");
                        String lugar_latitud = c.getString("lat");
                        String lugar_sello = c.getString("selloQ");

                        // tmp hash map for single contact
                        HashMap<String, String> lugar = new HashMap<>();

                        // adding each child node to HashMap key => value
                        lugar.put("lugar_nombre", lugar_nombre);
                        lugar.put("lugar_comuna", lugar_comuna);
                        lugar.put("lugar_descripcion", lugar_descripcion);
                        lugar.put("lugar_empresario", lugar_empresario);
                        lugar.put("lugar_ubicacion", lugar_latitud);
                        lugar.put("lugar_sello", lugar_sello);

                        // adding contact to contact list
                        contactList.add(lugar);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ResultadoLugarActivity.this, contactList,
                    R.layout.lugar, new String[]{
                    "lugar_nombre",
                    "lugar_comuna",
                    "lugar_empresario",
                    "lugar_descripcion",
                    "lugar_latitud"}, new int[]{
                    R.id.txt_iti_nombre,
                    R.id.txt_lugar_comuna,
                    R.id.txt_lugar_empresario,
                    R.id.txt_lugar_descripcion,
                    //R.id.txt_lugar_ubicacion
                    });

            listView.setAdapter(adapter);
        }

    }
}
