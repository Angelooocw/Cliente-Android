package com.calitech.cliente_isw2;

/**
 * Created by jquin on 26-05-2017.
 */

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class HttpItinerariosAdd {

    private static final String TAG = HttpHandler.class.getSimpleName();
    public HttpItinerariosAdd() {
    }

    public String makeServiceCall(String reqUrl, String... params) {


        String response = "";
        String userid = "joaquin";
        String itiNombre = params[0];
        String itiDescripcion = params[1];

        Log.e("Make Service AddIt: ", userid + " " + itiNombre + " " + itiDescripcion);

        try {

            URL url = new URL(reqUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //Composicion string envio
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("userid","UTF-8") + "=" + URLEncoder.encode(userid,"UTF-8")
                    + "&"+ URLEncoder.encode("descripcion","UTF-8") + "=" + URLEncoder.encode(itiDescripcion,"UTF-8")
                    + "&"+ URLEncoder.encode("nombre_itinerario","UTF-8") + "=" + URLEncoder.encode(itiNombre,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            // Termino composicion

            // Recibiendo datos desde el servidor.
            //InputStream inputStream = conn.getInputStream();
            InputStream in = new BufferedInputStream(conn.getInputStream());

            response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return response;
    }



    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            //Solucion parche
            line = reader.readLine();
            int i =0;
            while ((line = reader.readLine()) != null && (line != "DbConnect.php")) {

                Log.v("line", line);
                if(i>0){
                    sb.append(line).append('\n');
                    Log.v("line", line);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}