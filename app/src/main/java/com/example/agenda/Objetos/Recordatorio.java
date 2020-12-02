package com.example.agenda.Objetos;

import java.util.Calendar;

public class Recordatorio {
    private int id;
    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int minuto;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Recordatorio() {
    }

    public Recordatorio(int id, int dia, int mes, int anio, int hora, int minuto,String titulo) {
        this.id = id;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.hora = hora;
        this.minuto = minuto;
        this.titulo=titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        return (this.dia+"-"+this.mes+"-"+this.anio+"-"+this.hora+":"+this.minuto);
    }

    public long milis(){
        Calendar fechaActual= Calendar.getInstance();
        long millis=0;
        int hora=fechaActual.get(Calendar.HOUR);
        int minute=fechaActual.get(Calendar.MINUTE);
        int year=fechaActual.get(Calendar.YEAR);
        int month=fechaActual.get(Calendar.MONTH);
        int day=fechaActual.get(Calendar.DAY_OF_MONTH);
        millis+=Math.abs(this.hora-hora);
        millis+=Math.abs(this.minuto-minute)*60*1000;
        return millis;
    }
}
