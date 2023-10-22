package br.com.amar.nos.pontos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.databinding.ActivityListaContratoBinding;
import br.com.amar.nos.pontos.model.Contrato;
import br.com.amar.nos.pontos.service.ContratoService;
import br.com.amar.nos.pontos.ui.adapter.ContratoAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListaContratoActivity extends AppCompatActivity {

    private final ContratoService contratoService = new ContratoService();
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
        registerForContextMenu(viewBind.listaContratos);
    }

    private void setEventFloatingActionButtonAddContrato() {
        viewBind.activityListaContratoAdd.setOnClickListener((view) -> {
            Intent intent = new Intent(ListaContratoActivity.this, FormularioContratoActivity.class);
            intent.putExtra("idPessoa", idPessoa);
            startActivity(intent);
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.activity_lista_contrato_menu_btn_gerar_contrato) {
            Contrato contrato = contratoAdapter.getItem(menuInfo.position);
            this.contratoService.createPdf(contrato);
            Snackbar.make(item.getActionView(), "Contrato gerado", Snackbar.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_contrato_menu, menu);
    }

}
