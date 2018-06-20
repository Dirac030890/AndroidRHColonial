package com.example.hp.androidrhcolonial.MenuVH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.androidrhcolonial.Interface.ItemClickListener;
import com.example.hp.androidrhcolonial.R;

/**
 * Created by HP on 06/02/2018.
 */
// Se crea esta funcion para aparecer el menu con las imagenes de comidas
public class ComidaVH extends RecyclerView.ViewHolder implements View.OnClickListener{
    //se Crean los controles para observar la informacion de la comida como imagen e informacion
    public TextView comida_nombre;
    public ImageView comida_imagen;
private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // se crea para capturar los click en el menu

    public ComidaVH(View itemView) {
        super(itemView);
// Se enlazan los controles de la vista para programarlos
        comida_nombre =(TextView)itemView.findViewById(R.id.comida_nombre);
        comida_imagen =(ImageView)itemView.findViewById(R.id.comida_imagen);

        //se asigna el evento a este activity
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Se crea para obtener el indice o localizacion de donde se dio click en que item
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
