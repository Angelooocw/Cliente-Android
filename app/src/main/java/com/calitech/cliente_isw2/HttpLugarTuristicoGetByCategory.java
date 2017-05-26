package com.calitech.cliente_isw2;

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

public class HttpLugarTuristicoGetByCategory {

    private static final String TAG = HttpHandler.class.getSimpleName();
    public HttpLugarTuristicoGetByCategory() {
    }

    public String makeServiceCall(String reqUrl, String... params) {
        String response = null;
        //String categoria = "Restaurantes y Simil";
        String categoria = params[0];

        Log.e("Make Service Categoria", categoria);

        try {

            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");


            //Composicion string envio
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("categoria","UTF-8") + "=" + URLEncoder.encode(categoria,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            // Termino composicion

            // Recibiendo datos desde el servidor.
            //InputStream inputStream = conn.getInputStream();
            InputStream in = new BufferedInputStream(conn.getInputStream());

            /*
            // Componiendo el string de respuesta
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso8859-1"));
            //String response = "";
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                response += line;
            }
            bufferedReader.close();
            inputStream.close();
            // Termino composicion

            conn.disconnect();
            */
            //return response;
            //Composicion respuesta
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