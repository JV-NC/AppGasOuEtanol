package br.com.jvn.appgaseta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jvn.appgaseta.apoio.UtilGasEta;

public class MainActivity extends AppCompatActivity {
    EditText tfValorGas;
    EditText tfValorEta;
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
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnFinalizar = findViewById(R.id.btnFinalizar);
    }

    private void setButtons(){
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(tfValorGas.getText()) && !TextUtils.isEmpty(tfValorEta.getText())){
                    Toast.makeText(MainActivity.this, UtilGasEta.calcularMelhorOpcao(Double.parseDouble(tfValorGas.getText().toString()),Double.parseDouble(tfValorEta.getText().toString()),UtilGasEta.PADRAO_70),Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tfValorGas.setText("");
                tfValorEta.setText("");
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