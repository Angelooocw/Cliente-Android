package com.calitech.cliente_isw2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Itinerario extends AppCompatActivity {

    //private String[] paises={"Argentina","Chile","Paraguay","Bolivia","Peru",
      //      "Ecuador","Brasil","Colombia","Venezuela","Uruguay"};
    private ListView lv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerario);

        lv2= (ListView)findViewById(R.id.list2);

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
    }

}
