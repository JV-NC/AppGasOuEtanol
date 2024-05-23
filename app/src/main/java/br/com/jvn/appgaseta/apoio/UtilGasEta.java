package br.com.jvn.appgaseta.apoio;

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
}
