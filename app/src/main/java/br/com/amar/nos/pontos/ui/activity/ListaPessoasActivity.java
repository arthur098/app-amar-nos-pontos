package br.com.amar.nos.pontos.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.enumerator.EnumEstadoCivil;
import br.com.amar.nos.pontos.model.Pessoa;
import br.com.amar.nos.pontos.ui.adapter.PessoaAdapter;
import br.com.amar.nos.pontos.databinding.ActivityListaPessoasBinding;

public class ListaPessoasActivity extends AppCompatActivity {

    PessoaAdapter pessoaAdapter = new PessoaAdapter(this);
    private ActivityListaPessoasBinding viewBind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBind = ActivityListaPessoasBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        Pessoa pessoa = new Pessoa();
        pessoa.setNomeCompleto("Arthur Oliveira Rodrigues");
        pessoa.setEstadoCivil(EnumEstadoCivil.CASADO);
        pessoa.setNacionalidade("Brasileiro");
        pessoa.setProfissao("Programador");
        pessoa.setCpfCnpj("70302652116");

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNomeCompleto("Matheus Oliveira Rodrigues");
        pessoa2.setEstadoCivil(EnumEstadoCivil.SOLTEIRO);
        pessoa2.setNacionalidade("Brasileiro");
        pessoa2.setProfissao("Programador");
        pessoa2.setCpfCnpj("11122233345");

        PessoaDAO.save(pessoa, pessoa2);

        viewBind.fab.setOnClickListener((view) -> startActivity(new Intent(ListaPessoasActivity.this, FormularioPessoaActivity.class)));

        setListViewPessoas();
    }

    @Override
    protected void onResume() {
        pessoaAdapter.atualizaPessoa();
        super.onResume();
    }

    private void setListViewPessoas() {
        viewBind.listaPessoas.setAdapter(pessoaAdapter);
        viewBind.listaPessoas.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            Intent intent = new Intent(ListaPessoasActivity.this, ListaContratoActivity.class);
            intent.putExtra("idPessoa", pessoaAdapter.getItemId(i));

            startActivity(intent);
        });
        registerForContextMenu(viewBind.listaPessoas);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        Pessoa pessoa = pessoaAdapter.getItem(menuInfo.position);
        if(item.getItemId() == R.id.activity_lista_pessoas_menu_remover) {
            new AlertDialog.Builder(ListaPessoasActivity.this)
                    .setTitle("Remover pessoa")
                    .setMessage("Tem certeza que deseja excluir essa pessoa?")
                    .setPositiveButton("Sim", ((dialogInterface, i) -> Toast.makeText(this, "Excluido", Toast.LENGTH_SHORT).show()))
                    .setNegativeButton("Não", null)
                    .show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_pessoas_menu, menu);
    }
}
