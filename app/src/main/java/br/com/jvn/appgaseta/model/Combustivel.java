package br.com.jvn.appgaseta.model;

public class Combustivel {
    private String nome;
    private double preco;
    private String recomendacao;

    public Combustivel(String nome, double preco,String recomendacao) {
        setNome(nome);
        setPreco(preco);
        setRecomendacao(recomendacao);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao;
    }
}
