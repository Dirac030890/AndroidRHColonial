package com.example.hp.androidrhcolonial.MenuVH;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.androidrhcolonial.Interface.ItemClickListener;
import com.example.hp.androidrhcolonial.R;

/**
 * Created by HP on 05/02/2018.
 */

public class MenuVH extends RecyclerView.ViewHolder implements View.OnClickListener {

     public TextView txtMenuNombre;
     public ImageView imagenVista;
     private ItemClickListener itemClickListener;



    public MenuVH(View itemView) {
        super(itemView);

        txtMenuNombre =(TextView)itemView.findViewById(R.id.menu_nombre);
        imagenVista =(ImageView)itemView.findViewById(R.id.menu_imagen);
itemView.setOnClickListener(this);
    }


public void setItemClickListener(ItemClickListener itemClickListener)
{
    this.itemClickListener=itemClickListener;
}

    @Override
    public void onClick(View view) {
itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
