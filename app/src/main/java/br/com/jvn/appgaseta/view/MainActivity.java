package br.com.jvn.appgaseta.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.apoio.UtilGasEta;
import br.com.jvn.appgaseta.controller.ConfigController;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.model.Combustivel;

public class MainActivity extends AppCompatActivity {
    ConfigController config;
    Toolbar toolbar;
    BottomAppBar bottomAppBar;
    TextView lblPadrao;
    EditText tfValorGas;
    EditText tfValorEta;
    TextView lblResultado;
    Button btnCalcular;
    boolean canSave = false;

    GasEtaDB db;
    ControllerCombustivel controller;
    Combustivel combustivel;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActivityResultLauncher();

        db = new GasEtaDB(MainActivity.this);
        controller = new ControllerCombustivel();

        ArrayList<Combustivel> list = controller.getListaDados(db,"id"); //tst getLista
        for(int i=0;i<list.size();i++){
            Log.i("Banco de Dados",list.get(i).toString());
        }

        config = new ConfigController(MainActivity.this); //Define o tema da aplicação
        if(config.getDark()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setLayout();
        setButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.itemSettings){ //ConfigActivity
            activityResultLauncher.launch(new Intent(MainActivity.this,ConfigActivity.class));
        } else if(id == R.id.itemAbout) { //AboutActivity
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
        } else if(id == R.id.itemExit) {
            finish();
        }
        return true;
    }

    private void setLayout(){
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        lblPadrao = findViewById(R.id.lblPadrao);
        setLblPadrao();

        bottomAppBar = findViewById(R.id.bottomAppbar);
        tfValorGas = findViewById(R.id.tfValorGas);
        tfValorEta = findViewById(R.id.tfValorEta);
        lblResultado = findViewById(R.id.lblResultado);
        btnCalcular = findViewById(R.id.btnCalcular);
    }

    private void setButtons(){
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id =item.getItemId();

                if(id == R.id.itemBottomLimpar){
                    limpar();
                }
                else if(id == R.id.itemBottomSalvar){
                    if(canSave){
                        salvar();
                        //canSave = false;
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Não é possível salvar", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(id == R.id.itemBottomListar){
                    listar();
                }
                return true;
            }
        });
    }

    private void setLblPadrao(){
        if(config.getIsPadrao07()){
            lblPadrao.setText(R.string.txtPadraoMain);
        }
        else{
            DecimalFormat df = new DecimalFormat("#0.00");
            lblPadrao.setText("Padrão: "+df.format(config.getRazao()*100)+"%");
        }
    }

    private boolean checkTextFields(){
        boolean verify = true; //verifica se os campos estão vazios
        if(TextUtils.isEmpty(tfValorGas.getText()) || tfValorGas.getText().toString().compareTo(".")==0){
            tfValorGas.setError("Obrigatório");
            tfValorGas.requestFocus();
            verify = false;
        }
        if(TextUtils.isEmpty(tfValorEta.getText()) || tfValorEta.getText().toString().compareTo(".")==0){
            tfValorEta.setError("Obrigatório");
            tfValorEta.requestFocus();
            verify = false;
        }
        return verify;
    }

    private void cleanTextFields(){
        tfValorGas.setText("");
        tfValorEta.setText("");
        lblResultado.setText("");
    }

    private void calcular(){
        if(checkTextFields()){
            String recomendacao = UtilGasEta.calcularMelhorOpcao(Double.parseDouble(tfValorGas.getText().toString()),Double.parseDouble(tfValorEta.getText().toString()),config.getRazao());
            Toast.makeText(this, recomendacao, Toast.LENGTH_SHORT).show();
            double razao = UtilGasEta.calcularRazao(Double.parseDouble(tfValorEta.getText().toString()),Double.parseDouble(tfValorGas.getText().toString()));
            DecimalFormat df = new DecimalFormat("#0.00");

            combustivel = new Combustivel(Double.parseDouble(tfValorGas.getText().toString()),Double.parseDouble(tfValorEta.getText().toString()),razao);

            lblResultado.setText(df.format(razao*100)+"%");
            canSave = true;
        }
        else{
            Toast.makeText(MainActivity.this,"Falha ao Calcular!",Toast.LENGTH_SHORT).show();
            canSave = false;
            lblResultado.setText("");
        }
    }

    private void limpar(){
        if(controller.count(db)>0){
            AlertDialog alerta;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Atenção!");
            builder.setMessage("Você está prestes a deletar todos os registros. Deseja continuar?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cleanTextFields();

                    controller.limpar(db);

                    canSave = false;

                    Toast.makeText(MainActivity.this, "Registros deletados com sucesso!", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancelar", null);

            alerta = builder.create();
            alerta.show();
        } else{
            Toast.makeText(MainActivity.this, "Registros vazios!", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvar(){
        if(checkTextFields()){
            String date = UtilGasEta.retornaData("yyyy/MM/dd HH:mm:ss",new Date(),0); //recebe a data do momento atual
            combustivel.setDate(date);

            controller.salvar(combustivel,db);

            Toast.makeText(MainActivity.this,"Valores Salvos com Sucesso!",Toast.LENGTH_SHORT).show();
            canSave = false;

            cleanTextFields();
        }
    }

    private void listar(){
        Intent it = new Intent(MainActivity.this,RecyclerActivity.class);
        startActivity(it);
    }

    private void setActivityResultLauncher(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult activityResult) {
                        /*int result = activityResult.getResultCode();
                        Intent data = activityResult.getData();

                        Comandos usados para obter dados da activity criada
                         */
                        setLblPadrao();
                    }
                }
        );
    }
}