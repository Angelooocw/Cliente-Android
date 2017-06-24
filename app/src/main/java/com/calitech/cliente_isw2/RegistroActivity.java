package com.calitech.cliente_isw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistroActivity extends AppCompatActivity {

    private EditText edtNombre, edtUsuario,edtClave,edtClaveConfirmacion,edtCorreo;
    String nombre, usuario, clave, claveConfirmacion, correo;
    private Button btnRegistrationComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtNombre = (EditText) findViewById(R.id.edtRegistroNombre);
        edtUsuario = (EditText) findViewById(R.id.edtRegistroUsuario);
        edtClave = (EditText) findViewById(R.id.edtRegistroClave);
        edtClaveConfirmacion = (EditText) findViewById(R.id.edtRegistroClaveConfirmacion);
        edtCorreo = (EditText) findViewById(R.id.edtRegistroCorreo);

        btnRegistrationComplete = (Button) findViewById(R.id.btnResgitroCompletar);
        btnRegistrationComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistration(v);
            }
        });
    }

    public void UserRegistration(View view){
        nombre = edtNombre.getText().toString();
        usuario = edtNombre.getText().toString();
        clave = edtNombre.getText().toString();
        claveConfirmacion = edtNombre.getText().toString();
        correo = edtNombre.getText().toString();

        String method = "register";
        HttpLoginRegistro backgroundTask = new HttpLoginRegistro(this);
        backgroundTask.execute(method, nombre, usuario, clave, correo);

        finish();
    }

}
