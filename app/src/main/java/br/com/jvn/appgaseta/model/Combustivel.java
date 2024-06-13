package br.com.jvn.appgaseta.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Combustivel implements Parcelable {
    private int id;
    private double precoGas;
    private double precoEta;
    private double razao;
    private String date;

    public Combustivel() {
    }

    public Combustivel(double precoGas, double precoEta, double razao) {
        setPrecoGas(precoGas);
        setPrecoEta(precoEta);
        setRazao(razao);
    }

    protected Combustivel(Parcel in) {
        id = in.readInt();
        precoGas = in.readDouble();
        precoEta = in.readDouble();
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

    public double getPrecoGas() {
        return precoGas;
    }

    public void setPrecoGas(double precoGas) {
        this.precoGas = precoGas;
    }

    public double getPrecoEta() {
        return precoEta;
    }

    public void setPrecoEta(double precoEta) {
        this.precoEta = precoEta;
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

    @NonNull
    @Override
    public String toString() {
        return "Gasolina: R$"+String.valueOf(precoGas)+", Etanol: "+String.valueOf(precoEta)+", Razão Gas/Eta: "+razao+", "+date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(precoGas);
        dest.writeDouble(precoEta);
        dest.writeDouble(razao);
        dest.writeString(date);
    }
}
