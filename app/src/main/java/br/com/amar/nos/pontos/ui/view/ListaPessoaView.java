package br.com.amar.nos.pontos.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.contrato.BuscaContratoPorIdPessoaTask;
import br.com.amar.nos.pontos.asynctask.contrato.ExcluirContratoTask;
import br.com.amar.nos.pontos.asynctask.endereco.BuscaEnderecoPorIdPessoaTask;
import br.com.amar.nos.pontos.asynctask.endereco.ExcluirEnderecoTask;
import br.com.amar.nos.pontos.asynctask.pessoa.BuscaPessoasTask;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.databinding.ActivityListaPessoasBinding;
import br.com.amar.nos.pontos.model.Pessoa;
import br.com.amar.nos.pontos.ui.activity.FormularioPessoaActivity;
import br.com.amar.nos.pontos.ui.activity.ListaContratoActivity;

import br.com.amar.nos.pontos.ui.activity.ListaEnderecosActivity;
import br.com.amar.nos.pontos.ui.activity.ListaPessoasActivity;
import br.com.amar.nos.pontos.ui.adapter.PessoaAdapter;

public class ListaPessoaView {

    private final PessoaDAO pessoaDAO;
    private final Context context;
    private PessoaAdapter pessoaAdapter;
    private final ActivityListaPessoasBinding viewBind;

    public ListaPessoaView(Context context, PessoaDAO pessoaDAO, ActivityListaPessoasBinding viewBind) {
        this.context = context;
        this.pessoaDAO = pessoaDAO;
        this.viewBind = viewBind;
    }

    public void onContextConfig(MenuItem item, ListaPessoasActivity listaPessoasActivity, ListaPessoaView view, ContratoDAO contratoDAO, EnderecoDAO enderecoDAO) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Pessoa pessoa = pessoaAdapter.getItem(menuInfo.position);
        if(item.getItemId() == R.id.activity_lista_pessoas_menu_editar_pessoa) {
            Intent intent = new Intent(listaPessoasActivity, FormularioPessoaActivity.class);
            intent.putExtra("pessoa", pessoa);

            listaPessoasActivity.startActivity(intent);
        } else if(item.getItemId() == R.id.activity_lista_pessoas_menu_remover) {
            view.excluir(pessoa, contratoDAO, enderecoDAO);
        } else if(item.getItemId() == R.id.activity_lista_pessoas_menu_lista_enderecos) {
            Intent intent = new Intent(listaPessoasActivity, ListaEnderecosActivity.class);
            intent.putExtra("idPessoa", pessoa.getId());

            listaPessoasActivity.startActivity(intent);
        }
    }

    private void setListViewPessoas() {
        viewBind.listaPessoas.setAdapter(pessoaAdapter);
        viewBind.listaPessoas.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
            Intent intent = new Intent(this.context, ListaContratoActivity.class);
            intent.putExtra("idPessoa", pessoaAdapter.getItemId(i));

            context.startActivity(intent);
        });
    }

    public void excluir(Pessoa pessoa, ContratoDAO contratoDAO, EnderecoDAO enderecoDAO) {
        new AlertDialog.Builder(this.context)
                .setTitle("Remover pessoa")
                .setMessage("Tem certeza que deseja excluir essa pessoa?")
                .setPositiveButton("Sim", ((dialogInterface, i) -> {
                    new BuscaContratoPorIdPessoaTask(pessoa.getId(), contratoDAO, contratos -> new ExcluirContratoTask(contratoDAO, contratos, () -> new BuscaEnderecoPorIdPessoaTask(pessoa.getId(), enderecoDAO, enderecos -> new ExcluirEnderecoTask(enderecos, enderecoDAO, () -> this.pessoaAdapter.excluir(pessoa.getId())).execute()).execute()).execute()).execute();
                }))
                .setNegativeButton("Não", null)
                .show();
    }

    public void setAdapterList() {
        if(this.pessoaAdapter != null) {
            this.pessoaAdapter.atualizaPessoa();
        } else {
            new BuscaPessoasTask(pessoaDAO, pessoas -> {
                this.pessoaAdapter = new PessoaAdapter(this.context, pessoas, pessoaDAO);
                setListViewPessoas();
            }).execute();
        }
    }
}
