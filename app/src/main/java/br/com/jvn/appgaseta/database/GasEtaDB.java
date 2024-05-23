package br.com.jvn.appgaseta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.com.jvn.appgaseta.model.Combustivel;

public class GasEtaDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "gaseta.db";
    private static final int DB_VERSION = 1;
    Cursor cursor;
    SQLiteDatabase db;

    public GasEtaDB(Context context) {
        super(context,DB_NAME,null,DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Querry SQL criar tabela
        String sqlTabelaCombustivel = "CREATE TABLE Combustivel(id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nomeCombustivel TEXT, "+
                "precoCombustivel REAL, "+
                "recomendacao TEXT)";

        db.execSQL(sqlTabelaCombustivel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvarObj(String tabela, ContentValues dados){
        db.insert(tabela,null,dados);
    }

    public ArrayList<Combustivel> listarDados(){
        ArrayList<Combustivel> list = new ArrayList<>();

        Combustivel registro;
        String querrySQL = "SELECT * FROM Combustivel";

        cursor = db.rawQuery(querrySQL,null);

        if(cursor.moveToFirst()){
            do {
                registro = new Combustivel();
                registro.setId(cursor.getInt(0));
                registro.setNome(cursor.getString(1));
                registro.setPreco(cursor.getDouble(2));
                registro.setRecomendacao(cursor.getString(3));

                list.add(registro);
            }while(cursor.moveToNext());
        }

        return list;
    }
}
