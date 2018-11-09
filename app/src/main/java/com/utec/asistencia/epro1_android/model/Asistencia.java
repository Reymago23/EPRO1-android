package com.utec.asistencia.epro1_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Asistencia {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("carne")
    @Expose
    private String carne;

    @SerializedName("asignatura")
    @Expose
    private String asignatura;

    @SerializedName("seccion")
    @Expose
    private String seccion;

    @SerializedName("aula")
    @Expose
    private String aula;

    @SerializedName("ciclo")
    @Expose
    private String ciclo;

    @SerializedName("fechaHora")
    @Expose
    private Timestamp fechaHora;

    @Override
    public String toString() {
        return "Asistencia{" +
                "id=" + id +
                ", carne='" + carne + '\'' +
                ", asignatura='" + asignatura + '\'' +
                ", seccion='" + seccion + '\'' +
                ", aula='" + aula + '\'' +
                ", ciclo='" + ciclo + '\'' +
                '}';
    }

    public Asistencia() {
    }

    public Asistencia(String carne, String asignatura, String seccion, String aula, String ciclo) {
        this.carne = carne;
        this.asignatura = asignatura;
        this.seccion = seccion;
        this.aula = aula;
        this.ciclo = ciclo;
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

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }
}
