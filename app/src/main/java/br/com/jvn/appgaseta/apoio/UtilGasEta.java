package br.com.jvn.appgaseta.apoio;

import android.app.AlertDialog;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilGasEta {
    public static final double PADRAO_70 = 0.7;
    public static String calcularMelhorOpcao(double gasolina, double etanol,double razao){
        //preço ideal = gasolina * razao

        double precoIdeal = gasolina*razao;

        if(etanol<=precoIdeal){
            return "Abastecer com Etanol";
        }
        return "Abastecer com Gasolina";
    }

    public static String retornaData(String formato, Date data, int dias){
        SimpleDateFormat sdf = new SimpleDateFormat(formato);

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DATE,dias);
        return sdf.format(cal.getTime());
    }

    public static void ShowMessage(Context context, String titulo, String mensagem){
        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setPositiveButton("OK",null);
        alerta  =builder.create();
        alerta.show();
    }

    public static double calcularRazao(double v1, double v2){
        return v1/v2;
    }
}
