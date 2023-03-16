package com.example.appeventos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventoRepository {

    private EventoDao eventoDao;
    private LiveData<List<Evento>> eventoDataset;

    //AQUI SE HACE EL USO DEL QUERY SELECT * FROM
    public EventoRepository(Application app) {
        EventoDatabase db = EventoDatabase.getDatabase(app);
        eventoDao=db.eventoDao();
        eventoDataset = eventoDao.getEvento();
    }

    public LiveData<List<Evento>> getEventoDataset(){return eventoDataset;}

    public void insert(Evento nuevoEvento){
        EventoDatabase.dataWriterExecutor.execute(()->{
            eventoDao.insert(nuevoEvento);
        });
    }

    public void update(Evento modEvento){
        EventoDatabase.dataWriterExecutor.execute(()->{
            eventoDao.Update(modEvento);
        });
    }

    public void delete(Evento delEvento){
        EventoDatabase.dataWriterExecutor.execute(()->{
            eventoDao.delete(delEvento);
        });
    }

    public void deleteAll(){
        EventoDatabase.dataWriterExecutor.execute(()->{
            eventoDao.deleteAll();
        });
    }
}
