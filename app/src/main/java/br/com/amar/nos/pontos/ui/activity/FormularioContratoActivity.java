package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.databinding.ActivityFormularioContratoBinding;
import br.com.amar.nos.pontos.enumerator.EnumStatusContrato;
import br.com.amar.nos.pontos.model.Contrato;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;

public class FormularioContratoActivity extends AppCompatActivity {

    private ActivityFormularioContratoBinding viewBind;
    private Long idPessoa;

    private Contrato contrato;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityFormularioContratoBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        Intent intent = getIntent();
        idPessoa = intent.getLongExtra("idPessoa", 0);

        buildStatusSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.activity_formulario_menu_salvar) {
            buildContrato();
            ContratoDAO.save(contrato);
            Snackbar.make(FormularioContratoActivity.this, viewBind.getRoot(), contrato.getProduto(), Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildContrato() {
        contrato = new Contrato();
        contrato.setProduto(viewBind.activityFormularioContratoProduto.getText().toString());
        contrato.setStatus(EnumStatusContrato.values()[viewBind.activityFormularioContratoStatus.getSelectedItemPosition()]);
        contrato.setValor(new BigDecimal(viewBind.activityFormularioContratoValor.getText().toString()));
        contrato.setValorPago(new BigDecimal(viewBind.activityFormularioContratoValorPago.getText().toString()));
        contrato.setPessoa(PessoaDAO.buscarPorId(idPessoa));
    }

    private void buildStatusSpinner() {
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(FormularioContratoActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, EnumStatusContrato.values());
        viewBind.activityFormularioContratoStatus.setAdapter(spinnerAdapter);
    }


}
