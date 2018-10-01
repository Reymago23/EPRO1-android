package com.utec.asistencia.epro1_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Alumno {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("carne")
    @Expose
    private String carne;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    public Alumno(String carne, String password, String nombre, String apellido) {
        this.carne = carne;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
