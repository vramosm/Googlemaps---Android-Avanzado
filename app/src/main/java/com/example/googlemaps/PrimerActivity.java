package com.example.googlemaps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrimerActivity extends AppCompatActivity {

    private Button IrSegundoActivity;
    private CalendarView calendarView;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primer_activity);


        //se Añade la funcion de calendiario y reloj

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        timePicker = (TimePicker) findViewById(R.id.datePicker1);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                view.setDate(calendar.getTimeInMillis());

                Log.d("calendar", String.valueOf(view.getDate()));
            }
        });


        //Se pasa la información al siguiente activity
        IrSegundoActivity = (Button) findViewById(R.id.irSegundo);
        IrSegundoActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SegundoActivity.class);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDate = sdf.format(new Date(calendarView.getDate()));

                intent.putExtra("fecha", selectedDate);
                intent.putExtra("hora", timePicker.getHour());
                intent.putExtra("minuto", timePicker.getMinute());

                startActivityForResult(intent, 0);
            }
        });
    }
}
