package br.com.jvn.appgaseta.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.controller.ConfigController;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.database.GasEtaDB;

public class ConfigActivity extends AppCompatActivity {
    ControllerCombustivel controller;
    GasEtaDB db;
    ConfigController config;

    Toolbar toolbar;
    Spinner spinnerOrder;
    CheckBox cbTheme;
    Button btnDelete, btnSalvar, btnStandard;

    boolean isDark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        
        controller = new ControllerCombustivel();
        db = new GasEtaDB(ConfigActivity.this);
        config = new ConfigController(ConfigActivity.this);

        setLayout();

        spinnerOrder.setSelection(config.getOrder());

        isDark = config.getDark();
        cbTheme.setChecked(isDark);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar();
            }
        });

        btnStandard.setOnClickListener(new View.OnClickListener() { //reset configurações
            @Override
            public void onClick(View v) {
                resetConfig();
            }
        });
        
        btnSalvar.setOnClickListener(new View.OnClickListener() { //salvar configurações
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_recycler_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.itemRecyclerClose){ //fechar Activity
            close();
        }
        return true;
    }

    private void setLayout(){
        toolbar = findViewById(R.id.toolbarConfig);
        setSupportActionBar(toolbar);

        spinnerOrder = findViewById(R.id.spinnerOrder);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ConfigActivity.this,R.array.Ordenacao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerOrder.setAdapter(adapter);
        //set adapter com array de strings

        cbTheme = findViewById(R.id.cbTheme);
        btnDelete = findViewById(R.id.btnDelete);
        btnStandard = findViewById(R.id.btnStandard);
        btnSalvar = findViewById(R.id.btnSalvar);
    }

    private void close(){
        if(config.getOrder()!=spinnerOrder.getSelectedItemPosition() || config.getDark()!=cbTheme.isChecked()){
            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfigActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Atenção!");
            builder.setMessage("As alterações feitas serão perdidas. Deseja continuar?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Alterações são descartadas
                    finish();
                }
            });

            builder.setNegativeButton("Cancelar", null);

            alerta = builder.create();
            alerta.show();
        }
        else {
            finish();
        }
    }

    private void deletar(){
        if(controller.count(db)>0){
            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfigActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Atenção!");
            builder.setMessage("Você está prestes a deletar todos os registros. Deseja continuar?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    controller.limpar(db);

                    Toast.makeText(ConfigActivity.this, "Registros deletados com sucesso!", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancelar", null);

            alerta = builder.create();
            alerta.show();
        } else{
            Toast.makeText(ConfigActivity.this, "Registros vazios!", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvar(){
        int position = spinnerOrder.getSelectedItemPosition();
        boolean isDarkNovo = cbTheme.isChecked();

        config.salvar(position,isDarkNovo);

        if(isDarkNovo){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        Log.i("SharedPreferences", config.getOrder() + ", DarkMode: " + config.getDark());
        Toast.makeText(ConfigActivity.this, "Configurações salvas!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void standardConfig(){
        config.limpar();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        spinnerOrder.setSelection(0);
        cbTheme.setChecked(false);
    }

    private void resetConfig(){
        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfigActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Atenção!");
        builder.setMessage("As configurações atuais serão substituidas. Deseja continuar?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                standardConfig();
            }
        });

        builder.setNegativeButton("Cancelar", null);

        alerta = builder.create();
        alerta.show();
    }
}