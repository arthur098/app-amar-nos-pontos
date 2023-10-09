package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    }
}
