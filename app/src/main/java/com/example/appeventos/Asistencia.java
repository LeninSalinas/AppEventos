package com.example.appeventos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "asistencia_table", foreignKeys = @ForeignKey(entity = Evento.class,parentColumns = "id", childColumns = "idEvento"))
public class Asistencia {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "num_asistencia")
    private int numeroAsistencia;

    @NonNull
    @ColumnInfo(name = "confirmed")
    private boolean confirmada;

    @ColumnInfo(name = "idEvento")
    private int idEvento;

    public Asistencia(){
    }

    public Asistencia(int numeroAsistencia, boolean confirmada) {
        this.numeroAsistencia = numeroAsistencia;
        this.confirmada = confirmada;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroAsistencia() {
        return ++numeroAsistencia;
    }

    public void setNumeroAsistencia(int numeroAsistencia) {
        this.numeroAsistencia = numeroAsistencia;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }
}
