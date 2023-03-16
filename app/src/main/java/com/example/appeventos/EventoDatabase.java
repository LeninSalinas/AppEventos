package com.example.appeventos;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Evento.class}, version = 1, exportSchema = true)
public abstract class EventoDatabase extends RoomDatabase {

    public abstract EventoDao eventoDao();

    public static volatile EventoDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService dataWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    Evento evento = new Evento("Nombre del tema", "14/3/2023", "Nombre del expositor", "Estado del evento");
    static EventoDatabase getDatabase(final Context context) {
        if (INSTANCE==null){
            synchronized (EventoDatabase.class){
                RoomDatabase.Callback miCallback=new RoomDatabase.Callback(){
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db){
                        super.onCreate(db);
                        dataWriterExecutor.execute(()->{
                            /*EventoDao dao = INSTANCE.eventoDao();
                            dao.deleteAll();

                            Evento evento = new Evento("Robotica Industrial","14/3/2023","Kenny Cooper","Por comenzar");
                            dao.insert(evento);*/
                        });
                    }
                };
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), EventoDatabase.class,"evento_database").addCallback(miCallback).build();
            }

        }
        return INSTANCE;
    }
}
