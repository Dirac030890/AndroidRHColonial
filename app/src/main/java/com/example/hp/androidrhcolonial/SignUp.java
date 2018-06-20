package com.example.hp.androidrhcolonial;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hp.androidrhcolonial.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
MaterialEditText edtTelefono,edtNombre,edtPassword;
Button btnRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtNombre =(MaterialEditText)findViewById(R.id.edtNombre);
        edtTelefono =(MaterialEditText)findViewById(R.id.edtTelefono);
        edtPassword =(MaterialEditText)findViewById(R.id.edtPassword);
        btnRegistrarse=(Button)findViewById(R.id.btnRegistrarse);


        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference tabla_usuario=database.getReference("Usuario");
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialogo=new ProgressDialog(SignUp.this);
                mDialogo.setMessage("Conectando");
                mDialogo.show();
                tabla_usuario.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       if(dataSnapshot.child(edtTelefono.getText().toString()).exists())
                       {
                           mDialogo.dismiss();
                           Toast.makeText(SignUp.this,"Numero de telefono ya registrado",Toast.LENGTH_SHORT).show();
                       }else
                       {
                           mDialogo.dismiss();
                           Usuario usuario =new Usuario(edtNombre.getText().toString(),edtPassword.getText().toString());
                           tabla_usuario.child(edtTelefono.getText().toString()).setValue(usuario);

                           Toast.makeText(SignUp.this,"Registrado Felicidades",Toast.LENGTH_SHORT).show();

                            finish();
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
