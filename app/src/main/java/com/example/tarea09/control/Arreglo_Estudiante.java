package com.example.tarea09.control;

import com.example.tarea09.modelo.Aula;
import com.example.tarea09.modelo.Estudiante;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Arreglo_Estudiante {

    private ArrayList<Estudiante> lista = new ArrayList<>();

    public ArrayList<Estudiante> getLista() {
        return lista;
    }

    public void Registrar(Estudiante estudiante){
        lista.add(estudiante);
    }

    public void Editar(int pos, Estudiante estudiante){
        lista.set(pos, estudiante);
    }

    public void Eliminar(){
        lista.removeAll(lista);
    }

    public void Eliminar(Estudiante estudiante){
        lista.remove(estudiante);
    }

    public Estudiante getEstudiante(int pos){
        return lista.get(pos);
    }

    public int getSize(){
        return lista.size();
    }

    public void OrdenarCodigo(boolean ascendente){
        Collections.sort(lista, new Comparator<Estudiante>() {
            @Override
            public int compare(Estudiante e1, Estudiante e2) {
                if (ascendente) return new Integer(e1.getCodigo()).compareTo(new Integer(e2.getCodigo()));
                else return new Integer(e2.getCodigo()).compareTo(new Integer(e1.getCodigo()));
            }
        });
    }

    public void OrdenarNombre(boolean ascendente){
        Collections.sort(lista, new Comparator<Estudiante>() {
            @Override
            public int compare(Estudiante e1, Estudiante e2) {
                if (ascendente) return e1.getNombre().compareTo(e2.getNombre());
                else return e2.getNombre().compareTo(e1.getNombre());
            }
        });
    }

    public void OrdenarAula(boolean ascendente){
        Collections.sort(lista, new Comparator<Estudiante>() {
            @Override
            public int compare(Estudiante e1, Estudiante e2) {
                if (ascendente) return e1.getAula().compareTo(e2.getAula());
                else return e2.getAula().compareTo(e1.getAula());
            }
        });
    }

    public void OrdenarPromedio(boolean ascendente){
        Collections.sort(lista, new Comparator<Estudiante>() {
            @Override
            public int compare(Estudiante e1, Estudiante e2) {
                if (ascendente) return new Double(e1.getPromedio()).compareTo(new Double(e2.getPromedio()));
                else return new Double(e2.getPromedio()).compareTo(new Double(e1.getPromedio()));
            }
        });
    }

    public ArrayList<Estudiante> getEstudiantes(boolean aprobados){
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (aprobados) {
                if (lista.get(i).getPromedio() >= 10.5) estudiantes.add(lista.get(i));
            } else {
                if (lista.get(i).getPromedio() < 10.5) estudiantes.add(lista.get(i));
            }

        }
        return estudiantes;
    }

    public ArrayList<Estudiante> getEstudiantes(Aula aula){
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            if (aula.equals(lista.get(i).getAula())) estudiantes.add(lista.get(i));
        }
        return estudiantes;
    }

    public int getCantidad(Aula aula){
        int cantidad = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (aula.equals(lista.get(i).getAula())) ++cantidad;
        }
        return cantidad;
    }

    public double getPromedio(Aula aula){
        double suma = 0;
        int cantidad = getCantidad(aula);
        for (int i = 0; i < lista.size(); i++) {
            if (aula.equals(lista.get(i).getAula())) suma += lista.get(i).getPromedio();
        }
        return (cantidad > 0) ? Double.parseDouble(new DecimalFormat("###.#").format(suma/cantidad)) : 0;
    }

    public int CodigoMayor(){
        int codigo = 0;
        for (int pos = 0; pos < lista.size(); pos++) {
            if (lista.get(pos).getCodigo() > codigo) codigo = lista.get(pos).getCodigo();
        }
        return codigo;
    }
}
