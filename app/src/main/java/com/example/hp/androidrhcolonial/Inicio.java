package com.example.hp.androidrhcolonial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.androidrhcolonial.Comun.Comun;
import com.example.hp.androidrhcolonial.Interface.ItemClickListener;
import com.example.hp.androidrhcolonial.MenuVH.MenuVH;
import com.example.hp.androidrhcolonial.Model.Categoria;
import com.example.hp.androidrhcolonial.Model.Comida;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

FirebaseDatabase database;
DatabaseReference categoria;
TextView txtNombreCompleto;
RecyclerView reciclador_menu;
RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Categoria,MenuVH> adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Iniciando Firebase
        database=FirebaseDatabase.getInstance();
        categoria=database.getReference("Categoria");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent carro=new Intent(Inicio.this,Carro.class);
               startActivity(carro);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Poner Nombre
        View vistaCabecera =navigationView.getHeaderView(0);
        txtNombreCompleto=(TextView)vistaCabecera.findViewById(R.id.txtNombreCompleto);
      txtNombreCompleto.setText(Comun.usuarioActual.getNombre());
        //Cargar Imagnes
        reciclador_menu=(RecyclerView)findViewById(R.id.reciclador_menu);
        reciclador_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        reciclador_menu.setLayoutManager(layoutManager);
        loadMenu();

    }

    private void loadMenu() {
         adaptador =new FirebaseRecyclerAdapter<Categoria,MenuVH>(Categoria.class,R.layout.menu_item,MenuVH.class,categoria)
        {
         protected  void populateViewHolder (MenuVH viewholder, Categoria model,int posicion){

             viewholder.txtMenuNombre.setText(model.getNombre());
             Picasso.with(getBaseContext()).load(model.getImagen()).into(viewholder.imagenVista);
final Categoria clickitem=model;
viewholder.setItemClickListener(new ItemClickListener() {
    @Override
    public void onClick(View view, int posicion, boolean islongClick) {
        Toast.makeText(Inicio.this,""+clickitem.getNombre(),Toast.LENGTH_SHORT).show();
        //Se consigue la categoria por id para enviar el ativity relacionado
        Intent comidalista =new Intent(Inicio.this, ListaComida .class);
        // Por que la categoria nos da las comidas que se deven de apreciar en ele menu comida lista
        comidalista.putExtra("Categoria Id",adaptador.getRef(posicion).getKey());
        startActivity(comidalista);
    }
});

         }


        };reciclador_menu.setAdapter(adaptador);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

     if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_carta) {

         Intent carro=new Intent(Inicio.this,Carro.class);
         startActivity(carro);

        } else if (id == R.id.nav_ordenes) {
         Intent carro=new Intent(Inicio.this,OrdenEstatus.class);
         startActivity(carro);

        } else if (id == R.id.nav_Salir) {
Intent signIn =new Intent(Inicio.this,SignIn.class);
         signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(signIn);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
