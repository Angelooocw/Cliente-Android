package com.calitech.cliente_isw2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

/**
 * Created by Esteban on 21-06-2017.
 */

 public class AdaptadorComentarios extends RecyclerView.Adapter<AdaptadorComentarios.ViewHolder> {

    private LayoutInflater inflador;
     protected Vector<Comentario_Lugar> vectorcomments;

    public AdaptadorComentarios(Context contexto, Vector<Comentario_Lugar> commen){
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorcomments =commen;
    }

    @Override
    public AdaptadorComentarios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.contenido_comentario,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorComentarios.ViewHolder holder, int position) {
    Comentario_Lugar coment_l = vectorcomments.elementAt(position);
        holder.usuario.setText(coment_l.getUser());
        holder.puntaje.setRating(coment_l.getRSTAR());
        holder.comentario.setText(coment_l.getC());

    }

    @Override
    public int getItemCount() {
        return vectorcomments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public RatingBar puntaje;
        public TextView usuario;
        public TextView comentario;

        public ViewHolder(View itemView) {
            super(itemView);
            puntaje = (RatingBar) itemView.findViewById(R.id.ratinBarComentario);
            usuario = (TextView) itemView.findViewById((R.id.coment_username));
            comentario = (TextView) itemView.findViewById((R.id.texto_comentario));
        }

    }

//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
 //       View v = inflador.inflate(R.layout.e)
   // }
}
