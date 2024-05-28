package br.com.jvn.appgaseta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.apoio.UtilGasEta;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.model.Combustivel;

public class MainActivity extends AppCompatActivity {
    EditText tfValorGas;
    EditText tfValorEta;
    TextView lblResultado;
    Button btnCalcular;
    Button btnLimpar;
    Button btnSalvar;
    Button btnListar;

    ControllerCombustivel controller;
    Combustivel Gas;
    Combustivel Eta;
    String recomendacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new ControllerCombustivel(this);

        //controller.alterar(new Combustivel(4,"Etanol",3.42,"Abastecer com Etanol")); //tst Alterar
        //controller.deletar(4); //tst Deletar
        ArrayList<Combustivel> list = controller.getListaDados(); //tst getLista
        for(int i=0;i<list.size();i++){
            Log.i("Banco de Dados","id: "+list.get(i).getId()+", nome: "+list.get(i).getNome()+", preço: "+list.get(i).getPreco()+", recomendação: "+list.get(i).getRecomendacao());
        }

        setLayout();
        setButtons();
    }

    private void setLayout(){
        tfValorGas = findViewById(R.id.tfValorGas);
        tfValorEta = findViewById(R.id.tfValorEta);
        lblResultado = findViewById(R.id.lblResultado);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnListar = findViewById(R.id.btnListar);
    }

    private void setButtons(){
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean verify = true;

                if(TextUtils.isEmpty(tfValorGas.getText())){
                    tfValorGas.setError("Obrigatório");
                    tfValorGas.requestFocus();
                    verify = false;
                }
                if(TextUtils.isEmpty(tfValorEta.getText())){
                    tfValorEta.setError("Obrigatório");
                    tfValorEta.requestFocus();
                    verify = false;
                }
                if(verify){
                    recomendacao = UtilGasEta.calcularMelhorOpcao(Double.parseDouble(tfValorGas.getText().toString()),Double.parseDouble(tfValorEta.getText().toString()),UtilGasEta.PADRAO_70);

                    Gas = new Combustivel("Gasolina",Double.parseDouble(tfValorGas.getText().toString()),recomendacao);
                    Eta = new Combustivel("Etanol",Double.parseDouble(tfValorEta.getText().toString()),recomendacao);

                    lblResultado.setText(recomendacao);
                    btnSalvar.setEnabled(true);
                }
                else{
                    Toast.makeText(MainActivity.this,"Falha ao Calcular!",Toast.LENGTH_SHORT).show();
                    btnSalvar.setEnabled(false);
                    lblResultado.setText("");
                }
            }
        });
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tfValorGas.setText("");
                tfValorEta.setText("");
                lblResultado.setText("");

                controller.limpar();

                btnSalvar.setEnabled(false);
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.salvar(Gas);
                controller.salvar(Eta);
            }
        });
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Combustivel> list = controller.getListaDados();
                Intent it = new Intent(MainActivity.this,RecyclerActivity.class);
                it.putExtra("Lista",list);
                startActivity(it);
            }
        });
    }
}