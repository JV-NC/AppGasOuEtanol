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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.apoio.UtilGasEta;
import br.com.jvn.appgaseta.interfaces.CombustivelAdpterListener;

public class CombustivelAdapter extends RecyclerView.Adapter<CombustivelAdapter.CombustivelViewHolder> {
    private final ArrayList<Combustivel> combustiveis;
    private CombustivelAdpterListener listener;

    public CombustivelAdapter(ArrayList<Combustivel> combustiveis,CombustivelAdpterListener listener) {
        this.combustiveis = combustiveis;
        this.listener = listener;
    }

    public ArrayList<Combustivel> getCombustiveis() {
        return combustiveis;
    }

    public int removeCombustivel(int position){
        int id = -1;
        if(position>=0){
            id = combustiveis.get(position).getId();
            combustiveis.remove(position);
        }
        return id;
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
        holder.bind(combustivel,position);

        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return combustiveis.size();
    }

    class CombustivelViewHolder extends RecyclerView.ViewHolder{
        TextView lblIcon;
        TextView lblPrecoGas;
        TextView lblPrecoEta;
        TextView lblRazao;
        TextView lblDate;
        CardView cvContainer;
        public CombustivelViewHolder(@NonNull View itemView) {
            super(itemView);
            lblIcon = itemView.findViewById(R.id.lblIcon);
            lblPrecoGas = itemView.findViewById(R.id.lblPrecoGas);
            lblPrecoEta = itemView.findViewById(R.id.lblPrecoEta);
            lblRazao = itemView.findViewById(R.id.lblRazao);
            lblDate = itemView.findViewById(R.id.lblDate);
            cvContainer = itemView.findViewById(R.id.cvContainer);
        }

        public void bind(Combustivel combustivel, int position) {
            DecimalFormat df = new DecimalFormat("#0.00");
            lblIcon.setText(String.valueOf(position+1));
            if(position%2==0){
                lblIcon.setBackground(oval(Color.rgb(192,0,0),lblIcon));
            }
            else{
                lblIcon.setBackground(oval(Color.rgb(0,192,0),lblIcon));
            }
            lblPrecoGas.setText("Gas: R$ "+df.format(combustivel.getPrecoGas()));
            lblPrecoEta.setText("Eta: R$ "+df.format(combustivel.getPrecoEta()));
            lblRazao.setText("Eta/Gas: "+df.format(combustivel.getRazao()*100)+"%");
            //formato da data
            lblDate.setText(UtilGasEta.reformatarData("yyyy/MM/dd HH:mm:ss","dd/MM/yyyy HH:mm:ss",combustivel.getDate()));
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
