package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.endereco.BuscaEnderecoTask;
import br.com.amar.nos.pontos.asynctask.endereco.SalvaEnderecoTask;
import br.com.amar.nos.pontos.database.AmarDatabase;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.databinding.ActivityFormularioEnderecoBinding;
import br.com.amar.nos.pontos.model.Endereco;

public class FormularioEnderecoActivity extends AppCompatActivity {

    private ActivityFormularioEnderecoBinding viewBind;

    private Long idPessoa;
    private Long idEndereco = null;
    private EnderecoDAO enderecoDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityFormularioEnderecoBinding.inflate(getLayoutInflater());
        setSupportActionBar(viewBind.toolbar);
        setContentView(viewBind.getRoot());

        ActionBar supportActionBar = this.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        AmarDatabase db = AmarDatabase.getInstance(this);
        enderecoDAO = db.enderecoDAO();

        Intent intent = getIntent();
        if(intent.hasExtra("idPessoa")) {
            idPessoa = intent.getLongExtra("idPessoa", 0);
        }
        if(intent.hasExtra("idEndereco")) {
            idEndereco = intent.getLongExtra("idEndereco", 0);
            new BuscaEnderecoTask(idEndereco, enderecoDAO, endereco -> {
                viewBind.activityFormularioEnderecoLogradouro.setText(endereco.getLogradouro());
                viewBind.activityFormularioEnderecoNumero.setText(endereco.getNumero());
                viewBind.activityFormularioEnderecoComplemento.setText(endereco.getComplemento());
                viewBind.activityFormularioEnderecoBairro.setText(endereco.getBairro());
                viewBind.activityFormularioEnderecoMunicipio.setText(endereco.getMunicipio());
                viewBind.activityFormularioEnderecoEstado.setText(endereco.getEstado());
                idPessoa = endereco.getIdPessoa();
            }).execute();
        }
        setTitle(idEndereco != null ? "Editar Endereco" : "Novo Endereco");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.activity_formulario_menu_salvar) {
            if(validate()) {
                new BuscaEnderecoTask(idEndereco, enderecoDAO, e -> {
                    new SalvaEnderecoTask(montaEndereco(e != null ? e : new Endereco()), enderecoDAO, this::finish).execute();
                }).execute();
            }
        }
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public Endereco montaEndereco(Endereco endereco) {

        endereco.setLogradouro(viewBind.activityFormularioEnderecoLogradouro.getText().toString());
        endereco.setBairro(viewBind.activityFormularioEnderecoBairro.getText().toString());
        endereco.setComplemento(viewBind.activityFormularioEnderecoComplemento.getText().toString());
        endereco.setMunicipio(viewBind.activityFormularioEnderecoMunicipio.getText().toString());
        String numero = viewBind.activityFormularioEnderecoNumero.getText().toString();
        endereco.setNumero(numero.trim().isEmpty() ? "S/N" : numero);
        endereco.setEstado(viewBind.activityFormularioEnderecoEstado.getText().toString().toUpperCase());
        endereco.setIdPessoa(idPessoa);

        return endereco;
    }

    public boolean validate() {
        boolean isValido = false;

        if(viewBind.activityFormularioEnderecoLogradouro.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioEnderecoActivity.this, "Informar o logradouro.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioEnderecoComplemento.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioEnderecoActivity.this, "Informar o complemento.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioEnderecoBairro.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioEnderecoActivity.this, "Informar o bairro.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioEnderecoMunicipio.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioEnderecoActivity.this, "Informar o município.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioEnderecoEstado.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioEnderecoActivity.this, "Informar o estado.", Toast.LENGTH_SHORT).show();
        } else {
            isValido = true;
        }

        return isValido;
    }
}
