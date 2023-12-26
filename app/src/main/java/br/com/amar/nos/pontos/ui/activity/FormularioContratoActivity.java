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
import br.com.amar.nos.pontos.asynctask.contrato.BuscaContratoTask;
import br.com.amar.nos.pontos.asynctask.contrato.SalvaContratoTask;
import br.com.amar.nos.pontos.asynctask.endereco.BuscaEnderecoPorIdPessoaTask;
import br.com.amar.nos.pontos.database.AmarDatabase;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.databinding.ActivityFormularioContratoBinding;
import br.com.amar.nos.pontos.enumerator.EnumStatusContrato;
import br.com.amar.nos.pontos.model.Contrato;
import br.com.amar.nos.pontos.model.Endereco;
import br.com.amar.nos.pontos.ui.dialog.SelecionarEnderecoDialog;

public class FormularioContratoActivity extends AppCompatActivity {

    private ActivityFormularioContratoBinding viewBind;

    private ContratoDAO contratoDAO;
    private Long idPessoa = null;

    private Long idContrato = null;
    private EnderecoDAO enderecoDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityFormularioContratoBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        ActionBar supportActionBar = this.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        AmarDatabase db = AmarDatabase.getInstance(this);
        contratoDAO = db.contratoDAO();
        enderecoDAO = db.enderecoDAO();

        Intent intent = getIntent();
        if(intent.hasExtra("idPessoa")) {
            idPessoa = intent.getLongExtra("idPessoa", 0);
        }

        if(intent.hasExtra("idContrato")) {
            idContrato = intent.getLongExtra("idContrato", 0);
        }
        setTitle(idContrato != null ? "Editar Contrato" : "Novo Contrato");

        this.montaContrato();
        this.buildStatusSpinner();
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
                new BuscaContratoTask(idContrato, contratoDAO, (c) -> {
                    Contrato contrato = buildContratoPorIdPessoa(c);
                    final Endereco[] endereco = {null};
                    new BuscaEnderecoPorIdPessoaTask(idPessoa, enderecoDAO, (enderecos) -> {
                        if(enderecos.isEmpty()) {
                            Toast.makeText(FormularioContratoActivity.this, "Pessoa sem endereço cadastrado.", Toast.LENGTH_LONG).show();
                        } else if(enderecos.size() > 1) {
                            new SelecionarEnderecoDialog(FormularioContratoActivity.this, enderecos, (enderecoSelecionado) -> {
                                contrato.setIdEndereco(enderecoSelecionado.getId());
                                contrato.setEndereco(enderecoSelecionado);
                                new SalvaContratoTask(contrato, contratoDAO, this::finish).execute();
                            }).exibeDialogo(getSupportFragmentManager());
                        } else {
                            endereco[0] = enderecos.get(0);
                            contrato.setIdEndereco(endereco[0].getId());
                            new SalvaContratoTask(contrato, contratoDAO, this::finish).execute();
                        }
                    }).execute();
                }).execute();
            }
        }
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void montaContrato() {
        if(idContrato != null) {
            new BuscaContratoTask(idContrato, contratoDAO, (contrato) ->{
                viewBind.activityFormularioContratoProduto.setText(contrato.getProduto());
                viewBind.activityFormularioContratoValor.setText(contrato.getValor().toString());
                viewBind.activityFormularioContratoValorPago.setText(contrato.getValorPago().toString());
                viewBind.activityFormularioContratoStatus.setSelection(contrato.getStatus().ordinal());
                viewBind.activityFormularioContratoObservacao.setText(contrato.getObservacao());
                viewBind.activityFormularioContratoDescricaoProduto.setText(contrato.getDescricaoProduto());
                idPessoa = contrato.getIdPessoa();
            }).execute();
        }
    }

    private Contrato buildContratoPorIdPessoa(Contrato contrato) {
        if(contrato == null) {
            contrato = new Contrato();
        }
        contrato.setProduto(viewBind.activityFormularioContratoProduto.getText().toString());
        contrato.setDescricaoProduto(viewBind.activityFormularioContratoDescricaoProduto.getText().toString());
        contrato.setStatus(EnumStatusContrato.values()[viewBind.activityFormularioContratoStatus.getSelectedItemPosition()]);
        contrato.setValor(Double.parseDouble(viewBind.activityFormularioContratoValor.getText().toString()));
        contrato.setValorPago(Double.parseDouble(viewBind.activityFormularioContratoValorPago.getText().toString()));
        contrato.setObservacao(viewBind.activityFormularioContratoObservacao.getText().toString());
        contrato.setIdPessoa(idPessoa);

        return contrato;
    }

    private void buildStatusSpinner() {
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(FormularioContratoActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, EnumStatusContrato.values());
        viewBind.activityFormularioContratoStatus.setAdapter(spinnerAdapter);
    }

    public boolean validate() {
        boolean isValido = false;

        if(viewBind.activityFormularioContratoProduto.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioContratoActivity.this, "Informar o produto.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioContratoDescricaoProduto.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioContratoActivity.this, "Informar a descrição do produto.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioContratoValor.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioContratoActivity.this, "Informar o valor do produto.", Toast.LENGTH_SHORT).show();
        } else if(viewBind.activityFormularioContratoValorPago.getText().toString().trim().isEmpty()) {
            Toast.makeText(FormularioContratoActivity.this, "Informar o valor pago.", Toast.LENGTH_SHORT).show();
        } else {
            isValido = true;
        }

        return isValido;
    }
}
