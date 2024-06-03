package br.com.jvn.appgaseta.model;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.apoio.UtilGasEta;

public class CombustivelAdapter extends RecyclerView.Adapter<CombustivelAdapter.CombustivelViewHolder> {
    private final ArrayList<Combustivel> combustiveis;

    public CombustivelAdapter(ArrayList<Combustivel> combustiveis) {
        this.combustiveis = combustiveis;
    }

    @NonNull
    @Override
    public CombustivelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.combustivel_item,parent,false);
        return new CombustivelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CombustivelViewHolder holder, int position) {
        Combustivel combustivel = combustiveis.get(position);
        holder.bind(combustivel);
    }

    @Override
    public int getItemCount() {
        return combustiveis.size();
    }

    class CombustivelViewHolder extends RecyclerView.ViewHolder{
        TextView lblIcon;
        TextView lblNome;
        TextView lblPreco;
        TextView lblRazao;
        TextView lblDate;
        public CombustivelViewHolder(@NonNull View itemView) {
            super(itemView);
            lblIcon = itemView.findViewById(R.id.lblIcon);
            lblNome = itemView.findViewById(R.id.lblNome);
            lblPreco = itemView.findViewById(R.id.lblPreco);
            lblRazao = itemView.findViewById(R.id.lblRazao);
            lblDate = itemView.findViewById(R.id.lblDate);
        }

        public void bind(Combustivel combustivel) {
            DecimalFormat df = new DecimalFormat("#0.00");
            lblIcon.setText(String.valueOf(combustivel.getNome().charAt(0)));
            if(combustivel.getNome().compareTo("Gasolina")==0){
                lblIcon.setBackground(oval(Color.rgb(192,0,0),lblIcon));
            }
            else{
                lblIcon.setBackground(oval(Color.rgb(0,192,0),lblIcon));
            }
            lblNome.setText(combustivel.getNome());
            lblPreco.setText("R$ "+df.format(combustivel.getPreco()));
            lblRazao.setText(df.format(combustivel.getRazao()*100)+"%");
            lblDate.setText(combustivel.getDate());
        }
    }

    private static ShapeDrawable oval(@ColorInt int color, View view){
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.setIntrinsicHeight(view.getHeight());
        shape.setIntrinsicWidth(view.getWidth());
        shape.getPaint().setColor(color);
        return shape;
    }
}
