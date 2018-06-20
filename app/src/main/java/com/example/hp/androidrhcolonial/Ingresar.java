package com.example.hp.androidrhcolonial;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.androidrhcolonial.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ingresar extends AppCompatActivity {

    EditText edtTelefono,edtPassword;
    Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPassword=(EditText )findViewById(R.id.edtPassword);
        edtTelefono=(EditText )findViewById(R.id.edtTelefono);
        btnIngresar =(Button)findViewById(R.id.btnIngresar);


        //Iniciando Firebase
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference tabla_usuario=database.getReference("Usuario");
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


// Se muestra un dialogo de espera
                final ProgressDialog mDialogo=new ProgressDialog(Ingresar.this);
                mDialogo.setMessage("Conectando");
                mDialogo.show();
                tabla_usuario.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Ver si no eciste el usuario
                        if(dataSnapshot.child(edtTelefono.getText().toString()).exists())
                        {
                            //Desapareciendo el mensaje
                            mDialogo.dismiss();
                            Usuario usuario=dataSnapshot.child(edtTelefono.getText().toString()).getValue(Usuario.class);
                            if(usuario.getPassword().equals(edtPassword.getText().toString()))
                            {
                                Toast.makeText(Ingresar.this,"Exitoso ingreso",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                Toast.makeText(Ingresar.this,"Exitoso No exitoso",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            //Desapareciendo el mensaje
                            mDialogo.dismiss();
                            Toast.makeText(Ingresar.this,"No existe usuario",Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
