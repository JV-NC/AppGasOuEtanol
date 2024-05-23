package br.com.jvn.appgaseta.controller;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;

import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.model.Combustivel;
import br.com.jvn.appgaseta.view.MainActivity;

public class ControllerCombustivel extends GasEtaDB {

    public ControllerCombustivel(MainActivity activity) {
        super(activity);
        Log.d("MVC_Controller","Controladora iniciada!");
    }

    public void salvar(Combustivel combustivel){
        ContentValues dados = new ContentValues();

        dados.put("nomeCombustivel",combustivel.getNome());
        dados.put("precoCombustivel",combustivel.getPreco());
        dados.put("recomendacao",combustivel.getRecomendacao());

        salvarObj("Combustivel",dados);
    }

    public void limpar(){
        limparTabela("Combustivel");
    }

    public ArrayList<Combustivel> getListaDados(){
        return listarDados();
    }

    public void alterar(Combustivel combustivel){
        ContentValues dados = new ContentValues();

        dados.put("id",combustivel.getId());
        dados.put("nomeCombustivel",combustivel.getNome());
        dados.put("precoCombustivel",combustivel.getPreco());
        dados.put("recomendacao",combustivel.getRecomendacao());

        alterarObj("Combustivel",dados);
    }

    public void deletar(int id){
        deletarObj("Combustivel",id);
    }
}
