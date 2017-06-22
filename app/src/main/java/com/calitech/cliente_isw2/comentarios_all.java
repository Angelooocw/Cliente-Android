package com.calitech.cliente_isw2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;

import java.util.Vector;

/**
 * Created by Esteban on 21-06-2017.
 */

public class comentarios_all extends AppCompatActivity {

    private RecyclerView rvw;
    private RecyclerView.LayoutManager layout_m; //Definir manejador de layouts

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.box_comentarios);
        rvw = (RecyclerView) findViewById(R.id.indv_comentarios);

        Vector<Comentario_Lugar> coment = new Vector<Comentario_Lugar>();


        coment.add(new Comentario_Lugar("Juanito", (float)4.5,"El lugar es bonito y kawai"));
        coment.add(new Comentario_Lugar("Juanito2", (float)3.5,"No hay piscina"));
        coment.add(new Comentario_Lugar("Juanito2", (float)2.5,"Pesimos lugares de comida. Casi no hay mesas. Le falta una limpieza a la piscina"));

        rvw.setAdapter(new AdaptadorComentarios(this,coment));
        layout_m = new LinearLayoutManager(this);
        rvw.setLayoutManager(layout_m);


    }
}