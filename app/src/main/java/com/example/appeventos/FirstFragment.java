package com.example.appeventos;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appeventos.databinding.FragmentFirstBinding;

import java.util.ArrayList;


public class FirstFragment extends Fragment implements OnItemClickListener<Evento>{

    private FragmentFirstBinding binding;

    private EventoAdapter adapter;

    private EventoApp app;

    private EventoViewModel model;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        app= EventoApp.getInstance();
        model=new ViewModelProvider(this).get(EventoViewModel.class);
        adapter = new EventoAdapter(new ArrayList<>(), this);
        model.getEventoDataset().observe(this, eventos -> {
            adapter.setItems(eventos);
            validarDataset();
        });

        setUpRecyclerView();

        return binding.getRoot();

    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        binding.rcEvento.setLayoutManager(layoutManager);
        binding.rcEvento.setAdapter(adapter);
    }

    private void validarDataset() {
        if (adapter.getItemCount()==0){
            binding.imagen.setVisibility(View.VISIBLE);
            binding.advertencia.setVisibility(View.VISIBLE);
            binding.rcEvento.setVisibility(View.INVISIBLE);
        }else {
            binding.imagen.setVisibility(View.INVISIBLE);
            binding.advertencia.setVisibility(View.INVISIBLE);
            binding.rcEvento.setVisibility(View.VISIBLE);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data);

        if (requestCode==6){
            if (requestCode== RESULT_OK){
                int id=data.getIntExtra("ID",0);
                String tema=data.getStringExtra("Tema");
                String fecha=data.getStringExtra("Fecha");
                String expositor=data.getStringExtra("Expositor");
                String estado=data.getStringExtra("Estado");

                Evento updateEvent=new Evento(tema,fecha,expositor,estado);
                updateEvent.setIdEvento(id);
                model.update(updateEvent);
            }
        }
    }

    @Override
    public void onItemClick(Evento data, int type) {
        Intent intent=new Intent();
        intent.putExtra("ID", data.getIdEvento());
        intent.putExtra("Tema", data.getTema());
        intent.putExtra("Fecha", data.getFechaEvento());
        intent.putExtra("Expositor", data.getExpositor());
        intent.putExtra("Estado", data.getEstado());

        startActivityForResult(intent, 6, ActivityOptions.makeSceneTransitionAnimation(this.getActivity()).toBundle());
    }
}