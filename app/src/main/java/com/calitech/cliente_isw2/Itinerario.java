package com.calitech.cliente_isw2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Itinerario extends AppCompatActivity {

    //private String[] paises={"Argentina","Chile","Paraguay","Bolivia","Peru",
      //      "Ecuador","Brasil","Colombia","Venezuela","Uruguay"};
    private ListView lv2;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String url2 = "http://10.0.2.2/proyectosxampp/isw2Api/v1/itinerarios";
    ArrayList<HashMap<String, String>> listaItinerarios;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerario);

        listView = (ListView) findViewById(R.id.list);
        //lv2= (ListView)findViewById(R.id.list2);
        listaItinerarios = new ArrayList<>();
        //ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, paises);
        //lv2.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Compartir Itinerario", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new GetUserItinerarios().execute("joaquin");

    }

    private class GetUserItinerarios extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Itinerario.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg0) {
            //HttpHandler sh = new HttpHandler();

            HttpGetItinerarios sh = new HttpGetItinerarios();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(url2, arg0);
            Log.v("SERVER_RESPONSE",jsonStr);

            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("itinerarios");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String itinerario_nombre = c.getString("nombre");
                        String itinerario_fecha = c.getString("fecha");
                        String itinerario_descripcion = c.getString("descripcion");

                        // tmp hash map for single contact
                        HashMap<String, String> itinerario = new HashMap<>();

                        // adding each child node to HashMap key => value
                        itinerario.put("itinerario_nombre", itinerario_nombre);
                        itinerario.put("itinerario_fecha", itinerario_fecha);
                        itinerario.put("itinerario_descripcion", itinerario_descripcion);
                        // adding contact to contact list
                        //listaItinerarios.add(contact);
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
                    Itinerario.this, listaItinerarios,
                    R.layout.itinerario, new String[]{
                    "lugar_nombre",
                    "lugar_comuna",
                    "lugar_empresario",
                    "lugar_descripcion",
                    "lugar_ubicacion"}, new int[]{
                    R.id.txt_iti_nombre,
                    R.id.txt_lugar_comuna,
                    R.id.txt_lugar_empresario,
                    R.id.txt_lugar_descripcion,
                    R.id.txt_lugar_ubicacion});

            listView.setAdapter(adapter);
        }

    }

}
