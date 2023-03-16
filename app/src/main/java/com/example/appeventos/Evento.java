package com.example.appeventos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName="evento_table")
public class Evento {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer idEvento;

    @NonNull
    @ColumnInfo(name="tema")
    private String tema;

    @NonNull
    @ColumnInfo(name = "fecha")
    String fecha;

    @NonNull
    @ColumnInfo(name = "expositor")
    private String expositor;

    @NonNull
    @ColumnInfo(name = "estado")
    private String estado;

    public Evento() {
    }

    public Evento(@NonNull String tema, @NonNull String fecha, @NonNull String expositor, @NonNull String estado) {

        this.tema = tema;
        this.fecha = fecha;
        this.expositor = expositor;
        this.estado = estado;
    }

    @NonNull
    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(@NonNull Integer idEvento) {
        this.idEvento = idEvento;
    }

    @NonNull
    public String getTema() {
        return tema;
    }

    public void setTema(@NonNull String tema) {
        this.tema = tema;
    }

    @NonNull
    public String getFechaEvento() {
        return fecha;
    }

    public void setFechaEvento(@NonNull String fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public String getExpositor() {
        return expositor;
    }

    public void setExpositor(@NonNull String expositor) {
        this.expositor = expositor;
    }

    @NonNull
    public String getEstado() {
        return estado;
    }

    public void setEstado(@NonNull String estado) {
        this.estado = estado;
    }
}
