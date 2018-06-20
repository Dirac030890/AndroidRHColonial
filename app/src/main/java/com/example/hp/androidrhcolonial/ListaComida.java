package com.example.hp.androidrhcolonial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.androidrhcolonial.Interface.ItemClickListener;
import com.example.hp.androidrhcolonial.MenuVH.ComidaVH;
import com.example.hp.androidrhcolonial.Model.Comida;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListaComida extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference comidalista;
    String categoriaId="";
FirebaseRecyclerAdapter<Comida,ComidaVH> adaptador;
    RecyclerView reciclador_comida;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Comida,ComidaVH> busquedaadaptador;
    List<String> sLista=new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    ////funcionalidad de busqueda
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comida);

//Conectando a firebase
        db=FirebaseDatabase.getInstance();
        comidalista=db.getReference("Comidas");

        reciclador_comida=(RecyclerView)findViewById(R.id.reciclador_comida);
        reciclador_comida.setHasFixedSize(true);
      layoutManager=new LinearLayoutManager(this);
        reciclador_comida.setLayoutManager(layoutManager);
if(getIntent()!=null)
    categoriaId=getIntent().getStringExtra("Categoria Id");
if(!categoriaId.isEmpty()&&categoriaId!=null){
    loadComidaLista(categoriaId);

}
materialSearchBar=(MaterialSearchBar)findViewById(R.id.barrabusqueda);
materialSearchBar.setHint("Ingresa nombre de comida");
//materialSearchBar.setSpeechMode(false);
cargarSugerencia();

// baarra de busqueda
materialSearchBar.setLastSuggestions(sLista);
materialSearchBar.setCardViewElevation(10);
materialSearchBar.addTextChangeListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//se utiliza la lista creada

        List <String> sugerencia=new ArrayList<String>();

        //se busca mediante un for
        for (String search:sLista){
            if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                sugerencia.add(search);
        }
        materialSearchBar.setLastSuggestions(sugerencia);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});

materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
    @Override
    public void onSearchStateChanged(boolean enabled) {
        if(!enabled)
            reciclador_comida.setAdapter(adaptador);
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
iniciarBusqueda(text);
    }

    private void iniciarBusqueda(CharSequence text) {
        busquedaadaptador=new FirebaseRecyclerAdapter<Comida, ComidaVH>(Comida.class,R.layout.comida_item,ComidaVH.class,
                comidalista.orderByChild("Nombre").equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(ComidaVH viewHolder, Comida model, int position) {

                viewHolder.comida_nombre.setText(model.getNombre());
                Picasso.with(getBaseContext()).load((model.getImagen())).into(viewHolder.comida_imagen);
                final Comida local=model;
///Se envia las
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int posicion, boolean islongClick) {

                        Intent De=new Intent(ListaComida.this, ComidaDetalle.class);
                        De.putExtra("Comida Id",busquedaadaptador.getRef(posicion).getKey());
                        startActivity(De);
                    }
                });
            }
        };
        reciclador_comida.setAdapter(busquedaadaptador);
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
});
    }
private void cargarSugerencia(){/// se crea para crrar sugerencias desde firebase
        comidalista.orderByChild("MenuId").equalTo(categoriaId).addListenerForSingleValueEvent(new ValueEventListener(){

                    public void onDataChange(DataSnapshot dataSnapshot){
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Comida articulo =postSnapshot.getValue(Comida.class);
                            sLista.add(articulo.getNombre());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


}








    private void loadComidaLista(String categoriaId) {
        //aqui se relaciona la categoriaId Con MenuId como si selecccionara todos los  Select * From where menuId=
        adaptador=new FirebaseRecyclerAdapter<Comida, ComidaVH>(Comida.class,R.layout.comida_item,ComidaVH.class,
                comidalista.orderByChild("MenuId")
                .equalTo(categoriaId)) {
            @Override
            protected void populateViewHolder(ComidaVH viewHolder, Comida model, int posicion) {
viewHolder.comida_nombre.setText(model.getNombre());
                Picasso.with(getBaseContext()).load((model.getImagen())).into(viewHolder.comida_imagen);
                final Comida local=model;
///Se envia las
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int posicion, boolean islongClick) {

                        Intent De=new Intent(ListaComida.this, ComidaDetalle.class);
                        De.putExtra("Comida Id",adaptador.getRef(posicion).getKey());
                        startActivity(De);
                    }
                });            }
        };

        reciclador_comida.setAdapter(adaptador);
    }
}
