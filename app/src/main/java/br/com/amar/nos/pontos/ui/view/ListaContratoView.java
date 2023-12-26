package br.com.amar.nos.pontos.ui.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;
import androidx.core.content.res.ResourcesCompat;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.contrato.BuscaContratoPorIdPessoaTask;
import br.com.amar.nos.pontos.asynctask.contrato.ExcluirContratoTask;
import br.com.amar.nos.pontos.asynctask.endereco.BuscaEnderecoTask;
import br.com.amar.nos.pontos.asynctask.pessoa.BuscaPessoaTask;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.databinding.ActivityListaContratoBinding;
import br.com.amar.nos.pontos.model.Contrato;
import br.com.amar.nos.pontos.service.ContratoService;
import br.com.amar.nos.pontos.ui.activity.FormularioContratoActivity;
import br.com.amar.nos.pontos.ui.activity.ListaContratoActivity;
import br.com.amar.nos.pontos.ui.adapter.ContratoAdapter;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

public class ListaContratoView {
    private ContratoAdapter contratoAdapter;
    private final ContratoDAO contratoDAO;
    private final PessoaDAO pessoaDAO;
    private final EnderecoDAO enderecoDAO;
    private final Long idPessoa;
    private Contrato contrato;

    private final ContratoService contratoService = new ContratoService();

    public ListaContratoView(ContratoDAO contratoDAO, PessoaDAO pessoaDAO, EnderecoDAO enderecoDAO, Long idPessoa) {
        this.contratoDAO = contratoDAO;
        this.pessoaDAO = pessoaDAO;
        this.enderecoDAO = enderecoDAO;
        this.idPessoa = idPessoa;
    }

    public void initAdapter(ListaContratoActivity listaContratoActivity, ActivityListaContratoBinding viewBind) {
        if(contratoAdapter == null) {
            new BuscaContratoPorIdPessoaTask(idPessoa, contratoDAO, (contratos) -> {
                setListViewAdapter(listaContratoActivity, viewBind, contratos);
                setEventFloatingActionButtonAddContrato(listaContratoActivity, viewBind);
            }).execute();
        } else {
            new BuscaContratoPorIdPessoaTask(idPessoa, contratoDAO, (contratos) -> this.contratoAdapter.atualizaContratos(contratos)).execute();
        }
    }

    public void setListViewAdapter(ListaContratoActivity listaContratoActivity, ActivityListaContratoBinding viewBind, List<Contrato> contratos) {
        this.contratoAdapter = new ContratoAdapter(listaContratoActivity, this.contratoDAO, contratos);
        viewBind.listaContratos.setAdapter(contratoAdapter);
        viewBind.listaContratos.setOnItemClickListener(((adapterView, view, i, l) -> {
            Intent intent = new Intent(listaContratoActivity, FormularioContratoActivity.class);
            intent.putExtra("idContrato", contratoAdapter.getItemId(i));
            listaContratoActivity.startActivity(intent);
        }));
    }

    private void setEventFloatingActionButtonAddContrato(ListaContratoActivity listaContratoActivity, ActivityListaContratoBinding viewBind) {
        viewBind.activityListaContratoAdd.setOnClickListener((view) -> {
            Intent intent = new Intent(listaContratoActivity, FormularioContratoActivity.class);
            intent.putExtra("idPessoa", idPessoa);
            listaContratoActivity.startActivity(intent);
        });
    }

    public void contextItemSelected(ListaContratoActivity listaContratoActivity, MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.activity_lista_contrato_menu_btn_gerar_contrato) {
            this.contrato = contratoAdapter.getItem(menuInfo.position);
            new BuscaPessoaTask(contrato.getIdPessoa(), pessoaDAO, (pessoa -> {
                contrato.setPessoa(pessoa);
                new BuscaEnderecoTask(contrato.getIdEndereco(), enderecoDAO, (endereco) -> {
                    contrato.setEndereco(endereco);
                    Drawable drawable = ResourcesCompat.getDrawable(listaContratoActivity.getResources(), R.drawable.assinatura_amanda_amar, null);
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    this.contratoService.createPdf(contrato, stream.toByteArray());
                    Toast.makeText(listaContratoActivity, "Contrato gerado", Toast.LENGTH_SHORT).show();
                }).execute();
            })).execute();

        }
        if(item.getItemId() == R.id.activity_lista_contrato_menu_btn_excluir) {
            this.contrato = contratoAdapter.getItem(menuInfo.position);
            new ExcluirContratoTask(contratoDAO, Collections.singletonList(contrato), () -> contratoAdapter.remove(menuInfo.position)).execute();
        }
    }
}
