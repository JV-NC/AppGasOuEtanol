package br.com.jvn.appgaseta.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.util.ArrayList;
import java.util.Date;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.apoio.UtilGasEta;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.model.Combustivel;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomAppBar bottomAppBar;
    EditText tfValorGas;
    EditText tfValorEta;
    TextView lblResultado;
    Button btnCalcular;
    boolean canSave = false;

    GasEtaDB db;
    ControllerCombustivel controller;
    Combustivel Gas;
    Combustivel Eta;
    String recomendacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new GasEtaDB(MainActivity.this);
        controller = new ControllerCombustivel();

        //TODO: Testar uptade e delete
        ArrayList<Combustivel> list = controller.getListaDados(db); //tst getLista
        for(int i=0;i<list.size();i++){
            Log.i("Banco de Dados",list.get(i).toString());
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

        if(id == R.id.itemSettings){
            Toast.makeText(MainActivity.this,"Ir para Settings",Toast.LENGTH_SHORT).show();
        } else if(id == R.id.itemAbout) {
            startActivity(new Intent(MainActivity.this,AboutActivity.class));

        } else if(id == R.id.itemExit) {
            finish();
        }
        return true;
    }

    private void setLayout(){
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

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

    private void calcular(){
        if(checkTextFields()){
            recomendacao = UtilGasEta.calcularMelhorOpcao(Double.parseDouble(tfValorGas.getText().toString()),Double.parseDouble(tfValorEta.getText().toString()),UtilGasEta.PADRAO_70);
            double razao = UtilGasEta.calcularRazao(Double.parseDouble(tfValorEta.getText().toString()),Double.parseDouble(tfValorGas.getText().toString()));

            Gas = new Combustivel("Gasolina",Double.parseDouble(tfValorGas.getText().toString()),razao);
            Eta = new Combustivel("Etanol",Double.parseDouble(tfValorEta.getText().toString()),razao);

            lblResultado.setText(recomendacao);
            canSave = true;
        }
        else{
            Toast.makeText(MainActivity.this,"Falha ao Calcular!",Toast.LENGTH_SHORT).show();
            canSave = false;
            lblResultado.setText("");
        }
    }

    private void limpar(){
        tfValorGas.setText("");
        tfValorEta.setText("");
        lblResultado.setText("");

        controller.limpar(db);

        canSave = false;
    }

    private void salvar(){
        if(checkTextFields()){
            String date = UtilGasEta.retornaData("yyyy/MM/dd HH:mm:ss",new Date(),0); //recebe a data do momento atual
            Gas.setDate(date);
            Eta.setDate(date);

            controller.salvar(Gas,db);
            controller.salvar(Eta,db);

            Toast.makeText(MainActivity.this,"Valores Salvos com Sucesso!",Toast.LENGTH_SHORT).show();
            canSave = false;

        }
    }

    private void listar(){
        ArrayList<Combustivel> list = controller.getListaDados(db);
        for(int i=0;i<list.size();i++){
            Log.i("Banco de Dados",list.get(i).toString());

        }
        Intent it = new Intent(MainActivity.this,RecyclerActivity.class);
        it.putExtra("Lista",list);
        startActivity(it);
    }
}