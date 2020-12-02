package com.example.agenda.Daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {
    public static final String FICHA="fichas";
    public static final String[] FICHACOLUMNS={"titulo","descripcion","tipo","fechacreacion","fecharecordatorio","estado"};
    public static final String ARCHIVO="archivos";
    public static final String[] ARCHIVOCOLUMNS={"idArchivo","descripcion","tipo","ruta","tituloficha"};
    public static final String RECORDATORIO="recordatorios";
    public static final String[] RECORDATORIOCOLUMNS={"idrecordatorio","dia","mes","anio" +
            "","hora","minuto","tituloficha"};
    private String scripFicha="create table "+FICHA+" ("+FICHACOLUMNS[0]+" text primary key,"+
            FICHACOLUMNS[1]+" text,"+FICHACOLUMNS[2]+" text,"+FICHACOLUMNS[3]+" text,"+FICHACOLUMNS[4]+" text,"+
            FICHACOLUMNS[5]+" text);";
    private String scripArchivo="create table "+ARCHIVO+" ("+ARCHIVOCOLUMNS[0]+" integer primary key autoincrement,"+
            ARCHIVOCOLUMNS[1]+" text, "+ARCHIVOCOLUMNS[2]+" text, "+ARCHIVOCOLUMNS[3]+" text,"+ARCHIVOCOLUMNS[4]+" text,"+
            " foreign key ("+ARCHIVOCOLUMNS[4]+") references "+FICHA+"("+FICHACOLUMNS[0]+"));";
    private String scripRecordatorio="create table "+RECORDATORIO+" ("+RECORDATORIOCOLUMNS[0]+" integer primary key autoincrement,"+
            RECORDATORIOCOLUMNS[1]+" integer,"+RECORDATORIOCOLUMNS[2]+" integer,"+RECORDATORIOCOLUMNS[3]+" integer,"+RECORDATORIOCOLUMNS[4]+" integer,"+
            RECORDATORIOCOLUMNS[5]+" integer,"+RECORDATORIOCOLUMNS[6]+" text,"+
            " foreign key ("+RECORDATORIOCOLUMNS[6]+") references "+FICHA+"("+FICHACOLUMNS[0]+"));";
    public BaseDeDatos(Context context) {
        super(context,"database.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(scripFicha);
        db.execSQL(scripArchivo);
        db.execSQL(scripRecordatorio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
