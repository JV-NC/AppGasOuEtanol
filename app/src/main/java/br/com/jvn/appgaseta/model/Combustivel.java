package br.com.jvn.appgaseta.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Combustivel implements Parcelable {
    private int id;
    private String nome;
    private double preco;
    private String recomendacao;
    private Date date;

    public Combustivel() {
    }

    public Combustivel(String nome, double preco, String recomendacao) {
        setNome(nome);
        setPreco(preco);
        setRecomendacao(recomendacao);
    }

    public Combustivel(int id, String nome, double preco, String recomendacao) {
        setId(id);
        setNome(nome);
        setPreco(preco);
        setRecomendacao(recomendacao);
    }

    protected Combustivel(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        preco = in.readDouble();
        recomendacao = in.readString();
    }

    public static final Creator<Combustivel> CREATOR = new Creator<Combustivel>() {
        @Override
        public Combustivel createFromParcel(Parcel in) {
            return new Combustivel(in);
        }

        @Override
        public Combustivel[] newArray(int size) {
            return new Combustivel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
        this.date = df.parse(date);
    }

    @NonNull
    @Override
    public String toString() {
        return nome+", R$"+String.valueOf(preco)+", "+recomendacao+", "+date.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeDouble(preco);
        dest.writeString(recomendacao);
    }
}
