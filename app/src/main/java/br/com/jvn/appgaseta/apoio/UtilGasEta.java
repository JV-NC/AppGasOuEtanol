package br.com.jvn.appgaseta.apoio;

public class UtilGasEta {
    public static String calcularMelhorOpcao(double gasolina, double etanol){
        //preço ideal = gasolina * 0,7

        double precoIdeal = gasolina*0.7;

        if(etanol<=precoIdeal){
            return "Abastecer com Etanol";
        }
        return "Abastecer com Gasolina";
    }
}
