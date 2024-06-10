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

    public void salvar(int order, int dir, double razao, boolean isPadrao07, boolean isDark){ //salva forma de ordenação e Tema Escuro
        editor.putInt("order",order);
        editor.putInt("direction",dir);
        editor.putFloat("razao",Float.parseFloat(String.valueOf(razao)));
        editor.putBoolean("isPadrao07",isPadrao07);
        editor.putBoolean("isDark",isDark);
        editor.apply();
    }

    public int getOrder(){
        return preferences.getInt("order",0);
    }

    public int getDir(){
        return preferences.getInt("direction",0);
    }

    public double getRazao(){
        return preferences.getFloat("razao",Float.parseFloat("0.70"));
    }

    public boolean getIsPadrao07(){
        return preferences.getBoolean("isPadrao07",true);
    }

    public boolean getDark(){
        return preferences.getBoolean("isDark",false);
    }

    public void limpar(){ //limpa SP
        editor.clear();
        editor.apply();
    }
}
