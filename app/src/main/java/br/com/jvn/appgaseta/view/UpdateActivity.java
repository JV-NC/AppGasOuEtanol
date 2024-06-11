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
    EditText tfPrecoGas;
    EditText tfPrecoEta;
    EditText tfRazao;
    EditText tfDate;
    Button btnAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Combustivel combustivel = getIntent().getParcelableExtra("Combustivel"); //recebe combustivel a alterar;
        if(combustivel==null){
            Log.e("ParcelableExtra","combustivel parsed é nulo");
            finish();
        }

        setLayout(combustivel);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizar(combustivel);
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
        tfPrecoGas = findViewById(R.id.tfPrecoGas);
        tfPrecoEta = findViewById(R.id.tfPrecoEta);
        tfRazao = findViewById(R.id.tfRazao);
        tfDate = findViewById(R.id.tfDate);

        btnAtualizar = findViewById(R.id.btnAtualizar);

        DecimalFormat df = new DecimalFormat("#0.00"); //formatar valores com decimais

        tfId.setText(String.valueOf(combustivel.getId()));
        tfPrecoGas.setText(df.format(combustivel.getPrecoGas()));
        tfPrecoEta.setText(df.format(combustivel.getPrecoEta()));
        tfRazao.setText(df.format(combustivel.getRazao()));
        tfDate.setText(combustivel.getDate());
    }

    private boolean verifyTextFields(){
        boolean check = true;
        if(TextUtils.isEmpty(tfPrecoGas.getText()) || tfPrecoGas.getText().toString().compareTo(".")==0){
            tfPrecoGas.setError("Obrigatório");
            tfPrecoGas.requestFocus();
            check = false;
        }
        if(TextUtils.isEmpty(tfPrecoEta.getText()) || tfPrecoEta.getText().toString().compareTo(".")==0){
            tfPrecoEta.setError("Obrigatório");
            tfPrecoEta.requestFocus();
            check = false;
        }
        return check;
    }

    private void callRecycler(){
        Intent it = new Intent(UpdateActivity.this,RecyclerActivity.class);;
        startActivity(it);
    }

    private void atualizar(Combustivel combustivel){
        if (verifyTextFields()){
            combustivel.setPrecoGas(Double.parseDouble(tfPrecoGas.getText().toString()));
            combustivel.setPrecoEta(Double.parseDouble(tfPrecoEta.getText().toString()));

            double razao = UtilGasEta.calcularRazao(combustivel.getPrecoEta(),combustivel.getPrecoGas()); //calcula razao entre os novos valores

            combustivel.setRazao(razao);

            ControllerCombustivel controller = new ControllerCombustivel();
            GasEtaDB db = new GasEtaDB(UpdateActivity.this);

            controller.alterar(combustivel,db);

            callRecycler();

            finish();
        }
        else {
            Toast.makeText(UpdateActivity.this, "Por favor, verifique os campos!", Toast.LENGTH_SHORT).show();
        }
    }
}