package com.example.appeventos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventoViewModel extends AndroidViewModel {

    private EventoRepository repository;

    private final LiveData<List<Evento>> eventoDataset;

    public EventoViewModel(@NonNull Application application) {
        super(application);
        repository = new EventoRepository(application);
        this.eventoDataset=repository.getEventoDataset();
    }

    public LiveData<List<Evento>> getEventoDataset() {
        return eventoDataset;
    }

    public void insert(Evento nuevoEvento){
        repository.insert(nuevoEvento);
    }

    public void update(Evento modEvento){
        repository.update(modEvento);
    }

    public void delete(Evento delEvento){
        repository.delete(delEvento);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
