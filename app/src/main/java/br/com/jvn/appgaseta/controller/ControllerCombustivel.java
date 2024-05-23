package br.com.jvn.appgaseta.controller;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.model.Combustivel;
import br.com.jvn.appgaseta.view.MainActivity;

public class ControllerCombustivel extends GasEtaDB {
    SharedPreferences preferences;
    SharedPreferences.Editor dadosPreferences;
    private static final String NOME_PREFERENCES = "pref_gaseta";

    public ControllerCombustivel(MainActivity activity) {
        super(activity);
        Log.d("MVC_Controller","Controladora iniciada!");
        preferences = activity.getSharedPreferences(NOME_PREFERENCES,0);
        dadosPreferences = preferences.edit();
    }

    public void salvar(Combustivel combustivel){
        ContentValues dados = new ContentValues();

        dados.put("nomeCombustivel",combustivel.getNome());
        dados.put("precoCombustivel",combustivel.getPreco());
        dados.put("recomendacao",combustivel.getRecomendacao());

        dadosPreferences.putString("combustivel",combustivel.getNome());
        dadosPreferences.putFloat("precoCombustivel",(float)combustivel.getPreco());
        dadosPreferences.putString("recomendacao",combustivel.getRecomendacao());
        dadosPreferences.apply();

        salvarObj("Combustivel",dados);
    }

    public void limpar(){
        dadosPreferences.clear();
        dadosPreferences.apply();
    }

    public ArrayList<Combustivel> getListaDados(){
        return listarDados();
    }
}
