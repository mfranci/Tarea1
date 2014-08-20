package com.example.matt.tarea1.domain;

/**
 * Created by Student on 05/07/2014.
 */
public class Tarea {
    private int id;
    private String usuario_id;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String hora;
    private int prioridad;


    public Tarea(String usuario_id, String nombre, String descripcion, String fecha, String hora, int prioridad) {
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.prioridad = prioridad;
    }

    public Tarea(int id, String usuario_id, String nombre, String descripcion, String fecha, String hora, int prioridad) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.prioridad = prioridad;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
