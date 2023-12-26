package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.AmarDatabase;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.databinding.ActivityListaPessoasBinding;
import br.com.amar.nos.pontos.ui.view.ListaPessoaView;

public class ListaPessoasActivity extends AppCompatActivity {
    private ActivityListaPessoasBinding viewBind;
    private ContratoDAO contratoDAO;
    private EnderecoDAO enderecoDAO;
    private ListaPessoaView view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBind = ActivityListaPessoasBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        AmarDatabase db = AmarDatabase.getInstance(this);
        this.contratoDAO = db.contratoDAO();
        this.enderecoDAO = db.enderecoDAO();
        this.view = new ListaPessoaView(ListaPessoasActivity.this, db.pessoaDAO(), viewBind);

        viewBind.fab.setOnClickListener((view) -> startActivity(new Intent(ListaPessoasActivity.this, FormularioPessoaActivity.class)));

    }

    @Override
    protected void onResume() {
        this.view.setAdapterList();
        registerForContextMenu(viewBind.listaPessoas);
        super.onResume();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        this.view.onContextConfig(item, ListaPessoasActivity.this, view, contratoDAO, enderecoDAO);
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_pessoas_menu, menu);
    }
}
