package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.databinding.ActivityListaEnderecosBinding;
import br.com.amar.nos.pontos.ui.adapter.EnderecoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaEnderecosActivity extends AppCompatActivity {

    private ActivityListaEnderecosBinding viewBind;
    private Long idPessoa = null;
    private EnderecoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityListaEnderecosBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);
        Intent intent = getIntent();
        if(intent.hasExtra("idPessoa")) {
            this.idPessoa = intent.getLongExtra("idPessoa", 0);
        }
        setAdapter();

        FloatingActionButton fab = findViewById(R.id.activity_lista_enderecos_add);

        fab.setOnClickListener((view) -> {
            Intent intentToGo = new Intent(ListaEnderecosActivity.this, FormularioEnderecoActivity.class);
            intentToGo.putExtra("idPessoa", idPessoa);

            startActivity(intentToGo);
        });
    }

    @Override
    protected void onResume() {
        adapter.atualizarEnderecos();
        super.onResume();
    }

    public void setAdapter() {
        if(idPessoa != null && idPessoa > 0) {
            adapter = new EnderecoAdapter(ListaEnderecosActivity.this, idPessoa);
            ListView listaEndereco = findViewById(R.id.lista_enderecos);
            listaEndereco.setAdapter(adapter);
            listaEndereco.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent = new Intent(ListaEnderecosActivity.this, FormularioEnderecoActivity.class);
                intent.putExtra("idEndereco", adapter.getItemId(i));
                startActivity(intent);
            });
        }
    }

}
