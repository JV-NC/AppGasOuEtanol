package br.com.jvn.appgaseta.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.model.Combustivel;
import br.com.jvn.appgaseta.model.CombustivelAdapter;

public class RecyclerActivity extends AppCompatActivity {
ArrayList<Combustivel> list;
RecyclerView rv;
FloatingActionButton fabSair;
CombustivelAdapter combustivelAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);

        list = getIntent().getParcelableArrayListExtra("Lista");
        ArrayList<String> stringList = getIntent().getStringArrayListExtra("stringList");
        for (int i=0;i<list.size();i++){
            try {
                list.get(i).setDate(stringList.get(i));
            } catch (ParseException e) {
                Log.e("Erro nas listas",stringList.get(i));
            }
        }

        rv = findViewById(R.id.recyclerGasEta);
        fabSair = findViewById(R.id.fabSair);

        combustivelAdapter = new CombustivelAdapter(list);

        rv.setAdapter(combustivelAdapter);
        fabSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}