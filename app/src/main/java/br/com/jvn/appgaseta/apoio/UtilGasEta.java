package br.com.jvn.appgaseta.apoio;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilGasEta {
    public static String calcularMelhorOpcao(double gasolina, double etanol,double razao){ //utiliza uma razão para retornar melhor opção
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

    public static void ShowMessage(Context context, String titulo, String mensagem){ //Mostra mensagem com alert dialog
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

    public static String reformatarData(String origin, String target, String date) { //passa uma data em string de um formato para outro
        SimpleDateFormat sdfOrigin = new SimpleDateFormat(origin);
        SimpleDateFormat sdfTarget = new SimpleDateFormat(target);

        Date data = null;
        try {
            data = sdfOrigin.parse(date);
            if (data != null) {
                return sdfTarget.format(data);
            }
        } catch (ParseException e) {
            Log.e("reformatarData", "Erro ao transformar a data: " + date);
        }
        return null;
    }

    public static boolean assertDecimalEquals(double v1, double v2) {
        double epsilon = 0.000001d;
        return Math.abs(v1 - v2) < epsilon;
    }
}