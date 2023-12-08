package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Objects.Contactos;
import com.example.myapplication.Objects.ReferenciasFirebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    private FirebaseDatabase basedatabase;
    private DatabaseReference referencia;
    private Button btnNuevo;
    private void setListAdapter(MyArrayAdapter adapter) {
    }
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        basedatabase = FirebaseDatabase.getInstance();
        referencia =
                basedatabase.getReferenceFromUrl(ReferenciasFirebase.URL_DATABASE
                        + ReferenciasFirebase.DATABASE_NAME + "/" +
                        ReferenciasFirebase.TABLE_NAME);
        btnNuevo = (Button)findViewById(R.id.btnNuevo);
        obtenerContactos();
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
    public void obtenerContactos(){
        final ArrayList<Contactos> contactos = new ArrayList<Contactos>();

        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot dataSnapshot, String s) {
                Contactos contacto = dataSnapshot.getValue(Contactos.class);
                contactos.add(contacto);
                final MyArrayAdapter adapter = new MyArrayAdapter(context, R.layout.layout_contacto,contactos);
                setListAdapter(adapter);
            }

            @Override
            public void onChildChanged( DataSnapshot snapshot, String s) {

            }

            @Override
            public void onChildRemoved( DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        };
        referencia.addChildEventListener(listener);
    }



    class MyArrayAdapter extends ArrayAdapter<Contactos>
    { Context context;
        int textViewRecursoId;

        ArrayList<Contactos> objects;

        public MyArrayAdapter(Context context, int textViewRecursoId,
                              ArrayList<Contactos>
                              objects){
            super(context, textViewRecursoId, objects);
            this.context = context;
            this.textViewRecursoId = textViewRecursoId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup){

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(this.textViewRecursoId, null);
            TextView lblNombre = (TextView) view.findViewById(R.id.lblNombreContacto);
            TextView lblTelefono = (TextView) view.findViewById(R.id.lblTelefonoContacto);
            Button btnModificar = (Button) view.findViewById(R.id.btnModificar);
            Button btnBorrar = (Button) view.findViewById(R.id.btnBorrar);

            if(objects.get(position).getFavorito()>0){
                lblNombre.setTextColor(Color.BLUE);
                lblTelefono.setTextColor(Color.BLUE);
            } else {

                lblNombre.setTextColor(Color.BLACK);
                lblTelefono.setTextColor(Color.BLACK);
            }
            lblNombre.setText(objects.get(position).getNombre());
            lblTelefono.setText(objects.get(position).getTelefono1());
            btnBorrar.setOnClickListener(new View.OnClickListener(){

                @Override
                public  void onClick(View v) {
                    borrarContacto(objects.get(position).getID());
                    objects.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Contacto eliminado con exito",
                            Toast.LENGTH_SHORT).show();
                }
            });
            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle oBundle = new Bundle();
                    oBundle.putSerializable("contacto", objects.get(position));
                    Intent i = new Intent();
                    i.putExtras(oBundle);
                    setResult(Activity.RESULT_OK, i);

                    finish();

                }
            });

            return view;
        }

    }
    public void borrarContacto(String childIndex){
        referencia.child(String.valueOf(childIndex)).removeValue();
    }
}