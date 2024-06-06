package br.com.jvn.appgaseta.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;

import br.com.jvn.appgaseta.R;
import br.com.jvn.appgaseta.controller.ControllerCombustivel;
import br.com.jvn.appgaseta.database.GasEtaDB;
import br.com.jvn.appgaseta.interfaces.CombustivelAdpterListener;
import br.com.jvn.appgaseta.model.Combustivel;
import br.com.jvn.appgaseta.model.CombustivelAdapter;

public class RecyclerActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<Combustivel> list;
    RecyclerView rv;
    CombustivelAdapter combustivelAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);

        list = getIntent().getParcelableArrayListExtra("Lista");
        if(list.size()==0){
            Log.e("List Parse","Lista Vazia");
        }
        toolbar = findViewById(R.id.toolbarList);
        setSupportActionBar(toolbar);


        rv = findViewById(R.id.recyclerGasEta);

        combustivelAdapter = new CombustivelAdapter(list, new CombustivelAdpterListener() {
            @Override
            public void onItemClick(int position) { // ao clickar no item
                Toast.makeText(RecyclerActivity.this, String.valueOf(list.get(position).getId()), Toast.LENGTH_SHORT).show();
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
            int id= combustivelAdapter.removeCombustivel(viewHolder.getAdapterPosition());
            combustivelAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            if(id!=-1){
                ControllerCombustivel controller = new ControllerCombustivel();
                GasEtaDB db = new GasEtaDB(RecyclerActivity.this);
                controller.deletar(id,db);
            }
        }
    }
}