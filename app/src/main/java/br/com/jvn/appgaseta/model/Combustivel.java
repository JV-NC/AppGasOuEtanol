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
    private double razao;
    private String date;

    public Combustivel() {
    }

    public Combustivel(String nome, double preco, double razao) {
        setNome(nome);
        setPreco(preco);
        setRazao(razao);
    }

    public Combustivel(int id, String nome, double preco, double razao) {
        setId(id);
        setNome(nome);
        setPreco(preco);
        setRazao(razao);
    }

    protected Combustivel(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        preco = in.readDouble();
        razao = in.readDouble();
        date = in.readString();
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

    public double getRazao() {
        return razao;
    }

    public void setRazao(Double razao) {
        this.razao = (razao>=0.0) ? razao : 0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public void setDate(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
        this.date = df.parse(date);
    }*/

    @NonNull
    @Override
    public String toString() {
        return nome+", R$"+String.valueOf(preco)+", Razão Gas/Eta: "+razao+", "+date;
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
        dest.writeDouble(razao);
        dest.writeString(date);
    }
}
