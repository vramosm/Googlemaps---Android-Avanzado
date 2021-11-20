package com.example.googlemaps;

public class Evento {
    String fecha;
    String titulo;
    String descripcion;
    int hora;
    int minuto;

    public Evento(String fecha, String titulo, String descripcion, int hora, int minuto) {
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hora = hora;
        this.minuto = minuto;
    }
}
