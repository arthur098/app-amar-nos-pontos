package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.pessoa.SalvarPessoaTask;
import br.com.amar.nos.pontos.database.AmarDatabase;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.databinding.ActivityFormularioPessoaBinding;
import br.com.amar.nos.pontos.enumerator.EnumEstadoCivil;
import br.com.amar.nos.pontos.model.Pessoa;

public class FormularioPessoaActivity extends AppCompatActivity {
    private ActivityFormularioPessoaBinding viewBind;
    private Pessoa pessoa;
    private PessoaDAO pessoaDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityFormularioPessoaBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        ActionBar supportActionBar = this.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        AmarDatabase db = AmarDatabase.getInstance(this);
        pessoaDAO = db.pessoaDAO();

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, EnumEstadoCivil.values());
        viewBind.activityFormularioPessoaEstadoCivilSpinner.setAdapter(spinnerAdapter);

        setFormEdit();
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
                montaPessoa();
                new SalvarPessoaTask(pessoa, pessoaDAO, this::finish).execute();
            }
        }
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void montaPessoa() {
        if(pessoa == null) {
            pessoa = new Pessoa();
        }

        pessoa.setNomeCompleto(viewBind.activityFormularioPessoaNomeCompleto.getText().toString());
        pessoa.setCpfCnpj(viewBind.activityFormularioPessoaCpfCnpj.getText().toString());
        pessoa.setNacionalidade(viewBind.activityFormularioPessoaNacionalidade.getText().toString());
        pessoa.setEstadoCivil(EnumEstadoCivil.values()[viewBind.activityFormularioPessoaEstadoCivilSpinner.getSelectedItemPosition()]);
        pessoa.setProfissao(viewBind.activityFormularioPessoaProfissao.getText().toString());
    }

    public void setFormEdit() {
        Intent intent = getIntent();
        if(intent.hasExtra("pessoa")) {
            pessoa = (Pessoa) intent.getSerializableExtra("pessoa");

            viewBind.activityFormularioPessoaNomeCompleto.setText(pessoa.getNomeCompleto());
            viewBind.activityFormularioPessoaCpfCnpj.setText(pessoa.getCpfCnpj());
            viewBind.activityFormularioPessoaNacionalidade.setText(pessoa.getNacionalidade());
            viewBind.activityFormularioPessoaEstadoCivilSpinner.setSelection(pessoa.getEstadoCivil() == null ? EnumEstadoCivil.SOLTEIRO.ordinal() : pessoa.getEstadoCivil().ordinal());
            viewBind.activityFormularioPessoaProfissao.setText(pessoa.getProfissao());
        }
        setTitle(pessoa != null ? "Editar Pessoa" : "Nova Pessoa");
    }

    public boolean validate() {
        boolean isValido = false;

        if(viewBind.activityFormularioPessoaNomeCompleto.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioPessoaActivity.this, "Informar o nome completo.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioPessoaNacionalidade.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioPessoaActivity.this, "Informar a nacionalidade.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioPessoaProfissao.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioPessoaActivity.this, "Informar profissão.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioPessoaCpfCnpj.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioPessoaActivity.this, "Informar o CPF/CNPJ.", Toast.LENGTH_SHORT).show();
        } else {
            isValido = true;
        }

        return isValido;
    }
}
