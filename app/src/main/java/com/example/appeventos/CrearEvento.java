package com.example.appeventos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import com.example.appeventos.databinding.ActivityCrearEventoBinding;
import com.google.android.material.snackbar.Snackbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CrearEvento extends AppCompatActivity {

    private ActivityCrearEventoBinding binding;

    private int idEvent;

    private int numeroAsistencia = 0;

    private EventoViewModel model;

    Spinner spinnerEstado;

    Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        evento=new Evento();

        model = new ViewModelProvider(this).get(EventoViewModel.class);

        binding=ActivityCrearEventoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        obtenerDataIntent(getIntent());
        setSupportActionBar(binding.toolbar);

        spinnerEstado = findViewById(R.id.estado_spinner);
        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this, R.array.estados, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterEstado.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterEstado);

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estadoSeleccionado = parent.getItemAtPosition(position).toString();
                evento.setEstado(estadoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hace nada
            }
        });

        binding.btnGuardar.setOnClickListener(v->{
            actionButtonSave();
        });

        //setContentView(R.layout.activity_crear_evento);
    }

    public void actionButtonSave(){
        //Aqui volvemos a inicio para verificar los cambios
        Intent result=new Intent();
        result.putExtra("ID", idEvent);
        result.putExtra("TEMA", binding.tltTema.getEditText().getText().toString());
        result.putExtra("FECHA", binding.tltTema.getEditText().getText().toString());
        result.putExtra("EXPOSITOR", binding.expositor.getEditText().getText().toString());
        result.putExtra("ESTADO", evento.getEstado());

        setResult(Activity.RESULT_OK, result);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        int id = item.getItemId();

        if (id==android.R.id.home){
            onBackPressed();
        } else if (id==R.id.fin_event) {

            Asistencia asistencia=new Asistencia();

            // Crea un objeto AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // Establece el título del diálogo
            builder.setTitle("Finalizar evento");

            // Establece el mensaje del diálogo con el número de asistencia
            builder.setMessage("¿Estás seguro de que quieres finalizar el evento?");

            // Agrega un botón "OK" al diálogo
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Acción a realizar al hacer clic en el botón "OK"
                    builder.setMessage("El numero de asistencia fue: "+asistencia.getNumeroAsistencia()).setTitle("Numero total de asistencias");
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            
            // Crea el diálogo y muéstralo
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (id==R.id.del_event) {

            Evento delEvento=new Evento(binding.tltTema.getEditText().getText().toString(),

                    binding.tltFecha.getEditText().toString(),binding.expositor.getEditText().toString(),

                    binding.estadoSpinner.getOnItemSelectedListener().toString());
            //Establecemos el id del evento
            delEvento.setIdEvento(idEvent);

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete_dialog_message)
                    .setTitle(R.string.delete_dialog_title);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    model.delete(delEvento);

                    Snackbar.make(binding.consCrear,getString(R.string.elim_event), Snackbar.LENGTH_LONG).show();
                    finish();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmarAsistencia(int numeroAsistencia, Asistencia asistencia) {
        // Actualiza la asistencia para que se muestre como confirmada en el RecyclerView
        asistencia.setConfirmada(true);

        // Muestra un mensaje de confirmación con el número de asistencia y los detalles de la asistencia
        //Toast.makeText(this, "Asistencia número " + numeroAsistencia + " confirmada para " + asistencia.getNombre(), Toast.LENGTH_SHORT).show();
    }
        //return super.onOptionsItemSelected(item);


    private void obtenerDataIntent(Intent intent) {
        String tema= intent.getStringExtra("Tema");

        if (tema !=null){
            //Declaramos los valores cuando estos sean llamados al escenario principal
            String fecha= intent.getStringExtra("Fecha");
            String expositor= intent.getStringExtra("Expositor");
            String[] opcionesEstado = getResources().getStringArray(R.array.estados);
            idEvent=intent.getIntExtra("EVENTO_ID",0);

            //Establecemos el valor del tema
            binding.tltTema.getEditText().setText(tema);

            //Establecemos el valor de la fecha
            binding.tltFecha.getEditText().setText(fecha);

            //Establecemos el valor del expositor
            binding.expositor.getEditText().setText(expositor);

            // Establecer el estado inicial del Spinner utilizando setSelection()
            for (int i = 0; i < opcionesEstado.length; i++) {
                if (opcionesEstado[i].equals(opcionesEstado)) {
                    spinnerEstado.setSelection(i);
                    break;
                }

                //Establecemos el valor del boton
                binding.btnGuardar.setText(getText(R.string.btnguardar));

                //Establecemos el titulo
                binding.toolbar.setTitle(R.string.upt_event_title);

            }
        }else {
            //Se establece el 'texto crear evento' en ambos campos
            binding.btnGuardar.setText(getText(R.string.create_event));
            binding.toolbar.setTitle(R.string.create_event_title);
        }
    }
}