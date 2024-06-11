package br.com.jvn.appgaseta.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.UUID;

public class GasEtaDBCore extends SQLiteOpenHelper {
    public static final String NOME_DB ="gaseta.db";
    static final int VERSAO_DB = 1;

    public GasEtaDBCore(Context context){
        super(context,NOME_DB,null,VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "create table tbconfig (" +
                "idconfig integer primary key," +
                "registrado text, "+
                "chaveprivada text, "+
                "nome text, "+
                "email text, "+
                "telefone text, "+
                "guid text)";
        db.execSQL(sql);

        UUID s = UUID.randomUUID();
        sql = "insert into tbconfig (" +
                "idconfig, " +
                "registrado, "+
                "nome, "+
                "email, "+
                "telefone, "+
                "guid)" +
                "values" +
                "(1," +
                "'N',"+//urlservidor
                "'',"+//nome
                "'',"+//email
                "'',"+//telefone
                "'"+s.toString().replaceAll("-", "")+"')";
        db.execSQL(sql);



        sql = "CREATE TABLE Combustivel(id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "precoGas REAL, "+
                "precoEta REAL, "+
                "razao REAL, "+
                "date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<0){
            try {
                Log.e("Att banco de dados","Atualizar Banco de Dados para nova versão!");
            }catch (Exception e){
                Log.e("Att banco de dados","Falha ao atualizar Banco de Dados.");
            }
        }
    }
}
