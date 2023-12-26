package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.endereco.BuscaEnderecoPorIdPessoaTask;
import br.com.amar.nos.pontos.database.AmarDatabase;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.databinding.ActivityListaEnderecosBinding;
import br.com.amar.nos.pontos.model.Endereco;
import br.com.amar.nos.pontos.ui.adapter.EnderecoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaEnderecosActivity extends AppCompatActivity {

    private ActivityListaEnderecosBinding viewBind;
    private Long idPessoa = null;
    private EnderecoAdapter adapter;
    private EnderecoDAO enderecoDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityListaEnderecosBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);
        setTitle("Endereços");

        ActionBar supportActionBar = this.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        AmarDatabase db = AmarDatabase.getInstance(this);
        enderecoDAO = db.enderecoDAO();

        Intent intent = getIntent();
        if(intent.hasExtra("idPessoa")) {
            this.idPessoa = intent.getLongExtra("idPessoa", 0);
        }

        FloatingActionButton fab = findViewById(R.id.activity_lista_enderecos_add);

        fab.setOnClickListener((view) -> {
            Intent intentToGo = new Intent(ListaEnderecosActivity.this, FormularioEnderecoActivity.class);
            intentToGo.putExtra("idPessoa", idPessoa);

            startActivity(intentToGo);
        });
    }

    @Override
    protected void onResume() {
        if(adapter == null) {
            new BuscaEnderecoPorIdPessoaTask(idPessoa, enderecoDAO, this::setAdapter).execute();
        } else {
            adapter.atualizarEnderecos();
        }
        super.onResume();
    }

    public void setAdapter(List<Endereco> enderecos) {
        if(idPessoa != null && idPessoa > 0) {
            adapter = new EnderecoAdapter(ListaEnderecosActivity.this, idPessoa, enderecos, enderecoDAO);
            ListView listaEndereco = findViewById(R.id.lista_enderecos);
            listaEndereco.setAdapter(adapter);
            listaEndereco.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent = new Intent(ListaEnderecosActivity.this, FormularioEnderecoActivity.class);
                intent.putExtra("idEndereco", adapter.getItemId(i));
                startActivity(intent);
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
