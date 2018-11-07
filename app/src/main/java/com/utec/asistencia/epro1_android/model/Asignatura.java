package com.utec.asistencia.epro1_android.model;

public class Asignatura {



    private String name;
    private String sec;
    private String aula;
    private String ciclo;

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public Asignatura() {
    }

    public Asignatura(String name, String sec) {
        this.name = name;
        this.sec = sec;
    }


    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }
}
