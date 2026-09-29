package com.example.notasdocente.entidades;

public class Asignatura {
    private String codigo;
    private String nombre;
    private Programas carrera;

    public Asignatura() { }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Programas getCarrera() { return carrera; }
    public void setCarrera(Programas carrera) { this.carrera = carrera; }
}