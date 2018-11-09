package com.utec.asistencia.epro1_android.model;

public class Asignatura {



    private String nombre;
    private String seccion;
    private String dias;
    private String hora;
    private String aula;
    private String ciclo;

    @Override
    public String toString() {
        return "Asignatura{" +
                "nombre='" + nombre + '\'' +
                ", seccion='" + seccion + '\'' +
                ", dias='" + dias + '\'' +
                ", hora='" + hora + '\'' +
                ", aula='" + aula + '\'' +
                ", ciclo='" + ciclo + '\'' +
                '}';
    }

    public Asignatura() {
    }

    public Asignatura(String nombre, String seccion, String dias, String hora, String aula, String ciclo) {
        this.nombre = nombre;
        this.seccion = seccion;
        this.dias = dias;
        this.hora = hora;
        this.aula = aula;
        this.ciclo = ciclo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
}
