package br.com.amar.nos.pontos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.asynctask.pessoa.BuscaPessoaTask;
import br.com.amar.nos.pontos.asynctask.pessoa.BuscaPessoasTask;
import br.com.amar.nos.pontos.asynctask.pessoa.ExcluirPessoaTask;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.model.Pessoa;

import java.util.List;

public class PessoaAdapter extends BaseAdapter {

    private final PessoaDAO pessoaDAO;
    private List<Pessoa> pessoas;
    private final Context context;

    public PessoaAdapter(Context context, List<Pessoa> pessoaList, PessoaDAO pessoaDAO) {
        this.context = context;
        this.pessoas = pessoaList;
        this.pessoaDAO = pessoaDAO;
    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Pessoa getItem(int i) {
        return pessoas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return pessoas.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = getView(viewGroup);

        Pessoa pessoa = this.pessoas.get(i);
        TextView textViewNome = view.findViewById(R.id.item_pessoa_nome);

        textViewNome.setText(pessoa.getNomeCompleto());

        return view;
    }

    private View getView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_pessoa, viewGroup, false);
    }

    public void atualizaPessoa() {
        new BuscaPessoasTask(pessoaDAO, (pessoasList) -> {
            this.pessoas = pessoasList;
            this.notifyDataSetChanged();
        }).execute();

    }

    public void excluir(Long id) {
        new BuscaPessoaTask(id, this.pessoaDAO, pessoa -> new ExcluirPessoaTask(pessoa, this.pessoaDAO, () -> {
            this.atualizaPessoa();
            Toast.makeText(context, "Pessoa excluída com sucesso.", Toast.LENGTH_SHORT).show();
        }).execute()).execute();
    }
}
