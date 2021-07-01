package com.example.tarea09.modelo;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Estudiante implements Serializable {

    private int codigo;
    private String nombre;
    private Aula aula;
    private double practica;
    private double trabajos;
    private double evaluacion;
    private double promedio;

    public Estudiante() {
    }

    public Estudiante(Object[] Registro) {
        this.codigo = Integer.parseInt(Registro[0].toString());
        this.nombre = Registro[1].toString();
        this.aula = (Aula) Registro[2];
        this.practica = Double.parseDouble(Registro[3].toString());
        this.trabajos = Double.parseDouble(Registro[4].toString());
        this.evaluacion = Double.parseDouble(Registro[5].toString());
        this.promedio = Double.parseDouble(Registro[6].toString());
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public double getPractica() {
        return practica;
    }

    public void setPractica(double practica) {
        this.practica = practica;
    }

    public double getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(double trabajos) {
        this.trabajos = trabajos;
    }

    public double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    @Override
    public String toString() {
//        return codigo + ": " + nombre + " (3er Grado " + aula + ") - " + promedio;
        return codigo + ": " + nombre;
    }
}
