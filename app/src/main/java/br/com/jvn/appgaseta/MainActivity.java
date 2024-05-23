package br.com.jvn.appgaseta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jvn.appgaseta.apoio.UtilGasEta;

public class MainActivity extends AppCompatActivity {
    EditText tfValorGas;
    EditText tfValorEta;
    Button btnCalcular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, UtilGasEta.calcularMelhorOpcao(Double.parseDouble(tfValorGas.getText().toString()),Double.parseDouble(tfValorEta.getText().toString())),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLayout(){
        tfValorGas = findViewById(R.id.tfValorGas);
        tfValorEta = findViewById(R.id.tfValorEta);
        btnCalcular = findViewById(R.id.btnCalcular);
    }

}