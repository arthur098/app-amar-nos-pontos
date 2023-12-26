package br.com.amar.nos.pontos.ui.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.AmarDatabase;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.databinding.ActivityListaContratoBinding;
import br.com.amar.nos.pontos.ui.view.ListaContratoView;

public class ListaContratoActivity extends AppCompatActivity {

    private ActivityListaContratoBinding viewBind;

    private ListaContratoView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityListaContratoBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);
        setTitle("Contratos");

        ActionBar supportActionBar = this.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        Long idPessoa = getIntent().getLongExtra("idPessoa", 0);

        AmarDatabase db = AmarDatabase.getInstance(this);
        ContratoDAO contratoDAO = db.contratoDAO();
        PessoaDAO pessoaDAO = db.pessoaDAO();
        EnderecoDAO enderecoDAO = db.enderecoDAO();

        view = new ListaContratoView(contratoDAO, pessoaDAO, enderecoDAO, idPessoa);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.view.initAdapter(ListaContratoActivity.this, viewBind);
        registerForContextMenu(viewBind.listaContratos);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        this.view.contextItemSelected(ListaContratoActivity.this, item);
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_contrato_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
