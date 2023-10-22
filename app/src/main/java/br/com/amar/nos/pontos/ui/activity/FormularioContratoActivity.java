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
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.contrato.BuscaContratoTask;
import br.com.amar.nos.pontos.asynctask.contrato.SalvaContratoAsyncTask;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.databinding.ActivityFormularioContratoBinding;
import br.com.amar.nos.pontos.enumerator.EnumStatusContrato;
import br.com.amar.nos.pontos.model.Contrato;
import br.com.amar.nos.pontos.model.Endereco;
import br.com.amar.nos.pontos.ui.dialog.SelecionarEnderecoDialog;

import java.math.BigDecimal;
import java.util.List;

public class FormularioContratoActivity extends AppCompatActivity {

    private ActivityFormularioContratoBinding viewBind;
    private Long idPessoa = null;

    private Long idContrato = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityFormularioContratoBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        Intent intent = getIntent();
        if(intent.hasExtra("idPessoa")) {
            idPessoa = intent.getLongExtra("idPessoa", 0);
        }

        if(intent.hasExtra("idContrato")) {
            idContrato = intent.getLongExtra("idContrato", 0);
        }

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

            new BuscaContratoTask(idContrato, idPessoa, (c) -> {
                Contrato contrato = buildContratoPorIdPessoa(c);
                final Endereco[] endereco = {null};
                List<Endereco> enderecos = EnderecoDAO.buscarPorIdPessoa(idPessoa);
                if(enderecos.isEmpty()) {
                    Toast.makeText(FormularioContratoActivity.this, "Pessoa sem endereço cadastrado.", Toast.LENGTH_LONG).show();
                } else if(enderecos.size() > 1) {
                    new SelecionarEnderecoDialog(FormularioContratoActivity.this, enderecos, (enderecoSelecionado) -> {
                        contrato.setEndereco(enderecoSelecionado);
                        new SalvaContratoAsyncTask(contrato, this::finish).execute();
                    }).exibeDialogo(getSupportFragmentManager());
                } else {
                    endereco[0] = enderecos.get(0);
                    contrato.setEndereco(endereco[0]);
                    new SalvaContratoAsyncTask(contrato, this::finish).execute();
                }
            }).execute();
        }
        return super.onOptionsItemSelected(item);
    }

    private void montaContrato() {
        if(idContrato != null) {
            Contrato contrato = ContratoDAO.buscarPorId(idContrato);
            viewBind.activityFormularioContratoProduto.setText(contrato.getProduto());
            viewBind.activityFormularioContratoValor.setText(contrato.getValor().toString());
            viewBind.activityFormularioContratoValorPago.setText(contrato.getValorPago().toString());
            viewBind.activityFormularioContratoStatus.setSelection(contrato.getStatus().ordinal());
            viewBind.activityFormularioContratoObservacao.setText(contrato.getObservacao());
            viewBind.activityFormularioContratoDescricaoProduto.setText(contrato.getDescricaoProduto());
            idPessoa = contrato.getPessoa().getId();
        }
    }

    private Contrato buildContratoPorIdPessoa(Contrato contrato) {
        if(contrato == null) {
            contrato = new Contrato();
        }
        contrato.setProduto(viewBind.activityFormularioContratoProduto.getText().toString());
        contrato.setDescricaoProduto(viewBind.activityFormularioContratoDescricaoProduto.getText().toString());
        contrato.setStatus(EnumStatusContrato.values()[viewBind.activityFormularioContratoStatus.getSelectedItemPosition()]);
        contrato.setValor(new BigDecimal(viewBind.activityFormularioContratoValor.getText().toString()));
        contrato.setValorPago(new BigDecimal(viewBind.activityFormularioContratoValorPago.getText().toString()));
        contrato.setObservacao(viewBind.activityFormularioContratoObservacao.getText().toString());
        contrato.setPessoa(PessoaDAO.buscarPorId(idPessoa));

        return contrato;
    }

    private void buildStatusSpinner() {
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(FormularioContratoActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, EnumStatusContrato.values());
        viewBind.activityFormularioContratoStatus.setAdapter(spinnerAdapter);
    }
}
