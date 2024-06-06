package br.com.jvn.appgaseta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.model.Combustivel;

public class UpdateActivity extends AppCompatActivity {
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

        setLayout(combustivel);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyTextFields()){
                    combustivel.setNome(tfNome.getText().toString());
                    combustivel.setPreco(Double.parseDouble(tfPreco.getText().toString()));
                    combustivel.setRazao(Double.parseDouble(tfRazao.getText().toString()));
                    //TODO: recalcular razao, desativar nome e razao

                    ControllerCombustivel controller = new ControllerCombustivel();
                    GasEtaDB db = new GasEtaDB(UpdateActivity.this);
                    
                    controller.alterar(combustivel,db);
                    ArrayList<Combustivel> list = controller.getListaDados(db);

                    Intent it = new Intent(UpdateActivity.this,RecyclerActivity.class);
                    it.putExtra("Lista",list);
                    startActivity(it);
                    //TODO:Aprender usar nova versão de startActivityforResult

                    finish();
                }
                else {
                    Toast.makeText(UpdateActivity.this, "Por favor, verifique os campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setLayout(Combustivel combustivel){ //set layout views
        tfId = findViewById(R.id.tfId);
        tfNome = findViewById(R.id.tfNome);
        tfPreco = findViewById(R.id.tfPreco);
        tfRazao = findViewById(R.id.tfRazao);
        tfDate = findViewById(R.id.tfDate);

        btnAtualizar = findViewById(R.id.btnAtualizar);

        tfId.setText(String.valueOf(combustivel.getId()));
        tfNome.setText(combustivel.getNome());
        tfPreco.setText(String.valueOf(combustivel.getPreco()));
        tfRazao.setText(String.valueOf(combustivel.getRazao()));
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
        if(TextUtils.isEmpty(tfRazao.getText()) || tfRazao.getText().toString().compareTo(".")==0){
            tfRazao.setError("Obrigatório");
            tfRazao.requestFocus();
            check = false;
        }
        return check;
    }
}