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
import android.widget.Toast;

import java.text.DecimalFormat;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.apoio.UtilGasEta;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.model.Combustivel;

public class UpdateActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText tfId;
    EditText tfNome;
    EditText tfPreco;
    EditText tfRazao;
    EditText tfDate;
    Button btnAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Combustivel combustivel = getIntent().getParcelableExtra("Combustivel"); //recebe combustivel a alterar;
        Combustivel aux = getIntent().getParcelableExtra("CombustivelAux"); //recebe o aux
        if(aux==null){
            Log.e("ParcelableExtra","aux é nulo");
            finish();
        }

        setLayout(combustivel);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizar(combustivel,aux);
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

        if(id==R.id.itemRecyclerClose){
            callRecycler();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLayout(Combustivel combustivel){ //set layout views
        toolbar = findViewById(R.id.toolbarUpdate);
        setSupportActionBar(toolbar);

        tfId = findViewById(R.id.tfId);
        tfNome = findViewById(R.id.tfNome);
        tfPreco = findViewById(R.id.tfPreco);
        tfRazao = findViewById(R.id.tfRazao);
        tfDate = findViewById(R.id.tfDate);

        btnAtualizar = findViewById(R.id.btnAtualizar);

        DecimalFormat df = new DecimalFormat("#0.00"); //formatar valores com decimais

        tfId.setText(String.valueOf(combustivel.getId()));
        tfNome.setText(combustivel.getNome());
        tfPreco.setText(df.format(combustivel.getPreco()));
        tfRazao.setText(df.format(combustivel.getRazao()));
        tfDate.setText(combustivel.getDate());
    }

    private boolean verifyTextFields(){
        boolean check = true;
        if(TextUtils.isEmpty(tfNome.getText())){
            tfNome.setError("Obrigatório");
            tfNome.requestFocus();
            check = false;
        }
        if(TextUtils.isEmpty(tfPreco.getText()) || tfPreco.getText().toString().compareTo(".")==0){
            tfPreco.setError("Obrigatório");
            tfPreco.requestFocus();
            check = false;
        }
        return check;
    }

    private double calculaRazao(Combustivel com1, Combustivel com2) {
        if(com1.getId()%2==0){
            return UtilGasEta.calcularRazao(com1.getPreco(),com2.getPreco());
        }
        return UtilGasEta.calcularRazao(com2.getPreco(),com1.getPreco());
    }

    private void callRecycler(){
        Intent it = new Intent(UpdateActivity.this,RecyclerActivity.class);;
        startActivity(it);
    }

    private void atualizar(Combustivel combustivel, Combustivel aux){
        if (verifyTextFields()){
            //combustivel.setNome(tfNome.getText().toString());
            combustivel.setPreco(Double.parseDouble(tfPreco.getText().toString()));

            double razao = calculaRazao(combustivel,aux);

            combustivel.setRazao(razao);
            aux.setRazao(razao);

            ControllerCombustivel controller = new ControllerCombustivel();
            GasEtaDB db = new GasEtaDB(UpdateActivity.this);

            controller.alterar(combustivel,db);
            controller.alterar(aux,db);

            callRecycler();

            finish();
        }
        else {
            Toast.makeText(UpdateActivity.this, "Por favor, verifique os campos!", Toast.LENGTH_SHORT).show();
        }
    }
}