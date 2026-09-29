package com.example.notasdocente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notasdocente.dao.DaoUniversidad;
import com.example.notasdocente.entidades.Universidad;

public class ActividadCrudUniversidad extends Activity {

    private Button botonAgregar;
    private Button botonBuscar;
    private EditText campoNombre;
    private EditText campoId;
    private EditText campoWww;
    private DaoUniversidad dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_crud_universidad);

        campoId = (EditText) findViewById(R.id.campoId);
        campoNombre = (EditText) findViewById(R.id.campoNombre);
        campoWww = (EditText) findViewById(R.id.campoWww);
        botonAgregar = (Button) findViewById(R.id.botonGuardar);
        botonBuscar = (Button) findViewById(R.id.botonBuscar);

        try {
            dao = new DaoUniversidad(this, "notasdocentev1", 1);
            int proximo = dao.proximoId();
            campoId.setText("" + proximo);
        } catch (Exception e) {
            Toast msg = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            msg.show();
        }
    }

    public void agregarUniversidad(View campo) {
        String nombre = campoNombre.getText().toString();
        String www = campoWww.getText().toString();
        Universidad u = new Universidad();
        u.setNombre(nombre);
        u.setWww(www);

        try {
            dao.agregarUniversidad(u);
            Toast mensaje = Toast.makeText(this.getApplicationContext(), "Mensaje: Uni Agregada", Toast.LENGTH_LONG);
            mensaje.show();
            int proximo = dao.proximoId();
            campoId.setText("" + proximo);
            limpiarCampos(campo);
        } catch (Exception e) {
            Toast mensaje = Toast.makeText(this.getApplicationContext(), "ERROR: " + dao.getBd().getEstado() + "\n" + e.getMessage(), Toast.LENGTH_LONG);
            mensaje.show();
        }
    }

    public void limpiarCampos(View componente) {
        campoNombre.setText("");
        campoWww.setText("");
        int proximo = dao.proximoId();
        campoId.setText("" + proximo);
    }

    public void mostrarCuadroDialogoId(final View componente) {
        LayoutInflater layoutEmergente = LayoutInflater.from(this);
        View formularioId = layoutEmergente.inflate(R.layout.layout_dialog_buscar_id, null);
        AlertDialog.Builder dialogoId = new AlertDialog.Builder(this);
        dialogoId.setView(formularioId);

        final EditText campoBuscarId = (EditText) formularioId.findViewById(R.id.campoBuscarId);

        dialogoId.setCancelable(false);

        DialogInterface.OnClickListener oyenteok = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                campoId.setText(campoBuscarId.getText());
                buscarUniversidad(componente);
            }
        };

        DialogInterface.OnClickListener oyenteCancelar = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                limpiarCampos(componente);
            }
        };

        dialogoId.setPositiveButton("OK", oyenteok);
        dialogoId.setNegativeButton("Cancelar", oyenteCancelar);
        AlertDialog mensaje = dialogoId.create();
        mensaje.show();
    }

    public void buscarUniversidad(View campo) {
        String id = campoId.getText().toString();
        Universidad u = null;

        try {
            u = dao.consultarUnaUniversidad(id);
            if (u == null) {
                Toast mensaje = Toast.makeText(this.getApplicationContext(), "Mensaje: Universidad No Encontrada", Toast.LENGTH_LONG);
                mensaje.show();
            } else {
                campoNombre.setText(u.getNombre());
                campoWww.setText(u.getWww());
                Toast mensaje = Toast.makeText(this.getApplicationContext(), "Mensaje: Universidad Encontrada", Toast.LENGTH_LONG);
                mensaje.show();
            }
        } catch (Exception e) {
            Toast mensaje = Toast.makeText(this.getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG);
            mensaje.show();
        }
    }
}