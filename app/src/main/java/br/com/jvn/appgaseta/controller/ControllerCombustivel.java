package br.com.jvn.appgaseta.controller;

import android.content.SharedPreferences;
import android.util.Log;

import br.com.jvn.appgaseta.model.Combustivel;
import br.com.jvn.appgaseta.view.MainActivity;

public class ControllerCombustivel {
    SharedPreferences preferences;
    SharedPreferences.Editor dadosPreferences;
    public static final String NOME_PREFERENCES = "pref_gaseta";

    public ControllerCombustivel(MainActivity activity) {
        Log.d("MVC_Controller","Controladora iniciada!");
        preferences = activity.getSharedPreferences(NOME_PREFERENCES,0);
        dadosPreferences = preferences.edit();
    }

    public void salvar(Combustivel combustivel){
        dadosPreferences.putString("combustivel",combustivel.getNome());
        dadosPreferences.putFloat("precoCombustivel",(float)combustivel.getPreco());
        dadosPreferences.putString("recomendacao",combustivel.getRecomendacao());
        dadosPreferences.apply();
    }

    public void limpar(){
        dadosPreferences.clear();
        dadosPreferences.apply();
    }
}
