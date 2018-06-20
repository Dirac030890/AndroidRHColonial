package com.example.hp.androidrhcolonial;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.hp.androidrhcolonial.Database.Database;
import com.example.hp.androidrhcolonial.Model.Comida;
import com.example.hp.androidrhcolonial.Model.Orden;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ComidaDetalle extends AppCompatActivity {
TextView nombreComida,precioComida,comidaDescripcion;
ImageView imagenComida;
FloatingActionButton btnCarro;
ElegantNumberButton btnNumero;
String comidaId;
Comida comidaActual;
FirebaseDatabase db;
DatabaseReference comidas;
CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_detalle);
        //Conectando con firebase
        db=FirebaseDatabase.getInstance();
        comidas=db.getReference("Comidas");
        //Relacionando botones de la vista
        btnNumero=(ElegantNumberButton)findViewById(R.id.numeroboton);
        btnCarro=(FloatingActionButton)findViewById(R.id.btnCarrito);
        // se crea un escuchador y se crea un objeto database y se envia la in formacion de la vista detalle a la tabla de los producto detalle
        btnCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).agregarACarro(new Orden(
                 comidaId,
                        comidaActual.getNombre(),btnNumero.getNumber(),comidaActual.getPrecio(),comidaActual.getDescuento()
                ));

                Toast.makeText(ComidaDetalle.this,"Agregado a carro",Toast.LENGTH_SHORT).show();
            }
        });


        nombreComida=(TextView)findViewById(R.id.comidanombre) ;
        comidaDescripcion=(TextView)findViewById(R.id.comidadescripcion);
        precioComida=(TextView)findViewById(R.id.comidaprecio);
        imagenComida=(ImageView) findViewById(R.id.img_comida);
        collapsingToolbarLayout =(CollapsingToolbarLayout)findViewById(R.id.colapsar);

        if(getIntent()!=null){
            comidaId=getIntent().getStringExtra("Comida Id");
            if (!comidaId.isEmpty()){
                getDetalleComida(comidaId);
            }
        }
    }


    private void getDetalleComida (String ComidaId){
comidas.child(comidaId).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        comidaActual  =dataSnapshot.getValue(Comida.class);

     //Poner imagen de la comida
       Picasso.with(getBaseContext()).load(comidaActual.getImagen()).into(imagenComida);
  collapsingToolbarLayout.setTitle(comidaActual.getNombre());
       precioComida.setText(comidaActual.getPrecio());
     nombreComida.setText(comidaActual.getNombre());
        comidaDescripcion.setText(comidaActual.getDescripcion());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
    }
}
