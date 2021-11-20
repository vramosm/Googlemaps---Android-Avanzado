package com.example.googlemaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SegundoActivity extends AppCompatActivity {

    TextView fecha;
    TextView hora;
    TextView minuto;
    EditText titulo;
    EditText descripcion;
    Button irPaginaInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segundo_fragment);

        //Recupera la información del anterior activity para mostrarse arriba por si fuese necesario cambiar algo

        String valor = getIntent().getStringExtra("fecha");
        fecha = (TextView) findViewById(R.id.fecha);
        fecha.setText(valor);

        int valor1 = getIntent().getIntExtra("hora", 0);
        hora = (TextView) findViewById(R.id.hora);
        hora.setText(String.valueOf(valor1));

        int valor2 = getIntent().getIntExtra("minuto", 0);
        minuto = (TextView) findViewById(R.id.minuto);
        minuto.setText(String.valueOf(valor2));

        titulo = (EditText) findViewById(R.id.titulo);
        descripcion = (EditText) findViewById(R.id.descripcion);

        irPaginaInicio = (Button) findViewById(R.id.irPaginaInicio);
        irPaginaInicio.setOnClickListener(new View.OnClickListener() {


            //Pasa la informacion al activity principal
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MapsActivity.class);

                intent.putExtra("fecha", valor);
                intent.putExtra("hora", valor1);
                intent.putExtra("minuto", valor2);
                intent.putExtra("titulo", titulo.getText().toString());
                intent.putExtra("descripcion", descripcion.getText().toString());

                startActivityForResult(intent, 0);
            }
        });

    }
}
