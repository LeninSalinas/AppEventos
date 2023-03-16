package com.example.appeventos;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.transition.Explode;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appeventos.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private EventoApp app;

    private String tema;

    private String fecha;

    private String expositor;

    private String estado;

    private EventoViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        app= EventoApp.getInstance();
        model = new ViewModelProvider(this).get(EventoViewModel.class);

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Este es el boton para agregar nuevos ventanas
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CrearEvento.class);
            startActivityForResult(intent, 7, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });

        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode==7){
            if (resultCode==RESULT_OK){
                tema= data.getStringExtra("Tema de la conferencia");
                fecha= data.getStringExtra("Fecha del evento");
                expositor= data.getStringExtra("Expositor");
                estado= data.getStringExtra("Estado del evento");

                model.insert(new Evento(tema, fecha, expositor,estado));

                String dataEvento=tema+" "+fecha+" "+expositor+" "+estado;

                Snackbar.make(binding.clMain,getString(R.string.mensaje_confirmacion, dataEvento) , Snackbar.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.del_all) {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage(R.string.del_all_event).setTitle(R.string.delete_dialog_title);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    model.deleteAll();
                    Snackbar.make(binding.clMain,getString(R.string.confirmed_msg),Snackbar.LENGTH_LONG).show();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //No hace nada
                }
            });

            AlertDialog dialog=builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}