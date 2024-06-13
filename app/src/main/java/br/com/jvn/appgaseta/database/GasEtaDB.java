package br.com.jvn.appgaseta.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GasEtaDB{
    private SQLiteDatabase db;
    private int versaodb;

    @SuppressWarnings("static-access")
    public GasEtaDB(Context context){
        GasEtaDBCore auxdb = new GasEtaDBCore(context);
        versaodb = auxdb.VERSAO_DB;
        db = auxdb.getWritableDatabase();
    }

    public void fechar(){
        db.close();
    }

    public void runSQL(String sql){
        Log.e("runSQL",sql);
        try {
            db.execSQL(sql);
        }catch (Exception e){
            Log.e("DB","Impossível executar sql: "+sql+ "- Motivo: "+e.getMessage());
        }
    }

    public void runSQLSemLog(String sql){
        try {
            db.execSQL(sql);
        }catch (Exception e){

        }
        Log.i("DB",sql);
    }

    public int getVersaodb(){
        return versaodb;
    }

    public Cursor abreRAWSQL(String sql){
        Log.e("ABRE RAW",sql);

        Cursor cursor = null;

        try {
            cursor = db.rawQuery(sql,null);
        }catch (Exception e){
            Log.e("ABRE RAW","Impossível abrir sql: "+sql+ "- Motivo: "+e.getMessage());
        }

        return cursor;
    }

    public Cursor abreSQL(String table, String[]columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, orderBy);
    }
}