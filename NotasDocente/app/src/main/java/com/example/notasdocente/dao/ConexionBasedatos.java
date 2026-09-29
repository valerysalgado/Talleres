package com.example.notasdocente.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionBasedatos extends SQLiteOpenHelper {

    private SQLiteDatabase conexion;
    private String nombreBD;
    private int versionBD;
    private Context actividad;
    private String estado;

    public static final int MODO_LECTURA = 0;
    public static final int MODO_ESCRITURA = 1;

    public ConexionBasedatos(Context actividad, String nombreBD, SQLiteDatabase.CursorFactory cursor, int versionBD) {
        super(actividad, nombreBD, cursor, versionBD);
        this.nombreBD = nombreBD;
        this.versionBD = versionBD;
        this.actividad = actividad;
        this.estado = "Instancia SI";
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        try {
            bd.execSQL(CodigoSQLBackUp.sqlBackup);
            estado = "onCreate Ok\n";
        } catch (SQLiteException e) {
            estado += "onCreate NO\n";
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionActual, int versionNueva) {
        try {
            bd.execSQL(CodigoSQLBackUp.sqlBackup);
            estado += "onUpgrade SI\n";
        } catch (SQLiteException e) {
            estado += "onCreate NO\n";
        }
    }

    public void conectar(int modo) throws Exception {
        try {
            if (modo == MODO_ESCRITURA) {
                conexion = this.getWritableDatabase();
                estado += "Conexion Write SI\n";
            } else {
                conexion = this.getReadableDatabase();
                estado += "Conexion Read SI\n";
            }
        } catch (SQLiteException error) {
            estado += "Conexion Read/Write NO\n";
            throw new Exception("ERROR: Sin conexion a la BD\nMensaje: " + error.getMessage());
        }
    }

    public void desconectar() {
        if (conexion != null && conexion.isOpen()) {
            conexion.close();
            estado += "DesConexion SI\n";
        }
        conexion = null;
    }

    public void insertar(String tabla, ContentValues columnasValor) throws Exception {
        try {
            conexion.insertOrThrow(tabla, null, columnasValor);
        } catch (SQLException e) {
            throw new Exception("Mensaje: " + e.getMessage());
        }
    }

    public void actualizar(String tablas, ContentValues columnasValor, String whereColumnasIgualValor, String[] valorWhere) {
        conexion.update(tablas, columnasValor, whereColumnasIgualValor, valorWhere);
    }

    public void eliminar(String tabla, String whereColumnasIgualValor, String[] valorWhere) {
        conexion.delete(tabla, whereColumnasIgualValor, valorWhere);
    }

    public Cursor consultar(String consultaSQL, String[] valorWhere) {
        return conexion.rawQuery(consultaSQL, valorWhere);
    }

    public String getEstado() {
        return estado;
    }
}