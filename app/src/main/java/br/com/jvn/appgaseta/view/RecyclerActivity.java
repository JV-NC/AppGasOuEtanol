package br.com.jvn.appgaseta.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.controller.ConfigController;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.interfaces.CombustivelAdpterListener;
import br.com.jvn.appgaseta.model.Combustivel;
import br.com.jvn.appgaseta.model.CombustivelAdapter;

public class RecyclerActivity extends AppCompatActivity {
    Toolbar toolbar;
    ControllerCombustivel controller;
    GasEtaDB db;
    RecyclerView rv;
    CombustivelAdapter combustivelAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);

        controller = new ControllerCombustivel();
        db = new GasEtaDB(RecyclerActivity.this);
        ConfigController config = new ConfigController(RecyclerActivity.this);
        String order;
        if(config.getOrder()==0){
            order = "id";
        }
        else{
            order = "razao DESC";
        }

        ArrayList<Combustivel> list = controller.getListaDados(db,order);

        if(list.size()==0){
            Log.e("List DB","Lista Vazia");
        }

        toolbar = findViewById(R.id.toolbarList);
        setSupportActionBar(toolbar);


        rv = findViewById(R.id.recyclerGasEta);

        combustivelAdapter = new CombustivelAdapter(list, new CombustivelAdpterListener() {
            @Override
            public void onItemClick(int position) { // ao clickar no item
                Intent it = new Intent(RecyclerActivity.this,UpdateActivity.class);
                it.putExtra("Combustivel",combustivelAdapter.getCombustiveis().get(position));

                int aux;
                if(combustivelAdapter.getCombustiveis().get(position).getId()%2==0){
                    aux=position-1;
                }
                else{
                    aux=position+1;
                }
                it.putExtra("CombustivelAux",combustivelAdapter.getCombustiveis().get(aux));

                startActivity(it);
                finish();
            }
        });

        rv.setAdapter(combustivelAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHandler(0,ItemTouchHelper.LEFT));
        helper.attachToRecyclerView(rv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_recycler_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.itemRecyclerClose){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback{

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            int id = combustivelAdapter.removeCombustivel(pos);
            combustivelAdapter.notifyItemRemoved(pos);
            if(id!=-1){
                controller.deletar(id,db);

                int aux;
                if(id%2==0){
                    aux = pos-1;
                }
                else {
                    aux = pos;
                }

                id = combustivelAdapter.removeCombustivel(aux);
                combustivelAdapter.notifyItemRemoved(aux);
                if(id!=-1){
                    controller.deletar(id,db);
                }
            }
        }
    }
}