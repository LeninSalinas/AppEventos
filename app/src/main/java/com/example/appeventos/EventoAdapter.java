package com.example.appeventos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appeventos.databinding.EventoItemBinding;

import java.util.ArrayList;
import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    private List<Evento> dataset;
    private OnItemClickListener<Evento> manageClickAction;

    public EventoAdapter(List<Evento> dataset, OnItemClickListener<Evento> manageClickAction) {
        copiarDataset(dataset);
        this.manageClickAction = manageClickAction;
    }

    private void copiarDataset(List<Evento> newDataset) {
        if (this.dataset==null){
            this.dataset = new ArrayList<>();
        }
        this.dataset.clear();
        newDataset.forEach(d->{
            this.dataset.add(d);
        });
    }

    @NonNull
    @Override
    public EventoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventoItemBinding binding = EventoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    //Aqui es donde se modifican los datos de acorde a lo escrito
    @Override
    public void onBindViewHolder(@NonNull EventoAdapter.ViewHolder holder, int position) {
        Evento eventoMostrar = dataset.get(position);
        holder.binding.tema.setText(eventoMostrar.getTema());
        holder.binding.fecha.setText(eventoMostrar.getFechaEvento());
        holder.binding.tvExpositor.setText(eventoMostrar.getExpositor());
        holder.setOnClickListener(eventoMostrar, manageClickAction);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setItems(List<Evento> newDataset){
        copiarDataset(newDataset);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        EventoItemBinding binding;

        public ViewHolder( EventoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setOnClickListener(final Evento eventoSelected, final OnItemClickListener<Evento> listner){
            this.binding.cardEvento.setOnClickListener(v-> listner.onItemClick(eventoSelected, 1));
        }
    }
}
