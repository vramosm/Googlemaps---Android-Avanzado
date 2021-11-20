package com.example.googlemaps;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mMarker;
    private Button IrPrimerActivity;
    public boolean show;
    public static List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Se añade el Fragment del mapa
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();

        //Se añade el array para visualizar el listado
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, list);


        ListView lv = (ListView) findViewById(R.id.listView);


        //Se recuperan los datos para el listado
        if (extras != null) {
            String titulo = extras.getString("titulo");
            String fecha = extras.getString("fecha");
            String descripcion = extras.getString("descripcion");
            int hora = extras.getInt("hora");
            int minuto = extras.getInt("minuto");

            Evento event = new Evento(fecha, titulo, descripcion, hora, minuto);

            list.add(" Titulo: " + event.titulo + "\n Descripción: " + event.descripcion + " \n Fecha: " + event.fecha + " \n Hora: " + event.hora + ":" + event.minuto);
            arrayAdapter.notifyDataSetChanged();
            lv.setAdapter(arrayAdapter);

        }

        //Se añade la geolocalizacion

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Geocoder geocoder = new Geocoder(getBaseContext());
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_SHORT).show();
                try {
                    if (mMap != null) {
                        mMap.clear();
                    }
                    String posicion = list.get(position);
                    String[] posiciones = posicion.split("Descripción: ");
                    String[] lugar = posiciones[1].split("\n");
                    List<Address> direcciones = geocoder.getFromLocationName(lugar[0], 3);
                    if (direcciones.size() != 0) {
                        Address direccion = direcciones.get(0);
                        LatLng sitio = new LatLng(direccion.getLatitude(), direccion.getLongitude());
                        mMarker = mMap.addMarker(new MarkerOptions().position(sitio).title(direccion.getLocality()));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 14));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        //Se añade la opcion de eliminar los eventos

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int posicion = i;


                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MapsActivity.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Elimina este evento ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        list.remove(posicion);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();

                return false;
            }
        });

        //Se añade el intent para cambiar de Activity

        IrPrimerActivity = (Button) findViewById(R.id.getEvent);
        IrPrimerActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PrimerActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(40.437578, -3.715415);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in CEV"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public class MapsMarkerActivity extends AppCompatActivity
            implements OnMapReadyCallback {
        // Include the OnCreate() method here too, as described above.
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Add a marker in Sydney, Australia,
            // and move the map's camera to the same location.
            LatLng sydney = new LatLng(-33.852, 151.211);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

}