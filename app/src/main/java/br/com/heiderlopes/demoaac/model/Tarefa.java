package br.com.heiderlopes.demoaac.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Tarefa {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String tarefa;

    public Tarefa(){}

    public Tarefa(String titulo, String tarefa) {
        this.titulo = titulo;
        this.tarefa = tarefa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tarefa='" + tarefa + '\'' +
                '}';
    }
}