package com.calitech.cliente_isw2;

import android.widget.RatingBar;

/**
 * Created by Esteban on 21-06-2017.
 */

public class ComentarioLugar {
    private String user;
    private float puntaje;
    private String comentario;


    public ComentarioLugar(String user, float puntaje, String comentario){
        this.user= user;
        this.puntaje = puntaje;
        this.comentario = comentario;
    }

    public String getUser(){
        return user;
    }
    public String getC(){
        return comentario;
    }
    public float getRSTAR(){
        return puntaje;
    }
}
