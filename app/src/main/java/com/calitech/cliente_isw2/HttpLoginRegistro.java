package com.calitech.cliente_isw2;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jquin on 30-04-2017.
 */

public class HttpLoginRegistro extends AsyncTask<String, Void, String> {
    private AlertDialog alertDialog;
    private Context ctx;

    public HttpLoginRegistro(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        //Se deben cambiar las URLs actualmente estan conectadas al localhost.
        String method = params[0];

        if(method.equals("register")){
            return UserRegisterToDB(params);
        }else if(method == "login"){
            return UserLoginToDB(params);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information....");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Resgitration Success...")){
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }else{
            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }

    private String UserRegisterToDB(String... params){
        String registro_url = "http://10.0.2.2/ProyectosXampp/ClienteIsw2Services/cliente_registro.php";
        String nombre = params[1];
        String usuario = params[2];
        String clave = params[3];
        String correo = params[4];

        try {
            URL url = new URL(registro_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("nombre","UTF-8") +
                    "=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&" + URLEncoder.encode("usuario","UTF-8") +
                    "=" + URLEncoder.encode(usuario, "UTF-8")
                    + "&" + URLEncoder.encode("clave","UTF-8") +
                    "=" + URLEncoder.encode(clave, "UTF-8")
                    + "&" + URLEncoder.encode("correo","UTF-8") +
                    "=" + URLEncoder.encode(correo, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            IS.close();
            return "Resgitration Success...";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "There was a problem with the registration";
    }
    private String UserLoginToDB(String... params){
        String login_url = "http://10.0.2.2/ProyectosXampp/ClienteIsw2Services/cliente_login.php";
        String correo = params[1];
        String clave = params[2];

        try {
            //Mandando datos al servidor
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();

            //Composicion string envio
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("login_correo","UTF-8") + "=" + URLEncoder.encode(correo,"UTF-8")
                    + "&"+ URLEncoder.encode("login_clave","UTF-8") + "=" + URLEncoder.encode(clave,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            // Termino composicion

            // Recibiendo datos desde el servidor.
            InputStream inputStream = httpURLConnection.getInputStream();

            // Componiendo el string de respuesta
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso8859-1"));
            String response = "";
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                response += line;
            }
            bufferedReader.close();
            inputStream.close();
            // Termino composicion

            httpURLConnection.disconnect();
            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "There was a problem with the login";
    }
}
