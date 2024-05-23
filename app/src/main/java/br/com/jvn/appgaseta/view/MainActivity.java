package br.com.jvn.appgaseta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.apoio.UtilGasEta;

public class MainActivity extends AppCompatActivity {
    EditText tfValorGas;
    EditText tfValorEta;
    TextView lblResultado;
    Button btnCalcular;
    Button btnLimpar;
    Button btnSalvar;
    Button btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        btnFinalizar = findViewById(R.id.btnFinalizar);
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
                    lblResultado.setText(UtilGasEta.calcularMelhorOpcao(Double.parseDouble(tfValorGas.getText().toString()),Double.parseDouble(tfValorEta.getText().toString()),UtilGasEta.PADRAO_70));
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

                btnSalvar.setEnabled(false);
            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}