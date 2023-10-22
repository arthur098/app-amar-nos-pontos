package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.endereco.BuscaEnderecoAsyncTask;
import br.com.amar.nos.pontos.asynctask.endereco.SalvaEnderecoAsyncTask;
import br.com.amar.nos.pontos.databinding.ActivityFormularioEnderecoBinding;
import br.com.amar.nos.pontos.model.Endereco;

public class FormularioEnderecoActivity extends AppCompatActivity {

    private ActivityFormularioEnderecoBinding viewBind;

    private Long idPessoa;
    private Long idEndereco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityFormularioEnderecoBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        Intent intent = getIntent();
        if(intent.hasExtra("idPessoa")) {
            idPessoa = intent.getLongExtra("idPessoa", 0);
        }
        if(intent.hasExtra("idEndereco")) {
            idEndereco = intent.getLongExtra("idEndereco", 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.activity_formulario_menu_salvar) {
            new BuscaEnderecoAsyncTask(idEndereco, e -> new SalvaEnderecoAsyncTask(montaEndereco(e), this::finish).execute()).execute();
        }
        return super.onOptionsItemSelected(item);
    }

    public Endereco montaEndereco(Endereco endereco) {

        endereco.setLogradouro(viewBind.activityFormularioEnderecoLogradouro.getText().toString());
        endereco.setBairro(viewBind.activityFormularioEnderecoBairro.getText().toString());
        endereco.setComplemento(viewBind.activityFormularioEnderecoComplemento.getText().toString());
        endereco.setMunicipio(viewBind.activityFormularioEnderecoMunicipio.getText().toString());
        endereco.setNumero(viewBind.activityFormularioEnderecoNumero.getText().toString());
        endereco.setEstado(viewBind.activityFormularioEnderecoEstado.getText().toString());
        endereco.setIdPessoa(idPessoa);

        return endereco;
    }
}
