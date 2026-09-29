package com.example.notasdocente.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.notasdocente.entidades.Universidad;

import java.util.ArrayList;
import java.util.List;

public class DaoUniversidad {

    private ConexionBasedatos bd;

    public DaoUniversidad() { }

    public DaoUniversidad(Context actividad, String nombre, int version) throws Exception {
        try {
            bd = new ConexionBasedatos(actividad, nombre, null, version);
            bd.conectar(ConexionBasedatos.MODO_ESCRITURA);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void agregarUniversidad(Universidad institucion) throws Exception {
        ContentValues args = new ContentValues();

        if (institucion.getNombre() != null && !(institucion.getNombre().trim().isEmpty())) {
            args.put("nombre", institucion.getNombre());
        } else {
            throw new Exception("El nombre de la Universidad es Requerido");
        }

        if (institucion.getWww() != null && !(institucion.getWww().trim().isEmpty())) {
            args.put("www", institucion.getWww());
        } else {
            args.putNull("www");
        }

        try {
            bd.insertar("Universidades", args);
        } catch (Exception e) {
            throw new Exception("ERROR AL AGREGAR UNIVERSIDAD\nMensaje: " + e.getMessage());
        }
    }

    public Universidad getUniversidad(Cursor resultado) {
        Universidad institucion = new Universidad();
        institucion.setId(resultado.getString(0));
        institucion.setNombre(resultado.getString(1));
        institucion.setWww(resultado.getString(2));
        return institucion;
    }

    public Universidad consultarUnaUniversidad(String id) throws Exception {
        String[] args = new String[]{id};
        Cursor resultado = bd.consultar("SELECT * FROM Universidades WHERE id=?", args);

        if (resultado.moveToFirst()) {
            return getUniversidad(resultado);
        } else {
            throw new Exception("La Universidad " + id + " no ha sido agregada");
        }
    }

    public List<Universidad> listarTodasUniversidades() throws Exception {
        List<Universidad> listaUniversidades = new ArrayList<>();
        Cursor resultado = bd.consultar("SELECT * FROM Universidades", null);

        while (resultado.moveToNext()) {
            Universidad institucion = getUniversidad(resultado);
            listaUniversidades.add(institucion);
        }

        if (listaUniversidades.isEmpty()) {
            throw new Exception("No se han agregado Universidades al sistema");
        } else {
            return listaUniversidades;
        }
    }

    public void borrarUniversidad(String id) {
        String sqlDelete = "DELETE FROM Universidades WHERE id=?";
        String[] valoresWhere = {id};
        bd.eliminar("Universidades", "id=?", valoresWhere);
    }

    public void editarUniversidad(Universidad institucion) {
        ContentValues columnasValor = new ContentValues();
        columnasValor.put("nombre", institucion.getNombre());
        columnasValor.put("www", institucion.getWww());
        String[] valoresWhere = {institucion.getId()};
        bd.actualizar("Universidades", columnasValor, "id=?", valoresWhere);
    }

    public int proximoId() {
        String sentenciaSQL = "SELECT MAX(id) FROM Universidades";
        Cursor resultado = bd.consultar(sentenciaSQL, null);

        if (resultado.moveToFirst()) {
            return resultado.getInt(0) + 1;
        } else {
            return 1;
        }
    }

    public ConexionBasedatos getBd() {
        return bd;
    }
}