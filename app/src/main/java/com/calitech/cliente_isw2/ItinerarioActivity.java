package com.calitech.cliente_isw2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ItinerarioActivity extends AppCompatActivity {

    private String TAG = LoginActivity.class.getSimpleName();
    private static String urlItinerariosGet = "http://10.0.2.2/proyectosxampp/isw2Api/v1/itinerarios";
    private static String urlItinerariosAdd = "http://10.0.2.2/proyectosxampp/isw2Api/v1/single_itinerario";

    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> listaItinerarios;
    private ListView listViewItinerarios;
    private EditText edtItinerarioNombre, edtItinerarioDescripcion;
    private Button btnItinerarioAdd;

    GetUserItinerarios userItinerarios = new GetUserItinerarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerario);

        listViewItinerarios = (ListView) findViewById(R.id.listViewItinerarios);
        listaItinerarios = new ArrayList<>();

        edtItinerarioNombre = (EditText) findViewById(R.id.edtItinerarioNombre);
        edtItinerarioDescripcion = (EditText) findViewById(R.id.edtItinerarioDescripcion);

        btnItinerarioAdd = (Button) findViewById(R.id.btnItinerarioAgregar);
        btnItinerarioAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtItinerarioNombre.getText().toString();
                String descripcion = edtItinerarioDescripcion.getText().toString();
                edtItinerarioDescripcion.setText("");
                edtItinerarioNombre.setText("");
                new AddItinerario().execute(nombre,descripcion);
                //userItinerarios.execute("joaquin");
            }
        });
        userItinerarios.execute("joaquin");

    }

    private class GetUserItinerarios extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ItinerarioActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg0) {
            //HttpHandler sh = new HttpHandler();

            HttpItinerariosGet sh = new HttpItinerariosGet();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(urlItinerariosGet, arg0);
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
                        listaItinerarios.add(itinerario);
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
                    ItinerarioActivity.this, listaItinerarios,
                    R.layout.single_itinerario, new String[]{
                    "itinerario_nombre",
                    "itinerario_descripcion",
                    "itinerario_fecha"
                        }, new int[]{
                        R.id.txt_iti_nombre,
                        R.id.txt_iti_descripcion,
                        R.id.txt_iti_fecha});

            listViewItinerarios.setAdapter(adapter);

        }

    }

    private class AddItinerario extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ItinerarioActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... arg0) {
            //HttpHandler sh = new HttpHandler();

            HttpItinerariosAdd sh = new HttpItinerariosAdd();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(urlItinerariosAdd, arg0);
            Log.e("SERVER_RESPONSE",jsonStr);

            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String s = jsonObj.getString("message");
                    Log.e("message",s);

                    /*
                    Toast.makeText(getApplicationContext(),
                            "Message: " + s,
                            Toast.LENGTH_LONG)
                            .show();
*/


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
                    ItinerarioActivity.this, listaItinerarios,
                    R.layout.single_itinerario, new String[]{
                    "itinerario_nombre",
                    "itinerario_descripcion",
                    "itinerario_fecha"
            }, new int[]{
                    R.id.txt_iti_nombre,
                    R.id.txt_iti_descripcion,
                    R.id.txt_iti_fecha});

            listViewItinerarios.setAdapter(adapter);

        }

    }

}
