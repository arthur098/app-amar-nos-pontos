package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.databinding.ActivityListaContratoBinding;
import br.com.amar.nos.pontos.model.Contrato;
import br.com.amar.nos.pontos.ui.adapter.ContratoAdapter;

import java.util.List;

public class ListaContratoActivity extends AppCompatActivity {

    private ContratoAdapter contratoAdapter;
    private ActivityListaContratoBinding viewBind;

    private Long idPessoa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBind = ActivityListaContratoBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

        idPessoa = getIntent().getLongExtra("idPessoa", 0);
        List<Contrato> contratos = ContratoDAO.buscarPorIdPessoa(idPessoa);

        setListViewAdapter(contratos);
        setEventFloatingActionButtonAddContrato();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.contratoAdapter.atualizaContratos(idPessoa);
    }

    public void setListViewAdapter(List<Contrato> contratos) {
        this.contratoAdapter = new ContratoAdapter(ListaContratoActivity.this, contratos);
        viewBind.listaContratos.setAdapter(contratoAdapter);
        viewBind.listaContratos.setOnItemClickListener(((adapterView, view, i, l) -> {
            Intent intent = new Intent(ListaContratoActivity.this, FormularioContratoActivity.class);
            intent.putExtra("idContrato", contratoAdapter.getItemId(i));
            startActivity(intent);
        }));
    }

    private void setEventFloatingActionButtonAddContrato() {
        viewBind.activityListaContratoAdd.setOnClickListener((view) -> {
            Intent intent = new Intent(ListaContratoActivity.this, FormularioContratoActivity.class);
            intent.putExtra("idPessoa", idPessoa);
            startActivity(intent);
        });
    }


}
