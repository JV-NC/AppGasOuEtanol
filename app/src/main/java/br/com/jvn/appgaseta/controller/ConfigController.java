package br.com.jvn.appgaseta.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ConfigController {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static final String NOME_PREFERENCES = "pref_config";
    public ConfigController(Context context){ //cria/acessa SharedPreferences
        Log.i("Config Controller","Controladora Iniciada!");
        preferences = context.getSharedPreferences(NOME_PREFERENCES,0);
        editor =preferences.edit();
    }

    public void salvar(int order, boolean isDark){ //salva forma de ordenação e Tema Escuro
        editor.putInt("order",order);
        editor.putBoolean("isDark",isDark);
        editor.apply();
    }

    public int getOrder(){
        return preferences.getInt("order",0);
    }

    public boolean getDark(){
        return preferences.getBoolean("isDark",false);
    }

    public void limpar(){ //limpa SP
        editor.clear();
        editor.apply();
    }
}
