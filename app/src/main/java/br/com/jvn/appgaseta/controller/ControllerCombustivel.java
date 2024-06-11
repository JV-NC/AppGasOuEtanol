package br.com.jvn.appgaseta.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.model.Combustivel;

public class ControllerCombustivel{

    public ControllerCombustivel() {
        Log.d("MVC_Controller","Controladora iniciada!");
    }

    public void salvar(Combustivel combustivel,GasEtaDB db){
        String sql = "insert into Combustivel " +
                "(precoGas, " +
                "precoEta, " +
                "razao, " +
                "date)" +
                "values (" +
                "'"+String.valueOf(combustivel.getPrecoGas())+"', " +
                "'"+String.valueOf(combustivel.getPrecoEta())+"', " +
                "'"+String.valueOf(combustivel.getRazao())+"', " +
                "'"+ combustivel.getDate()+"')";

        db.runSQL(sql);
    }

    public void limpar(GasEtaDB db){
        db.runSQL("DELETE FROM Combustivel");
    }

    public ArrayList<Combustivel> getListaDados(GasEtaDB db,String order){
        ArrayList<Combustivel> list = new ArrayList<>();

        Combustivel registro;
        String sql  = "SELECT * FROM Combustivel order by "+order;

        Cursor cursor = db.abreRAWSQL(sql);

        if(cursor.getCount()>0){
            if (cursor.moveToFirst()){
                do{
                    registro = new Combustivel();
                    registro.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));

                    registro.setPrecoGas(cursor.getDouble(cursor.getColumnIndexOrThrow("precoGas")));
                    registro.setPrecoEta(cursor.getDouble(cursor.getColumnIndexOrThrow("precoEta")));
                    registro.setRazao(cursor.getDouble(cursor.getColumnIndexOrThrow("razao")));
                    registro.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));

                    list.add(registro);
                }while(cursor.moveToNext());
            }
        }

        return list;
    }

    public Combustivel getItem(int id, GasEtaDB db){
        Combustivel combustivel = new Combustivel();

        String sql = "SELECT FROM Combustivel WHERE id = '"+id+"'";

        Cursor cursor = db.abreRAWSQL(sql);

        combustivel.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        combustivel.setPrecoGas(cursor.getDouble(cursor.getColumnIndexOrThrow("precoGas")));
        combustivel.setPrecoEta(cursor.getDouble(cursor.getColumnIndexOrThrow("precoEta")));
        combustivel.setRazao(cursor.getDouble(cursor.getColumnIndexOrThrow("razao")));
        combustivel.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));

        return combustivel;
    }

    public void alterar(Combustivel combustivel, GasEtaDB db){
        ContentValues dados = new ContentValues();

        dados.put("id",combustivel.getId());
        dados.put("precoGas",combustivel.getPrecoGas());
        dados.put("precoEta",combustivel.getPrecoEta());
        dados.put("razao",combustivel.getRazao());
        dados.put("date",combustivel.getDate());

        String sql = "UPDATE Combustivel " +
                "SET precoGas = '"+ combustivel.getPrecoGas() + "', " +
                "precoEta = '"+ combustivel.getPrecoEta() +"', "+
                "razao = '"+ combustivel.getRazao() +"', " +
                "date = '"+ combustivel.getDate() +"' "+
                "WHERE id = '"+combustivel.getId()+"'";

        db.runSQL(sql);
    }

    public void deletar(int id, GasEtaDB db){
        String sql = "DELETE FROM Combustivel WHERE id = '"+id+"'";

        db.runSQL(sql);
    }

    public int count(GasEtaDB db){
        String sql = "SELECT * FROM Combustivel";
        Cursor cursor = db.abreRAWSQL(sql);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
